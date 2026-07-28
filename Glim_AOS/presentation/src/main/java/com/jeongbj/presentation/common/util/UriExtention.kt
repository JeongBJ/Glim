package com.jeongbj.presentation.common.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.jeongbj.core.common.MultipartImage

fun Uri.toMultipartImage(context: Context): MultipartImage {
    val contentResolver = context.contentResolver

    val bytes = contentResolver
        .openInputStream(this)
        ?.readBytes()
        ?: error("Cannot read image")

    val mimeType = contentResolver.getType(this)
        ?: "image/jpeg"

    val extension = MimeTypeMap
        .getSingleton()
        .getExtensionFromMimeType(mimeType)
        ?: "jpg"

    val fileName = "${System.currentTimeMillis()}.$extension"

    return MultipartImage(
        bytes = bytes,
        fileName = fileName,
        mimeType = mimeType
    )
}