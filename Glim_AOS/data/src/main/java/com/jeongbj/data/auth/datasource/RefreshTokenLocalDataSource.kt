package com.jeongbj.data.auth.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jeongbj.android.keystore.KeyStoreManager
import javax.inject.Inject

class RefreshTokenLocalDataSource @Inject constructor(
    private val prefs: SharedPreferences,
    private val keyStoreManager: KeyStoreManager
) {

    fun save(refreshToken: String) {
        prefs.edit { putString(PREF_KEY, keyStoreManager.encrypt(refreshToken)) }
    }

    fun get(): String? {
        return keyStoreManager.decrypt(prefs.getString(PREF_KEY, null))
    }

    fun clear() {
        prefs.edit { clear() }
    }


    companion object{
        const val PREF_KEY = "refreshToken"
    }
    
}