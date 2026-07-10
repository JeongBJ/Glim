package com.jeongbj.glim.firebase

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jeongbj.data.fcm.manager.FcmTokenManager
import com.jeongbj.domain.setting.usecase.SettingUseCases
import com.jeongbj.glim.di.ApplicationClass.Companion.CHANNEL_ID
import com.jeongbj.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {

    @Inject
    lateinit var settingUseCases: SettingUseCases

    @Inject
    lateinit var tokenManager: FcmTokenManager
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val type = message.data["type"]
        val title = message.data["title"] ?: ""
        val body = message.data["body"] ?: ""
        val quoteSeq = message.data["quoteSeq"]

        when (type) {
            "LIKE" -> {
                showPostNotification(
                    title = title,
                    body = body,
                    quoteSeq = quoteSeq
                )
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showPostNotification(title: String, body: String, quoteSeq: String?) {
        val deepLinkUri = "https://jeongbj.kro.kr/glim/share/$quoteSeq".toUri()

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            quoteSeq?.hashCode() ?: 0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_glim_image)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        Timber.d(deepLinkUri.toString())
        Timber.d(intent.action ?: "null")
        Timber.d(intent.dataString ?: "null")
        NotificationManagerCompat.from(this).notify(quoteSeq?.hashCode() ?: 0, notification)
    }
}