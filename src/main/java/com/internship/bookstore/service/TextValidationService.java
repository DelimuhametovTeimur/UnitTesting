package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TextValidationService {

    public boolean validate(ReviewDto reviewDto) {

        if (!reviewDto.getTextReview().toLowerCase().contains("free") &&
                !reviewDto.getTextReview().toLowerCase().contains("admin") &&
                !reviewDto.getTextReview().toLowerCase().contains("test") &&
                !reviewDto.getTextReview().toLowerCase().contains("unacceptable")) {
            return true;
        }
        else {
            log.warn("Introduced review with text [{}] contains forbidden words", reviewDto.getTextReview());
        }
        return false;
    }
}
