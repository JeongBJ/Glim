package com.jeongbj.data.quote.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.response.QuoteResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface QuoteApi {

    @Multipart
    @POST
    suspend fun saveQuote(
        @Part("request") quoteRequest: CreateQuoteRequest,
        @Part("image") image: MultipartBody.Part)
    : BaseResponse<QuoteResponse>

    @POST
    suspend fun generateImage(@Body generateImageRequest: GenerateImageRequest)
    : BaseResponse<ByteArray>
}