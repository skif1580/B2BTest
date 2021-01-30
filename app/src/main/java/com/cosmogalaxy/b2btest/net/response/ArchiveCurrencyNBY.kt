package com.cosmogalaxy.b2btest.net.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveCurrencyNBY(
    @SerialName("StartDate")
    var StartDate: String,
    @SerialName("TimeSign")
    var TimeSign: String,
    @SerialName("CurrencyCode")
    var CurrencyCode: String,
    @SerialName("CurrencyCodeL")
    var CurrencyCodeL: String,
    @SerialName("Units")
    var Units: Int,
    @SerialName("Amount")
    var Amount: Double

)

