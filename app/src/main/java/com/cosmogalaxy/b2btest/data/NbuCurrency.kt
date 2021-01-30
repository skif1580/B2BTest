package com.cosmogalaxy.b2btest.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NbuCurrency(
    val r030: Long,
    val txt: String? = "",
    val rate: Double,
    val cc: String,
    @SerialName("exchangedate")
    val exchangeDate: String
)
