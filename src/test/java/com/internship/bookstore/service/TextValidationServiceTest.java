package com.internship.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.internship.bookstore.utils.ReviewTestUtils.REVIEW_DTO_ONE;
import static com.internship.bookstore.utils.ReviewTestUtils.REVIEW_DTO_TWO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TextValidationServiceTest {

    @InjectMocks
    private TextValidationService textValidationService;

    @Test
    public void shouldReturnTrueValidation() {
        assertTrue(textValidationService.validate(REVIEW_DTO_ONE));
    }

    @Test
    public void shouldReturnFalseValidation() {
        assertFalse(textValidationService.validate(REVIEW_DTO_TWO));
    }
}