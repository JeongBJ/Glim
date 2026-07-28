package com.jeongbj.android.image

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

fun Bitmap.toJpegByteArray(
    quality: Int = 90,
): ByteArray {
    return ByteArrayOutputStream().use { stream ->
        compress(
            Bitmap.CompressFormat.JPEG,
            quality,
            stream
        )
        stream.toByteArray()
    }
}

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