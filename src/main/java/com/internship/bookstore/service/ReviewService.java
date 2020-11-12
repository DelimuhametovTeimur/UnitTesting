package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewDto;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.Review;
import com.internship.bookstore.model.User;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {

    public final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    public final UserRepository userRepository;
    public final TextValidationService textValidationService;

    @Value("${message.user.id.not-found}")
    private String messageUserNotFound;

    @Value("${message.book.not-found}")
    private String messageBookNotFound;

    @Value("${message.review.not-found}")
    private String messageReviewNotFound;

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviews() {

        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review: reviewRepository.findAll()) {
            reviewDtos.add(new ReviewDto(review));
        }
        return reviewDtos;
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            log.warn("Review with with id [{}] was not found in database", id);
            return new RecordNotFoundException(format(messageReviewNotFound, id));
        });
        return new ReviewDto(review);
    }

    public void addReview(ReviewDto reviewDto) {

        User user = userRepository.findById(reviewDto.getUserId()).orElseThrow(
                () -> { log.warn("User with id [{}] was not found in database",
                        reviewDto.getUserId());
                    return new RecordNotFoundException(
                            format(messageUserNotFound, reviewDto.getUserId()));
                });

        Book book = bookRepository.findById(reviewDto.getBookId()).orElseThrow(
                () -> { log.warn("Book with id [{}] was not found in database",
                        reviewDto.getBookId());
                    return new RecordNotFoundException(
                            format(messageBookNotFound, reviewDto.getBookId()));
                });

        if(textValidationService.validate(reviewDto)) {
            Review review = new Review(reviewDto, book, user);
            reviewRepository.save(review);
        }
        throw new RecordNotFoundException(format("Introduced review with text contains forbidden words"));
    }
}
