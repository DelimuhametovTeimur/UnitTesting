package com.internship.bookstore.controller;

import com.internship.bookstore.api.controller.ReviewRestController;
import com.internship.bookstore.api.dto.ReviewDto;
import com.internship.bookstore.service.ReviewService;
import com.internship.bookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.internship.TestConstants.*;
import static com.internship.bookstore.utils.ReviewTestUtils.*;

import static com.internship.it.controller.BaseController.createExpectedBody;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewRestController.class)
public class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void shouldReturnReviews() throws Exception {

        when(reviewService.getReviews()).thenReturn(Collections.singletonList(REVIEW_DTO_ONE));

        mockMvc.perform(get("/review/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reviewService).getReviews();
    }

    @Test
    @WithMockUser
    public void shouldReturnReview() throws Exception {

        when(reviewService.getReview(any(Long.class))).thenReturn(REVIEW_DTO_ONE);

        mockMvc.perform(get("/review/get/{id}", ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reviewService).getReview(any(Long.class));
    }

    @Test
    @WithMockUser
    public void shouldNotReturnReview() throws Exception {

        when(reviewService.getReview(any(Long.class))).thenReturn(REVIEW_DTO_ONE);

        mockMvc.perform(get("/review/get/{id}", ID_NEGATIVE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
