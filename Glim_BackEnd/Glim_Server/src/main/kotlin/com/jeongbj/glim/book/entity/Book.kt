package com.jeongbj.glim.book.entity

import com.jeongbj.glim.quote.entity.Quote
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "t_book",
    indexes = [
        Index(name = "idx_book_title", columnList = "title"),
        Index(name = "idx_book_author", columnList = "author"),
        Index(name = "idx_book_isbn13", columnList = "isbn13")
    ]
)
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bookSeq: Long = 0,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "link_url", nullable = false)
    val linkUrl: String,

    @Column(name = "cover_url", nullable = false)
    val coverUrl: String,

    @Column(name = "author", nullable = false)
    val author: String,

    @Column(name = "translator")
    val translator: String?,

    @Column(name = "isbn13", nullable = false, unique = true)
    val isbn13: String,

    @Column(name = "description", columnDefinition = "TEXT")
    val description: String?,

    @Column(name = "pub_date", nullable = false)
    val pubDate: LocalDate,

    @Column(name = "price_sales", nullable = false)
    val priceSales: Int,

    @Column(name = "category_id", nullable = false)
    val categoryId: Int,

    @Column(name = "category_name", nullable = false)
    val categoryName: String,

    @Column(name = "publisher", nullable = false)
    val publisher: String,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "view_count", nullable = false)
    var viewCount: Long = 0,

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val quotes: MutableList<Quote> = mutableListOf()

) {
    fun increaseViewCount() {
        this.viewCount++
    }
}