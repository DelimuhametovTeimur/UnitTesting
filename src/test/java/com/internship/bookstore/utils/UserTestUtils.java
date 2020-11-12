package com.internship.bookstore.utils;

import com.internship.bookstore.model.User;

import static com.internship.TestConstants.*;

public class UserTestUtils {
    public static final User USER_ONE = User
            .builder()
            .id(ID_ONE)
            .email(AUTH_USER_EMAIL)
            .password(AUTH_USER_PASSWORD)
            .build();
}
