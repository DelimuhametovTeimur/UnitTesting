package com.internship.bookstore.api.dto;

import com.internship.bookstore.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long bookId;
    private Long userId;
    private String textReview;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.bookId = review.getBook().getId();
        this.userId = review.getUser().getId();
        this.textReview = review.getTextReview();
    }
}
