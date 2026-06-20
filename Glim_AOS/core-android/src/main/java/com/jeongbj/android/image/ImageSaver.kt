package com.jeongbj.android.image

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.flowResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageSaver @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    fun saveImageToGallery(
        imageUrl: String,
        fileName: String = "glim_${System.currentTimeMillis()}.jpg",
    ): Flow<ResultType<Uri>> = flowResult {
        val values = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                fileName
            )
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "${Environment.DIRECTORY_PICTURES}/Glim"
            )
            put(
                MediaStore.Images.Media.MIME_TYPE,
                "image/jpeg"
            )
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw IOException("이미지 저장에 실패했습니다.")

        try {
            URL(imageUrl).openStream().use { input ->
                val output = context.contentResolver.openOutputStream(uri)
                    ?: throw IOException("OutputStream 생성 실패")
                output.use {
                    input.copyTo(it)
                }
            }
            uri
        } catch (e: Exception) {
            context.contentResolver.delete(
                uri,
                null,
                null
            )
            throw e
        }
    }
}