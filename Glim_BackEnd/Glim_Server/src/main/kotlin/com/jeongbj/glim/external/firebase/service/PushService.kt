package com.jeongbj.glim.external.firebase.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.jeongbj.glim.quote.entity.Quote
import com.jeongbj.glim.user.repository.FcmTokenRepository
import org.springframework.stereotype.Service

@Service
class PushService(
    private val fcmTokenRepository: FcmTokenRepository
) {

    fun notifyQuoteLiked(fromUserSeq: Long, quote: Quote) {
        if (fromUserSeq == quote.user.userSeq) return
        println(quote.user.fcmTokens)

        quote.user.fcmTokens
            .filter { it.pushEnabled }
            .forEach { fcmToken ->
                runCatching {
                    sendLikeNotification(
                        token = fcmToken.token,
                        quoteSeq = quote.quoteSeq
                    )
                }.onFailure {
                    fcmTokenRepository.delete(fcmToken)
                }
            }
    }

    private fun sendLikeNotification(
        token: String,
        quoteSeq: Long
    ) {
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