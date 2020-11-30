package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.ReviewRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import com.internship.bookstore.utils.exceptions.ReviewNotValidException;
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
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
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
        verifyNoMoreInteractions(bookRepository, reviewRepository, userRepository);
    }

    @Test
    public void shouldReturnReviews() {
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(REVIEW_ONE, REVIEW_TWO));
        final List<ReviewResponseDto> reviewResponseDtos = reviewService.getAllReviews();
        assertTrue(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_ONE));
        assertTrue(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_TWO));
        verify(reviewRepository).findAll();
    }

    @Test
    public void shouldNotReturnReviews() {
        final List<ReviewResponseDto> reviewResponseDtos = reviewService.getAllReviews();
        assertFalse(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_ONE));
        assertFalse(reviewResponseDtos.contains(REVIEW_RESPONSE_DTO_TWO));
        verify(reviewRepository).findAll();
    }

    @Test
    public void shouldReturnReview() {
        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.of(REVIEW_ONE));
        assertEquals(REVIEW_RESPONSE_DTO_ONE, reviewService.getReview(ID_ONE));
        verify(reviewRepository).findById(any(Long.class));
    }

    @Test
    public void shouldNotReturnReview() {
        when(reviewRepository.findById(ID_ONE)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> reviewService.getReview(ID_ONE));
        verify(reviewRepository).findById(ID_ONE);
    }

    @Test
    public void shouldSaveReview() {
        final ReviewRequestDto reviewRequestDto = REVIEW_REQUEST_DTO_ONE;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(USER_ONE));
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(textValidationService.validate(reviewRequestDto)).thenReturn(true);
        when(reviewRepository.save(REVIEW_ONE_NO_ID )).thenReturn(REVIEW_ONE);
        assertEquals(REVIEW_RESPONSE_DTO_ONE, reviewService.save(reviewRequestDto));
        verify(userRepository).findById(ID_ONE);
        verify(bookRepository).findById(ID_ONE);
        verify(textValidationService).validate(reviewRequestDto);
    }

    @Test
    public void shouldNotSaveReviewNoBookFound() {
        final ReviewRequestDto reviewRequestDto = REVIEW_REQUEST_DTO_ONE;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(USER_ONE));
        assertThrows(RecordNotFoundException.class, () -> reviewService.save(reviewRequestDto));
        verify(userRepository).findById(ID_ONE);
        verify(bookRepository).findById(ID_ONE);
    }

    @Test
    public void shouldNotSaveReviewNoUserFound() {
        final ReviewRequestDto reviewRequestDto = REVIEW_REQUEST_DTO_ONE;
        assertThrows(RecordNotFoundException.class, () -> reviewService.save(reviewRequestDto));
        verify(userRepository).findById(ID_ONE);
    }

    @Test
    public void shouldNotSaveReviewNoValidText() {
        final ReviewRequestDto reviewRequestDto = REVIEW_REQUEST_DTO_ONE;
        when(userRepository.findById(ID_ONE)).thenReturn(Optional.of(USER_ONE));
        when(bookRepository.findById(ID_ONE)).thenReturn(Optional.of(BOOK_ONE));
        when(textValidationService.validate(reviewRequestDto)).thenReturn(false);
        assertThrows(ReviewNotValidException.class, () -> reviewService.save(reviewRequestDto));
        verify(userRepository).findById(ID_ONE);
        verify(bookRepository).findById(ID_ONE);
        verify(textValidationService).validate(reviewRequestDto);
    }
}