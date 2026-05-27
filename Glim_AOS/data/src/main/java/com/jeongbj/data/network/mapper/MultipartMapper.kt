package com.jeongbj.data.network.mapper

import com.google.gson.Gson
import com.jeongbj.core.common.MultipartImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun MultipartImage?.toMultipartBody(): MultipartBody.Part?{
    if(this == null) return null
    val requestBody = bytes.toRequestBody(mimeType.toMediaType())
    return MultipartBody.Part.createFormData("image", fileName, requestBody)
}

fun MultipartImage.toMultipartBody(): MultipartBody.Part{
    val requestBody = bytes.toRequestBody(mimeType.toMediaType())
    return MultipartBody.Part.createFormData("image", fileName, requestBody)
}
private val gson = Gson()
fun Any.toJsonRequestBody(): RequestBody {
    return gson
        .toJson(this)
        .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}