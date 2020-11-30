package com.internship.bookstore.utils;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Review;

import static com.internship.TestConstants.*;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;

public class ReviewTestUtils {

    public static final Review REVIEW_ONE = Review
            .builder()
            .id(ID_ONE)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final Review REVIEW_ONE_NO_ID = Review
            .builder()
            .book(BOOK_ONE)
            .user(USER_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final Review REVIEW_TWO = Review
            .builder()
            .id(ID_TWO)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewResponseDto REVIEW_RESPONSE_DTO_ONE = ReviewResponseDto
            .builder()
            .id(ID_ONE)
            .bookId(ID_ONE)
            .userId(ID_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewResponseDto REVIEW_RESPONSE_DTO_TWO = ReviewResponseDto
            .builder()
            .id(ID_TWO)
            .bookId(ID_ONE)
            .userId(ID_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewRequestDto REVIEW_REQUEST_DTO_ONE = ReviewRequestDto
            .builder()
            .bookId(BOOK_ONE.getId())
            .userId(USER_ONE.getId())
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewRequestDto REVIEW_REQUEST_DTO_TWO = ReviewRequestDto
            .builder()
            .bookId(BOOK_ONE.getId())
            .userId(USER_ONE.getId())
            .textReview(TEXT_REVIEW_INCORRECT)
            .build();
}