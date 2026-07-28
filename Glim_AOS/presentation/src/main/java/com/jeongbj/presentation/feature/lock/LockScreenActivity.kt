package com.jeongbj.presentation.feature.lock

import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.android.BuildConfig
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.feature.lock.service.LockScreenService
import com.jeongbj.presentation.theme.GlimTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockScreenActivity : ComponentActivity() {
    private val viewModel by viewModels<LockScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyLockScreenFlags()

        enableEdgeToEdge()
        setContent {
            val quotes = viewModel.quotes.collectAsLazyPagingItems()
            LaunchedEffect(Unit) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        LockScreenSideEffect.OpenCamera -> openCameraFromLockScreen(this@LockScreenActivity)
                        is LockScreenSideEffect.OpenQuote -> handleUnlock(effect.quoteSeq)
                        LockScreenSideEffect.UnlockScreen -> handleUnlock(null)
                        is LockScreenSideEffect.ShowToast -> this@LockScreenActivity.showToast(effect.msg)
                    }
                }
            }
            GlimTheme {
                LockScreenContent(
                    onAction = viewModel::onAction,
                    quotes = quotes
                )
            }
        }


    }

    override fun onResume() {
        super.onResume()
        applyLockScreenFlags()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelLockScreenNotification()
    }

    private fun handleUnlock(quoteSeq: Long?) {
        val keyguardManager = getSystemService(KeyguardManager::class.java)

        if (keyguardManager?.isKeyguardLocked == true) {
            keyguardManager.requestDismissKeyguard(
                this@LockScreenActivity,
                object : KeyguardManager.KeyguardDismissCallback() {
                    override fun onDismissSucceeded() {
                        if (quoteSeq != null) {
                            navigateToGlimQuote(this@LockScreenActivity, quoteSeq)
                        }
                        finish()
                    }
                    override fun onDismissError() {
                    }
                    override fun onDismissCancelled() {
                    }
                }
            )
        } else {
            if (quoteSeq != null) {
                navigateToGlimQuote(this@LockScreenActivity, quoteSeq)
            }
            cancelLockScreenNotification()
            finish()
        }
    }

    private fun cancelLockScreenNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(LockScreenService.FULL_SCREEN_NOTIFICATION_ID)
    }

    private fun navigateToGlimQuote(context: Context, quoteSeq: Long) {
        val deepLinkUri = "${BuildConfig.BASE_URL}$quoteSeq".toUri()

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            setPackage(context.packageName)
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        }

        context.startActivity(intent)
    }

    private fun openCameraFromLockScreen(context: Context) {
        val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            )
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val fallbackIntent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(fallbackIntent)
            } catch (e2: ActivityNotFoundException) {

            }
        }
    }

    private fun applyLockScreenFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
    }
}