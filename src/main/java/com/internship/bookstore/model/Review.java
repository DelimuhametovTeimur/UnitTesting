package com.internship.bookstore.model;

import com.internship.bookstore.api.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@Builder(toBuilder = true)
public class Review {

    @Id
    @Column
    @SequenceGenerator(name = "review_id_generator", sequenceName = "seq_review", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text_review")
    private String textReview;

    public Review(ReviewResponseDto reviewResponseDto, Book book, User user) {
        this.id = reviewResponseDto.getId();
        this.book = book;
        this.user = user;
        this.textReview = reviewResponseDto.getTextReview();
    }
}
