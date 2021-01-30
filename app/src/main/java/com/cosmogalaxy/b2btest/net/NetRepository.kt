package com.cosmogalaxy.b2btest.net

import com.cosmogalaxy.b2btest.data.NbuCurrency
import com.cosmogalaxy.b2btest.data.PrivatCurrency
import com.cosmogalaxy.b2btest.net.response.ArchiveCurrencyNBY

class NetRepository {

    companion object {
        const val NBY_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json"
        const val NBY_URL_ARCHIVE="https://bank.gov.ua/NBU_Exchange/exchange?json"
    }

    private val resApi = RestWrapper.restApi

    suspend fun loadListCurrencyPrivat():List<PrivatCurrency> = resApi.loadPrivatListCurrency()

    suspend fun getArchiveCurrencyListPrivat(date:String) = resApi.loadArchiveCurrencyListPrivat(date)

    suspend fun loadListCurrencyNby():List<NbuCurrency> = resApi.loadNbyListCurrency(NBY_URL)

    suspend fun getArchiveCurrencyNBY(date: String):List<ArchiveCurrencyNBY> =resApi.loadArchiveNBYListCurrency(NBY_URL_ARCHIVE+date)

}