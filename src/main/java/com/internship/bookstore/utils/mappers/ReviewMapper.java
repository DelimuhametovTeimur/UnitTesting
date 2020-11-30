package com.internship.bookstore.utils.mappers;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Review;
import lombok.NoArgsConstructor;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReviewMapper {

    public static final Function<Review, ReviewResponseDto> mapReviewToReviewResponseDto = review -> ReviewResponseDto.builder()
            .id(review.getId())
            .bookId(review.getBook().getId())
            .userId(review.getUser().getId())
            .textReview(review.getTextReview())
            .build();

    public static final Function<ReviewRequestDto, Review> mapReviewRequestDtoToReview = reviewRequestDto ->
            Review.builder()
                    .textReview(reviewRequestDto.getTextReview())
                    .build();
}
