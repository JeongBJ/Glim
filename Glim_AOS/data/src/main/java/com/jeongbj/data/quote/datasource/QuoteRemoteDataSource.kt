package com.jeongbj.data.quote.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.data.quote.api.QuoteApi
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.response.QuoteResponse
import okhttp3.MultipartBody
import javax.inject.Inject

class QuoteRemoteDataSource @Inject constructor(
    private val quoteApi: QuoteApi
){
    suspend fun saveQuote(quoteRequest: CreateQuoteRequest, image: MultipartBody.Part)
    : BaseResponse<QuoteResponse> =
        quoteApi.saveQuote(quoteRequest, image)

    suspend fun generateImage(generateImageRequest: GenerateImageRequest)
    : BaseResponse<ByteArray> =
        quoteApi.generateImage(generateImageRequest)
}