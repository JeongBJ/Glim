package com.jeongbj.glim.external.firebase.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.jeongbj.glim.quote.entity.Quote
import org.springframework.stereotype.Service

@Service
class PushService {

    fun notifyQuoteLiked(fromUserSeq: Long, quote: Quote) {
        if (fromUserSeq == quote.user.userSeq) return

        quote.user.fcmTokens
            .filter { it.pushEnabled }
            .forEach {
                sendLikeNotification(
                    token = it.token,
                    quoteSeq = quote.quoteSeq
                )
            }
    }

    private fun sendLikeNotification(
        token: String,
        quoteSeq: Long
    ) {
        println(token)
        val message = Message.builder()
            .setToken(token)
            .putData("type", "LIKE")
            .putData("quoteSeq", quoteSeq.toString())
            .putData("title", "좋아요")
            .putData("body", "누군가 회원님의 글을 좋아했습니다.")
            .build()
        FirebaseMessaging.getInstance().send(message)
    }
}