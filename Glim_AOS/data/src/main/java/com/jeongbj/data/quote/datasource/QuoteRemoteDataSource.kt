package com.jeongbj.data.quote.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.quote.api.QuoteApi
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.request.QuotePageRequest
import com.jeongbj.data.quote.response.QuoteCursorResponse
import com.jeongbj.data.quote.response.QuoteResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

class QuoteRemoteDataSource @Inject constructor(
    private val quoteApi: QuoteApi
){
    suspend fun saveQuote(quoteRequest: CreateQuoteRequest, image: MultipartBody.Part)
    : BaseResponse<QuoteResponse> =
        quoteApi.saveQuote(quoteRequest, image)

    suspend fun generateImage(generateImageRequest: GenerateImageRequest)
    : ResponseBody =
        quoteApi.generateImage(generateImageRequest)

    suspend fun getQuotes(quotePageRequest: QuotePageRequest)
    : BaseResponse<CursorPage<QuoteResponse, QuoteCursorResponse>> =
        quoteApi.getQuotes(quotePageRequest)

    suspend fun getQuote(quoteSeq: Long): BaseResponse<QuoteResponse> =
        quoteApi.getQuote(quoteSeq)

    suspend fun increaseView(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.increaseView(quoteSeq)

    suspend fun likeQuote(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.likeQuote(quoteSeq)

    suspend fun deleteQuote(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.deleteQuote(quoteSeq)

    suspend fun getLockScreenQuotes(quotePageRequest: QuotePageRequest) =
        quoteApi.getLockScreenQuotes(quotePageRequest)
}