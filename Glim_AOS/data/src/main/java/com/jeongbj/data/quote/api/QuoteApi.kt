package com.jeongbj.data.quote.api

import com.jeongbj.core.common.BaseResponse
import com.jeongbj.core.common.CursorPage
import com.jeongbj.data.quote.request.CreateQuoteRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.request.QuotePageRequest
import com.jeongbj.data.quote.response.QuoteResponse
import com.jeongbj.domain.quote.model.QuoteCursor
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface QuoteApi {

    @Multipart
    @POST("quote/insert")
    suspend fun saveQuote(
        @Part("request") quoteRequest: CreateQuoteRequest,
        @Part image: MultipartBody.Part)
    : BaseResponse<QuoteResponse>

    @POST("quote/generate")
    @Streaming
    suspend fun generateImage(@Body generateImageRequest: GenerateImageRequest)
    : ResponseBody

    @POST("quote")
    suspend fun getQuotes(@Body quotePageRequest: QuotePageRequest): BaseResponse<CursorPage<QuoteResponse, QuoteCursor>>

    @GET("quote/{quoteSeq}")
    suspend fun getQuote(@Path("quoteSeq") quoteSeq: Long): BaseResponse<QuoteResponse>

    @GET("quote/view/{quoteSeq}")
    suspend fun increaseView(@Path("quoteSeq") quoteSeq: Long): BaseResponse<Unit>

    @GET("like/{quoteSeq}")
    suspend fun likeQuote(@Path("quoteSeq") quoteSeq: Long): BaseResponse<Unit>

    @DELETE("quote/{quoteSeq}")
    suspend fun deleteQuote(@Path("quoteSeq") quoteSeq: Long): BaseResponse<Unit>
}