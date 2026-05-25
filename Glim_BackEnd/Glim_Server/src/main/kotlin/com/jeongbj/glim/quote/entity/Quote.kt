package com.jeongbj.glim.quote.entity

import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "t_quote")
class Quote (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val quoteSeq: Long = 0,

    @Column(name = "image_url", nullable = false)
    val imageUrl: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "num_likes", nullable = false
    )
    var numLikes: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_seq", nullable = false, foreignKey = ForeignKey(name = "fk_quote_book"))
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false, foreignKey = ForeignKey(name = "fk_quote_user"))
    val user: User

) {
    fun increaseLikes() = this.numLikes++

    fun decreaseLikes() = this.numLikes--

}