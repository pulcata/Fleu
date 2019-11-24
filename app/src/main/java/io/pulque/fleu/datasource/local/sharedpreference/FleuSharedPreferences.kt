package io.pulque.fleu.datasource.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import io.pulque.fleu.datasource.local.sharedpreference.SharedPreferencesKeys.SHARED_PREFERENCES_NAME
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class FleuSharedPreferences @Inject constructor(val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0)

    var token: String
        get() = sharedPreferences.getString(SharedPreferencesKeys.AUTH_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(SharedPreferencesKeys.AUTH_TOKEN, value).apply()
}