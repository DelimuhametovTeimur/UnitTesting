package com.internship.bookstore.api.controller;

import com.internship.bookstore.api.dto.ReviewDto;
import com.internship.bookstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewRestController {

    public final ReviewService reviewService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllReviews() {
        return new ResponseEntity<>(reviewService.getReviews(), OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getReview(@PathVariable Long id) {
        if(id >= 0)
        {
            return new ResponseEntity<>(reviewService.getReview(id), OK);
        }
        else {
            return new ResponseEntity<>("Incorrect id value", BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
        return new ResponseEntity<>("Review saved successfully", CREATED);
    }
}