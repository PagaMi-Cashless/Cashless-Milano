/**
 * @author Ayoub Chouak (a.chouak)
 * @file   PaymentSchema.java
 * @brief  JSON Payment Schema
 */
package io.github.app.schema;

import com.google.gson.annotations.*;

public class PaymentSchema
{
    @SerializedName("xpayNonce")
    public String xPayNonce;

    @SerializedName("xpayIdOperazione")
    public String xPayOpId;

    @SerializedName("xpayTimeStamp")
    public String xPayTimestamp;

    @SerializedName("xpayEsito")
    public String xPayEsito;

    @SerializedName("xpayMac")
    public String xPayMac;

    @SerializedName("importo")
    public String payImporto;

    @SerializedName("timestamp")
    public String payTimestamp;

    @SerializedName("divisa")
    public String payCurrency;

    @SerializedName("mac")
    public String payMac;

    @SerializedName("codiceTransazione")
    public String payTransactionCode;

    @SerializedName("alias")
    public String payAlias;
}
