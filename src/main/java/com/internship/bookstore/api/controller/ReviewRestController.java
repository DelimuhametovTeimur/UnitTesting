package com.internship.bookstore.api.controller;

import com.internship.bookstore.api.dto.ReviewRequestDto;
import com.internship.bookstore.api.dto.ReviewResponseDto;
import com.internship.bookstore.api.exchange.Response;
import com.internship.bookstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewRestController {

    public final ReviewService reviewService;

    @GetMapping("/getAll")
    public ResponseEntity<Response<List<ReviewResponseDto>>> getAllReviews() {
        return ResponseEntity.ok(Response.build(reviewService.getAllReviews()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response<ReviewResponseDto>> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(Response.build(reviewService.getReview(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<Response<ReviewResponseDto>> addReview(
            @RequestBody @Valid ReviewRequestDto reviewRequestDto, Errors validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }
        return ResponseEntity.ok(Response.build(reviewService.save(reviewRequestDto)));
    }
}