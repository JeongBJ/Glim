package com.jeongbj.presentation.feature.lock.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.jeongbj.domain.setting.usecase.GetSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getSettingsUseCase: GetSettingsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val isEnabled = getSettingsUseCase().first().lockScreenEnabled
                if (isEnabled) {
                    ContextCompat.startForegroundService(
                        context,
                        Intent(context, LockScreenService::class.java)
                    )
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}