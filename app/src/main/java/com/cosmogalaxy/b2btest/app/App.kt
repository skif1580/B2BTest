package com.cosmogalaxy.b2btest.app

import android.app.Application
import com.cosmogalaxy.b2btest.di.DiInjector

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DiInjector.startEngine(this)
    }
}