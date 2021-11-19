package com.example.primarydetail

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.primarydetail.di.appModule
import com.example.primarydetail.di.databaseModule
import com.example.primarydetail.di.networkModule
import com.example.primarydetail.settings.ThemeHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE)
        themePref?.let { ThemeHelper.applyTheme(it) }

        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(appModule, networkModule, databaseModule)
        }
    }
}
