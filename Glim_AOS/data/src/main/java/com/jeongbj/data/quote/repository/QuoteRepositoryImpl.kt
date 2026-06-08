package com.jeongbj.data.quote.repository

import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.network.mapper.toMultipartBody
import com.jeongbj.data.quote.datasource.QuoteRemoteDataSource
import com.jeongbj.data.quote.mapper.toDomain
import com.jeongbj.data.quote.mapper.toRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.repository.QuoteRepository
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteRemoteDataSource: QuoteRemoteDataSource
): QuoteRepository {
    override suspend fun saveQuote(
        createQuote: CreateQuote,
        image: MultipartImage,
    ): Quote =
        quoteRemoteDataSource.saveQuote(createQuote.toRequest(), image.toMultipartBody()!!)
            .unwrap().toDomain()

    override suspend fun generateImage(content: String): ByteArray =
        quoteRemoteDataSource.generateImage(GenerateImageRequest(content)).bytes()

}