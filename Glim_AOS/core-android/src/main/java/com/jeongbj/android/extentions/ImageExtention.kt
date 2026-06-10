package com.jeongbj.android.extentions

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

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