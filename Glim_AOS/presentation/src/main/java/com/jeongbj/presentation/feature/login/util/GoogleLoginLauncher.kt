package com.jeongbj.presentation.feature.login.util

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jeongbj.android.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GoogleLoginLauncher(
    private val context: Context
) : GoogleLoginResult {
    private val credentialManager = CredentialManager.create(context)

    suspend fun login(googleClientId: String): GoogleLoginResult = withContext(Dispatchers.IO) {
        try {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(googleClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(request = request, context = context)
            val credential = result.credential
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)
            GoogleLoginResult.Success(googleIdTokenCredential.idToken)
        } catch (e: NoCredentialException) {
            Timber.e(e)
            GoogleLoginResult.NoCredential
        } catch (e: Exception) {
            Timber.e(e)
            GoogleLoginResult.Error(e)
        }

    }
}

sealed interface GoogleLoginResult {

    data class Success(
        val idToken: String
    ) : GoogleLoginResult

    data object NoCredential : GoogleLoginResult

    data class Error(
        val throwable: Throwable
    ) : GoogleLoginResult
}