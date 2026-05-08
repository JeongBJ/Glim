package com.jeongbj.android.keystore

import android.util.Base64
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreManager @Inject constructor(
    private val keyStoreHelper: KeyStoreHelper
){
    private val key: SecretKey by lazy {
        keyStoreHelper.getOrCreateSecretKey()
    }

    fun encrypt(plainText: String): String{
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val combined = iv + encrypted
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    fun decrypt(cipherText: String?): String?{
        if(cipherText.isNullOrEmpty()) return null

        try {
            val bytes = Base64.decode(cipherText, Base64.NO_WRAP)
            val iv = bytes.copyOfRange(0, IV_SIZE)
            val encrypted = bytes.copyOfRange(IV_SIZE, bytes.size)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
            return String(cipher.doFinal(encrypted), Charsets.UTF_8)
        } catch (e: Exception){
            Log.e("KeyStoreManager", "decrypt: ${e.message}", e)
            return null
        }
    }

    private companion object {
        const val IV_SIZE = 12
    }
}