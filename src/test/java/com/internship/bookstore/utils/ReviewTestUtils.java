package com.internship.bookstore.utils;

import com.internship.bookstore.api.dto.ReviewDto;
import com.internship.bookstore.model.Review;

import static com.internship.TestConstants.*;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_TWO;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;

public class ReviewTestUtils {

    public static final Review REVIEW_ONE = Review
            .builder()
            .id(ID_ONE)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final Review REVIEW_TWO = Review
            .builder()
            .id(ID_TWO)
            .book(BOOK_TWO)
            .user(USER_ONE)
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewDto REVIEW_DTO_ONE = ReviewDto
            .builder()
            .id(ID_ONE)
            .bookId(BOOK_ONE.getId())
            .userId(USER_ONE.getId())
            .textReview(TEXT_REVIEW_CORRECT)
            .build();

    public static final ReviewDto REVIEW_DTO_TWO = ReviewDto
            .builder()
            .id(ID_TWO)
            .bookId(BOOK_ONE.getId())
            .userId(USER_ONE.getId())
            .textReview(TEXT_REVIEW_INCORRECT)
            .build();
}