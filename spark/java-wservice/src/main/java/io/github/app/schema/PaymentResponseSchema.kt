package io.github.app.schema

import com.google.gson.annotations.SerializedName

class PaymentResponseSchema
{
    @SerializedName("esito")
    var xPayEsito: String? = null

    @SerializedName("esitoEx")
    var xPayEsitoEx: String? = null
}
