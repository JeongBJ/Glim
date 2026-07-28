package com.jeongbj.presentation.feature.post.ocr

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class MLKitTextRecognizer {
    private val recognizer by lazy {
        TextRecognition.getClient(
            KoreanTextRecognizerOptions.Builder().build()
        )
    }
    suspend fun recognizeText(bitmap: Bitmap): String {
        return recognizer
            .process(InputImage.fromBitmap(bitmap, 0))
            .await()
            .text
    }
}