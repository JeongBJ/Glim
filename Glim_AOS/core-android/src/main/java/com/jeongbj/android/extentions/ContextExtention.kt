package com.jeongbj.android.extentions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

fun Context.createImageUri(): Uri {
    val file = File(
        cacheDir,
        "camera_${System.currentTimeMillis()}.jpg"
    )

    return FileProvider.getUriForFile(
        this,
        "$packageName.provider",
        file
    )
}

fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

tailrec fun Context.findActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }