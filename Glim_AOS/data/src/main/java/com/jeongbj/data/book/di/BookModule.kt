package com.jeongbj.data.book.di

import com.jeongbj.data.book.repository.BookRepositoryImpl
import com.jeongbj.data.book.usecase.ClearRecentQueryUseCaseImpl
import com.jeongbj.data.book.usecase.GetHomeDataUseCaseImpl
import com.jeongbj.data.book.usecase.GetRecentQueryUseCaseImpl
import com.jeongbj.data.book.usecase.SaveRecentQueryUseCaseImpl
import com.jeongbj.data.book.usecase.SearchBookByIsbn13UseCaseImpl
import com.jeongbj.data.book.usecase.SearchBookUseCaseImpl
import com.jeongbj.domain.book.repository.BookRepository
import com.jeongbj.domain.book.usecase.ClearRecentQueryUseCase
import com.jeongbj.domain.book.usecase.GetHomeDataUseCase
import com.jeongbj.domain.book.usecase.GetRecentQueryUseCase
import com.jeongbj.domain.book.usecase.SaveRecentQueryUseCase
import com.jeongbj.domain.book.usecase.SearchBookByIsbn13UseCase
import com.jeongbj.domain.book.usecase.SearchBookUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(impl: BookRepositoryImpl): BookRepository

    @Binds
    abstract fun bindSearchBookUseCase(impl: SearchBookUseCaseImpl): SearchBookUseCase

    @Binds
    abstract fun bindSearchBookByIsbn13UseCase(impl: SearchBookByIsbn13UseCaseImpl): SearchBookByIsbn13UseCase

    @Binds
    abstract fun bindGetHomeDataUseCase(impl: GetHomeDataUseCaseImpl): GetHomeDataUseCase

    @Binds
    abstract fun bindGetRecentQueryUseCase(impl: GetRecentQueryUseCaseImpl): GetRecentQueryUseCase

    @Binds
    abstract fun bindSaveRecentQueryUseCase(impl: SaveRecentQueryUseCaseImpl): SaveRecentQueryUseCase

    @Binds
    abstract fun bindClearRecentQueryUseCase(impl: ClearRecentQueryUseCaseImpl): ClearRecentQueryUseCase

}