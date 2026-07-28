package com.jeongbj.presentation.common.notification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jeongbj.android.extentions.checkNotificationPermission
import com.jeongbj.android.extentions.findActivity

@Composable
fun rememberNotificationPermissionState(
    onResult: (granted: Boolean) -> Unit = {}
): NotificationPermissionState {
    val context = LocalContext.current
    var isEnabled by remember {
        mutableStateOf(context.checkNotificationPermission())
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        isEnabled = granted
        onResult(granted)
    }

    val settingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val granted = context.checkNotificationPermission()
        isEnabled = granted
        onResult(granted)
    }

    // remember(isEnabled) 제거 -> 매번 새로 만들지 않고, isEnabled는 State로 위임
    val requestPermission: () -> Unit = remember(permissionLauncher, settingsLauncher) {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val activity = context.findActivity()
                val isDenied = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
                val shouldShowRationale = activity?.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it, Manifest.permission.POST_NOTIFICATIONS
                    )
                } ?: false
                val hasRequestedBefore = context.hasRequestedNotificationPermissionBefore()
                val isPermanentlyDenied = isDenied && hasRequestedBefore && !shouldShowRationale

                if (isPermanentlyDenied) {
                    val intent = Intent().apply {
                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    }
                    settingsLauncher.launch(intent)
                } else {
                    context.markNotificationPermissionRequested()
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                val intent = Intent().apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                settingsLauncher.launch(intent)
            }
        }
    }

    return NotificationPermissionState(
        isEnabled = isEnabled,
        requestPermission = requestPermission,
        openSettings = {
            val intent = Intent().apply {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            settingsLauncher.launch(intent)
        }
    )
}
data class NotificationPermissionState(
    val isEnabled: Boolean,
    val requestPermission: () -> Unit,
    val openSettings: () -> Unit
)