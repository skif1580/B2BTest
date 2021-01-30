package com.cosmogalaxy.b2btest.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrivatCurrency(
    val ccy: String?="",
    @SerialName( "base_ccy")
    val baseCcy: String,
    val buy: String,
    val sale: String
)
