package com.internship.bookstore.controller;

import com.internship.bookstore.api.controller.ReviewRestController;
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
        when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(REVIEW_RESPONSE_DTO_ONE));
        mockMvc.perform(get("/review/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(Collections.singletonList(REVIEW_RESPONSE_DTO_ONE))));
        verify(reviewService).getAllReviews();
    }

    @Test
    @WithMockUser
    public void shouldReturnReview() throws Exception {
        when(reviewService.getReview(ID_ONE)).thenReturn(REVIEW_RESPONSE_DTO_ONE);
        mockMvc.perform(get("/review/get/" + ID_ONE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(REVIEW_RESPONSE_DTO_ONE)));
        verify(reviewService).getReview(ID_ONE);
    }
}
