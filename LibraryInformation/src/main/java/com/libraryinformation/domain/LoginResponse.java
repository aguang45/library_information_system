package com.libraryinformation.domain;

import lombok.Data;

/**
 * 用于登录成功后返回的数据
 */
@Data
public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
