package com.jeongbj.data.quote.datasource

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.quote.api.QuoteApi
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.request.QuotePageRequest
import com.jeongbj.data.quote.response.QuoteResponse
import com.jeongbj.domain.quote.model.QuoteCursor
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
    : BaseResponse<CursorPage<QuoteResponse, QuoteCursor>> =
        quoteApi.getQuotes(quotePageRequest)

    suspend fun getQuote(quoteSeq: Long): BaseResponse<QuoteResponse> =
        quoteApi.getQuote(quoteSeq)

    suspend fun increaseView(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.increaseView(quoteSeq)

    suspend fun likeQuote(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.likeQuote(quoteSeq)

    suspend fun blockQuote(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.blockQuote(quoteSeq)

    suspend fun deleteQuote(quoteSeq: Long): BaseResponse<Unit> =
        quoteApi.deleteQuote(quoteSeq)
}