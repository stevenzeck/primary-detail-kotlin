package com.example.primarydetail

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.primarydetail.settings.ThemeHelper
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE)
        themePref?.let { ThemeHelper.applyTheme(it) }
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
