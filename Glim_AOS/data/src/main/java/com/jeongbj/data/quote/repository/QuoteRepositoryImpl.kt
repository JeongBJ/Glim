package com.jeongbj.data.quote.repository

import com.jeongbj.core.common.CursorPage
import com.jeongbj.core.common.MultipartImage
import com.jeongbj.core.common.unwrap
import com.jeongbj.data.network.mapper.toMultipartBody
import com.jeongbj.data.quote.datasource.QuoteRemoteDataSource
import com.jeongbj.data.quote.mapper.toDomain
import com.jeongbj.data.quote.mapper.toRequest
import com.jeongbj.data.quote.request.GenerateImageRequest
import com.jeongbj.data.quote.request.QuotePageRequest
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.model.QuoteCursor
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

    override suspend fun getQuotes(
        seed: Long?,
        cursor: QuoteCursor?,
        size: Int,
    ): CursorPage<Quote, QuoteCursor> {
        val result = quoteRemoteDataSource.getQuotes(QuotePageRequest(
            seed = seed,
            cursor = cursor,
            size = size
        ))

        return CursorPage(
            items = result.data.items.map { it.toDomain() },
            hasNext = result.data.hasNext,
            nextCursor = result.data.nextCursor?.toDomain(),
            seed = result.data.seed
        )
    }

    override suspend fun getQuote(quoteSeq: Long): Quote =
        quoteRemoteDataSource.getQuote(quoteSeq)
            .unwrap().toDomain()

    override suspend fun increaseView(quoteSeq: Long) =
        quoteRemoteDataSource.increaseView(quoteSeq).unwrap()

    override suspend fun likeQuote(quoteSeq: Long) =
        quoteRemoteDataSource.likeQuote(quoteSeq).unwrap()

    override suspend fun deleteQuote(quoteSeq: Long) =
        quoteRemoteDataSource.deleteQuote(quoteSeq).unwrap()

}