package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.Review;
import com.internship.bookstore.model.User;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import com.internship.bookstore.utils.exceptions.ReviewNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.internship.bookstore.utils.mappers.ReviewMapper.mapReviewRequestDtoToReview;
import static com.internship.bookstore.utils.mappers.ReviewMapper.mapReviewToReviewResponseDto;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

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
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(mapReviewToReviewResponseDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            log.warn("Review with with id [{}] was not found in database", id);
            return new RecordNotFoundException(format(messageReviewNotFound, id));
        });
        return mapReviewToReviewResponseDto.apply(review);
    }

    public ReviewResponseDto save(ReviewRequestDto reviewRequestDto) {
        log.info("Saving review for book with: id [{}]", reviewRequestDto.getBookId());
        User user = userRepository.findById(reviewRequestDto.getUserId()).orElseThrow(
                () -> { log.warn("User with id [{}] was not found in the database",
                        reviewRequestDto.getUserId());
                    return new RecordNotFoundException(
                            format(messageUserNotFound, reviewRequestDto.getUserId()));
                });
        Book book = bookRepository.findById(reviewRequestDto.getBookId()).orElseThrow(
                () -> { log.warn("Book with id [{}] was not found in the database",
                        reviewRequestDto.getBookId());
                    return new RecordNotFoundException(
                            format(messageBookNotFound, reviewRequestDto.getBookId()));
                });
        if(textValidationService.validate(reviewRequestDto)) {
            Review review = mapReviewRequestDtoToReview.apply(reviewRequestDto);
            review.setBook(book);
            review.setUser(user);
            review = reviewRepository.save(review);
            return mapReviewToReviewResponseDto.apply(review);
        }
        throw new ReviewNotValidException("Introduced review contains forbidden words like 'admin', 'free', 'test' " +
                "and 'unacceptable'");
    }
}
