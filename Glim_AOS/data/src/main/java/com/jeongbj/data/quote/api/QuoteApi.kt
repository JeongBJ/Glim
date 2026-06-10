package com.jeongbj.data.quote.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.response.QuoteResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Streaming

interface QuoteApi {

    @Multipart
    @POST("quote")
    suspend fun saveQuote(
        @Part("request") quoteRequest: CreateQuoteRequest,
        @Part image: MultipartBody.Part)
    : BaseResponse<QuoteResponse>

    @POST("quote/generate")
    @Streaming
    suspend fun generateImage(@Body generateImageRequest: GenerateImageRequest)
    : ResponseBody
}