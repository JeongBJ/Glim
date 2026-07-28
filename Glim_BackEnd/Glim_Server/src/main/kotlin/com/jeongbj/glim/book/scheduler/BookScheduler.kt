package com.jeongbj.glim.book.scheduler

import com.jeongbj.glim.book.service.BookService
import org.jvnet.hk2.annotations.Service
import org.springframework.scheduling.annotation.Scheduled

@Service
class BookScheduler(
    private val bookService: BookService
){
    @Scheduled(cron = "0 0 6 * * *")
    fun refreshItemListCache() {
        bookService.getHomeItemList()
    }
}