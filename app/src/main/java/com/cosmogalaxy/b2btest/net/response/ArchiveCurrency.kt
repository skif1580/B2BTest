package com.cosmogalaxy.b2btest.net.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class ArchiveCurrency(
    @SerialName("date")
    var date: String,
    @SerializedName("bank")
    var bank: String,
    @SerializedName("baseCurrency")
    var baseCurrency: Int,
    @SerializedName("baseCurrencyLit")
    var baseCurrencyLit: String,
    @SerializedName("exchangeRate")
    var exchangeRate: List<ExchangeRate>
)

data class ExchangeRate(

    @SerializedName("baseCurrency")
    var baseCurrency: String,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("saleRateNB")
    var salesRateNB: Float,
    @SerializedName("purchaseRateNB")
    var purchaseRateNB: Float,
    @SerializedName("saleRate")
    var saleRate: Float,
    @SerializedName("purchaseRate")
    var purchaseRate: Float

)