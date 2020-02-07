package com.apps.noteMe

import android.app.Application
import timber.log.Timber

class Application : Application(){

    init {
        val timber = Timber.plant(Timber.DebugTree())
    }
}