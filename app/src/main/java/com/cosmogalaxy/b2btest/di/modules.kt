package com.cosmogalaxy.b2btest.di

import com.cosmogalaxy.b2btest.net.NetRepository
import com.cosmogalaxy.b2btest.viewmodels.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule= module {
    single { NetRepository() }
    viewModel { CurrencyViewModel(get()) }
}

