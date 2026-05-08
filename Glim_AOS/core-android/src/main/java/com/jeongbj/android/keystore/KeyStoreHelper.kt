package com.jeongbj.android.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.jeongbj.core.common.KEYSTORE
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreHelper @Inject constructor() {
    private val keyStore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        KeyStore.getInstance(KEYSTORE.TYPE).apply {
            load(null)
        }
    }

    @Synchronized
    fun getOrCreateSecretKey(): SecretKey {
        return getSecretKey() ?: createSecretKey()
    }

    private fun getSecretKey(): SecretKey? {
        return (keyStore.getEntry(
            KEYSTORE.ALIAS,
            null
        ) as? KeyStore.SecretKeyEntry)?.secretKey
    }

    private fun createSecretKey(): SecretKey {
        getSecretKey()?.let { return it }

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE.TYPE
        )

        val parameterSpec = KeyGenParameterSpec.Builder(
            KEYSTORE.ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)
        }.build()

        keyGenerator.init(parameterSpec)

        return keyGenerator.generateKey()
    }
}