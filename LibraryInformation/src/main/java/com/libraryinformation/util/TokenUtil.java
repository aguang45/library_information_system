package com.libraryinformation.util;

import com.libraryinformation.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * token工具类，用于生成token和验证token
 */
public class TokenUtil {
    // 密钥
    String secret = "LibraryInformation";
    public String createToken(String str) {
        long now = System.currentTimeMillis(); // 当前时间戳
        long expire = now + 1000 * 60 * 60; // 过期时间（1小时）
        String token = Jwts.builder()
                .setSubject(str.toString()) // 设置主题（用户ID）
                .setIssuedAt(new Date(now)) // 设置签发时间
                .setExpiration(new Date(expire)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secret) // 设置签名算法和密钥
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
