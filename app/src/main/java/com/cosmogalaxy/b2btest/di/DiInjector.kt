package com.cosmogalaxy.b2btest.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object DiInjector {
    fun startEngine(appContext: Context) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(appContext)
            modules(listOf(viewModule))
        }
    }
}