package com.cosmogalaxy.b2btest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmogalaxy.b2btest.data.NbuCurrency
import com.cosmogalaxy.b2btest.data.PrivatCurrency
import com.cosmogalaxy.b2btest.net.NetRepository
import com.cosmogalaxy.b2btest.net.response.ArchiveCurrency
import com.cosmogalaxy.b2btest.net.response.ArchiveCurrencyNBY
import kotlinx.coroutines.launch

class CurrencyViewModel(private val netRepository: NetRepository) : ViewModel() {
    private var _privatState = MutableLiveData<State>(State.Default)
    val privateState: LiveData<State> get() = _privatState
    private var _nbuState = MutableLiveData<State>(State.Default)
    val nbuState: LiveData<State> get() = _nbuState

    fun loadListCurrencyBankPrivat() {
        viewModelScope.launch {
            _privatState.postValue(State.LoadData)
            try {
                val res = netRepository.loadListCurrencyPrivat()
                _privatState.postValue(State.SuccessPrivat(res))
            } catch (e: Exception) {
                _privatState.postValue(State.Error(e.message!!))
            }
        }
    }

    fun loadArchiveNBUCurrency(data: String) {
        viewModelScope.launch {
            _nbuState.postValue(State.LoadData)
            try {
                val result = netRepository.getArchiveCurrencyNBY(data)
                val listCurrency = getListCurrencyNBU(result)
                _nbuState.postValue(State.SuccessNBU(listCurrency))
            } catch (e: java.lang.Exception) {
                _nbuState.postValue(State.Error(e.message!!))
            }
        }
    }

    private fun getListCurrencyNBU(result: List<ArchiveCurrencyNBY>): List<NbuCurrency> {
        val listCurrencyNBY = mutableListOf<NbuCurrency>()
        result.forEach {
            val currency =
                NbuCurrency(0, it.CurrencyCodeL, it.Amount, it.CurrencyCodeL, it.StartDate)
            listCurrencyNBY.add(currency)
        }
        return listCurrencyNBY
    }

    fun getArchiveCurrencyPrivate(date: String) {
        viewModelScope.launch {
            _privatState.postValue(State.LoadData)
            try {
                val resArchiveList = netRepository.getArchiveCurrencyListPrivat(date)
                val list = getListPrivatCurrencyObject(resArchiveList)
                _privatState.postValue(
                    State.SuccessPrivat(list)
                )
            } catch (e: Exception) {
                _privatState.postValue(State.Error(e.message!!))
            }
        }
    }

    private fun getListPrivatCurrencyObject(resArchiveList: ArchiveCurrency): List<PrivatCurrency> {
        val listPrivatCurrency = mutableListOf<PrivatCurrency>()
        resArchiveList.exchangeRate.forEach {
            val currency = PrivatCurrency(
                it.currency,
                it.baseCurrency,
                it.salesRateNB.toString(),
                it.purchaseRateNB.toString()
            )
            listPrivatCurrency.add(currency)
        }
        return listPrivatCurrency
    }

    fun loadListCurrencyBankNBU() {
        viewModelScope.launch {
            _nbuState.postValue(State.LoadData)
            try {
                val res = netRepository.loadListCurrencyNby()
                _nbuState.postValue(State.SuccessNBU(res))
            } catch (e: Exception) {
                _nbuState.postValue(State.Error(e.message!!))
            }
        }
    }
}

sealed class State {
    object Default : State()
    object LoadData : State()
    data class SuccessPrivat(val data: List<PrivatCurrency>) : State()
    data class SuccessNBU(val data: List<NbuCurrency>) : State()
    data class Error(val message: String) : State()
}