package com.jeongbj.presentation.feature.lock.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LockScreenServiceController @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun start() {
        ContextCompat.startForegroundService(context, Intent(context, LockScreenService::class.java))
    }

    fun stop() {
        context.stopService(Intent(context, LockScreenService::class.java))
    }
}