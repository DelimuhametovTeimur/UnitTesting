package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewDto;
import com.internship.bookstore.model.Review;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static com.internship.TestConstants.ID_ONE;
import static com.internship.TestConstants.ID_TWO;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_TWO;
import static com.internship.bookstore.utils.ReviewTestUtils.*;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TextValidationService textValidationService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(reviewService, "messageUserNotFound",
                "User with id %s was not found");
        ReflectionTestUtils.setField(reviewService, "messageBookNotFound",
                "Book with id %s was not found");
        ReflectionTestUtils.setField(reviewService, "messageReviewNotFound",
                "Review with id %s not found");
    }

    @AfterEach
    void tearDown(){

        verifyNoMoreInteractions(bookRepository, reviewRepository);
        verifyNoMoreInteractions(userRepository, reviewRepository);
    }

    @Test
    public void shouldReturnReviews() {

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(REVIEW_ONE, REVIEW_TWO));

        final List<Review> reviews = reviewRepository.findAll();

        assertTrue(reviews.contains(REVIEW_ONE));
        assertTrue(reviews.contains(REVIEW_TWO));
    }

    @Test
    public void shouldNotReturnReviews() {

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(REVIEW_ONE));

        final List<Review> reviews = reviewRepository.findAll();

        assertFalse(reviews.contains(REVIEW_TWO));
    }

    @Test
    public void shouldReturnReview() {

        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.of(REVIEW_ONE));

        final ReviewDto reviewDto = REVIEW_DTO_ONE;
        final ReviewDto newReviewDto = reviewService.getReview(ID_ONE);

        assertEquals(reviewDto, newReviewDto);

        verify(reviewRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldNotReturnReview() {

        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {reviewService.getReview(ID_ONE);});
    }

    @Test
    public void shouldAddReview() {

        final ReviewDto reviewDto = REVIEW_DTO_ONE;
        assertEquals(reviewDto.getBookId(), BOOK_ONE.getId());
        assertEquals(reviewDto.getUserId(), USER_ONE.getId());
        textValidationService.validate(reviewDto);
    }

    @Test
    public void shouldNotAddReviewNoUserFound() {

        final ReviewDto reviewDto = REVIEW_DTO_ONE;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(USER_ONE));
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {reviewService.addReview(reviewDto);});
    }

    @Test
    public void shouldNotAddReviewNoBookFound() {

        final ReviewDto reviewDto = REVIEW_DTO_ONE;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {reviewService.addReview(reviewDto);});
    }

    @Test
    public void shouldNotAddReviewNoValidText() {

        final ReviewDto reviewDto = REVIEW_DTO_TWO;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(USER_ONE));
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));

        assertThrows(RecordNotFoundException.class, () -> {reviewService.addReview(reviewDto);});
    }
}