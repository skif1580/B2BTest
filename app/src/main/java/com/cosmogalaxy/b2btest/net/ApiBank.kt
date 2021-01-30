package com.cosmogalaxy.b2btest.net

import com.cosmogalaxy.b2btest.data.NbuCurrency
import com.cosmogalaxy.b2btest.data.PrivatCurrency
import com.cosmogalaxy.b2btest.net.response.ArchiveCurrency
import com.cosmogalaxy.b2btest.net.response.ArchiveCurrencyNBY
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiBank {

    @GET("p24api/pubinfo?json&exchange&coursid=5")
    suspend fun loadPrivatListCurrency():List<PrivatCurrency>

    @GET("p24api/exchange_rates?json")
    suspend fun loadArchiveCurrencyListPrivat(@Query("date") date:String):ArchiveCurrency

    @GET
    suspend fun loadNbyListCurrency(@Url url:String):List<NbuCurrency>

    @GET
    suspend fun loadArchiveNBYListCurrency(@Url url: String):List<ArchiveCurrencyNBY>
}