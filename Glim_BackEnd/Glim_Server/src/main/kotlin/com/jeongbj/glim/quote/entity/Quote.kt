package com.jeongbj.glim.quote.entity

import com.jeongbj.glim.book.entity.Book
import com.jeongbj.glim.like.entity.Like
import com.jeongbj.glim.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "t_quote",
    indexes = [
        Index(
            name = "idx_quote_book_seq",
            columnList = "book_seq"
        )
    ])
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

    @Column(name = "num_likes", nullable = false)
    var numLikes: Long = 0,

    @Column(name = "num_views", nullable = false)
    var numViews: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_seq", nullable = false, foreignKey = ForeignKey(name = "fk_quote_book"))
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false, foreignKey = ForeignKey(name = "fk_quote_user"))
    val user: User,

    @OneToMany(mappedBy = "quote", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<Like> = mutableListOf()

) {
    fun increaseLikes() = this.numLikes++

    fun decreaseLikes() = this.numLikes--

    fun increaseView() = this.numViews++

}