/**
 * @author Ayoub Chouak (a.chouak)
 * @brief  Payment schema used for the transaction between this backend and Nexi gateway
 *
 */
package io.github.app.schema

import com.google.gson.annotations.SerializedName

class PaymentInternalSchema
{
    @SerializedName("apiKey")
    var xPayApiKey: String? = null

    @SerializedName("codiceTransazione")
    var xPayTransactCode: String? = null

    @SerializedName("importo")
    var xPayImporto: String? = null

    @SerializedName("divisa")
    var xPayCurrency: String? = null

    @SerializedName("xpayNonce")
    var xPayNonce: String? = null

    @SerializedName("timeStamp")
    var xPayTimestamp: String? = null

    @SerializedName("mac")
    var xPayHmac: String? = null
}