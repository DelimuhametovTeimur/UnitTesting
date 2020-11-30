package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TextValidationService {

    public boolean validate(ReviewRequestDto reviewRequestDto) {
        if (!reviewRequestDto.getTextReview().toLowerCase().contains("free") &&
                !reviewRequestDto.getTextReview().toLowerCase().contains("admin") &&
                !reviewRequestDto.getTextReview().toLowerCase().contains("test") &&
                !reviewRequestDto.getTextReview().toLowerCase().contains("unacceptable")) {
            return true;
        }
        else {
            log.warn("Introduced review with text [{}] contains forbidden words", reviewRequestDto.getTextReview());
        }
        return false;
    }
}
