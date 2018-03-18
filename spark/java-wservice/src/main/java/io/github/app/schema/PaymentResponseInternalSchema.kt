/**
 * @author Ayoub Chouak (a.chouak)
 * @brief  Payment schema used for the transaction between this backend and Nexi gateway
 *
 */
package io.github.app.schema

import com.google.gson.annotations.SerializedName

class PaymentResponseInternalSchema
{
    @SerializedName("esito")
    var xPayEsito: String? = null

    @SerializedName("idOperazione")
    var xPayOperationId: String? = null

    @SerializedName("codiceAutorizzazione")
    var xPayAuthCode: String? = null

    @SerializedName("codiceConvenzione")
    var xPayConventionCode: String? = null

    @SerializedName("data")
    var xPayDate: String? = null

    @SerializedName("ora")
    var xPayTime: String? = null

    @SerializedName("nazione")
    var xPayCountry: String? = null

    @SerializedName("regione")
    var xPayRegion: String? = null

    @SerializedName("tipoProdotto")
    var xPayProductType: String? = null

    @SerializedName("tipoTransazione")
    var xPayTransactType: String? = null

    @SerializedName("timeStamp")
    var xPayTimestamp: String? = null

    @SerializedName("mac")
    var xPayHmac: String? = null

    @SerializedName("ppo")
    var xPayMasterpass: String? = null
}