package com.libraryinformation.domain;

import lombok.Data;

/**
 * 用于接收注册的请求
 */
@Data
public class RegisterRequest {
    private User user;
    private String checkCode;
}
