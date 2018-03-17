package io.github.app.handlers

import com.mashape.unirest.http.HttpMethod
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import com.mashape.unirest.request.HttpRequest
import com.mashape.unirest.request.HttpRequestWithBody
import io.github.app.conf.ApiEndpoint
import io.github.app.conf.KeyStore
import io.github.app.exception.NexiPaymentException
import io.github.app.schema.PaymentInternalSchema
import io.github.app.schema.PaymentResponseInternalSchema
import io.github.app.schema.PaymentResponseSchema
import io.github.app.schema.PaymentSchema
import io.github.app.utils.JsonUtils
import spark.*
import java.security.*;
import java.time.*;

object NexiHandlers
{
    fun handlePayment(schema: PaymentSchema): PaymentResponseSchema
    {
        val schemaResponse = PaymentResponseSchema()

        // @todo: handle payment and send a JSON response
        val sha1 = MessageDigest.getInstance("SHA1")
        val nonceHmacInput = "esito=${schema.xPayEsito}idOperazione=${schema.xPayOpId}xpayNonce=${schema.xPayNonce}timeStamp=${schema.xPayTimestamp}${KeyStore.NEXI_HMAC_SECRET}"
        val nonceHmac = sha1.digest(nonceHmacInput.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)

        //  HMAC Mismatch
        if (nonceHmac != schema.xPayMac)
            throw NexiPaymentException("HMAC Mismatch (Nonce)!")

        val timestamp = Instant.now().epochSecond * 1000
        val hmacInput = "apiKey=${schema.payAlias}codiceTransazione=${schema.payTransactionCode}importo=${schema.payImporto}divisa=${schema.payCurrency}xpayNonce=${schema.xPayNonce}timestamp=$timestamp${KeyStore.NEXI_HMAC_SECRET}"
        val hmac = sha1.digest(hmacInput.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)

        // Prepare the payload
        var schemaInternal = PaymentInternalSchema()

        schemaInternal.xPayApiKey = schema.payAlias
        schemaInternal.xPayTransactCode = schema.payTransactionCode
        schemaInternal.xPayImporto = schema.payImporto
        schemaInternal.xPayCurrency = schema.payCurrency
        schemaInternal.xPayNonce = schema.xPayNonce
        schemaInternal.xPayTimestamp = timestamp.toString()
        schemaInternal.xPayHmac = hmac

        // Execute the POST request
        var rest: HttpResponse<JsonNode>? = null

        try {
            rest = Unirest.post(ApiEndpoint.NEXI_NONCE_PAYMENT)
                    .header("content-type", "application/json")
                    .body(JsonUtils.serialize(schemaInternal)).asJson()
        } catch (ex: UnirestException) {
            throw NexiPaymentException("Error dispatching the transaction request to Nexi!", ex)
        }

        // POST went fine, now decode the response
        val responseInternal = JsonUtils.parse(rest.body.toString(), PaymentResponseInternalSchema().javaClass)

        // Compute the HMAC of the response fields
        val responseHmacInput = "esito=${responseInternal.xPayEsito}idOperazione=${responseInternal.xPayOperationId}timeStamp=${responseInternal.xPayTimestamp}${KeyStore.NEXI_HMAC_SECRET}"
        val responseHmac = sha1.digest(responseHmacInput.toByteArray(Charsets.UTF_8)).toString(Charsets.UTF_8)

        // Mismatch? Oops
        if (responseHmac != responseInternal.xPayHmac)
            throw NexiPaymentException("HMAC Mismatch (ResponseInternal)!")

        // Return a PaymentResponseSchema based on xPayEsito
        if (responseInternal.xPayEsito.equals("OK", true))
            schemaResponse.xPayEsito = "OK"
        else if (responseInternal.xPayEsito.equals("ANNULLO", true))
            schemaResponse.xPayEsito = "ROLLBACK"
        else if (responseInternal.xPayEsito.equals("ERRORE", true))
            schemaResponse.xPayEsito = "INTERNAL_ERR"
        else if (responseInternal.xPayEsito.equals("KO", true)) {
            schemaResponse.xPayEsito = "KO"
            schemaResponse.xPayEsitoEx = when (NexiPaymentException.NEXI_ERR_CODES[Integer.valueOf(responseInternal.xPayEsito)]) {
                NexiPaymentException.NexiError.INPUT_MISMATCH -> {
                    "Input non valido!"
                }
                NexiPaymentException.NexiError.INFO_NOT_FOUND -> {
                    "Informazioni non reperibili."
                }
                NexiPaymentException.NexiError.HMAC_MISSING,
                NexiPaymentException.NexiError.HMAC_MISMATCH -> {
                    "Errore di integrita'."
                }
                NexiPaymentException.NexiError.TIMESTAMP_EXPIRED -> {
                    "Richiesta scaduta."
                }
                else -> "Errore generale."
            }
        }
        else
            schemaResponse.xPayEsito = "UNEXPECTED_ERR"

        return schemaResponse
    }
}
