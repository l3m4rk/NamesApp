package dev.l3m4rk.namesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NamesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // STOPSHIP: remove for release
        Timber.plant(Timber.DebugTree())
    }
}