package com.jeongbj.glim.quote.repository

import com.jeongbj.glim.quote.entity.Quote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository: JpaRepository<Quote, Long> {

}