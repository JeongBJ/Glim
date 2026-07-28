package com.jeongbj.presentation.common.notification

import android.content.Context
import androidx.core.content.edit

private const val PREF_NAME = "permission_prefs"
private const val KEY_NOTIFICATION_REQUESTED = "notification_permission_requested"

fun Context.hasRequestedNotificationPermissionBefore(): Boolean {
    val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(KEY_NOTIFICATION_REQUESTED, false)
}

fun Context.markNotificationPermissionRequested() {
    val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    prefs.edit { putBoolean(KEY_NOTIFICATION_REQUESTED, true) }
}