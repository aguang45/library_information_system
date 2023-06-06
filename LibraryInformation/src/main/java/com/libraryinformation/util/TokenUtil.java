package com.libraryinformation.util;

import com.libraryinformation.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * token工具类，用于生成token和验证token
 */
public class TokenUtil {
    // 密钥
    static String secret = "LibraryInformation";

    /**
     * 生成token
     *
     * @param user 用户对象
     * @return 返回生成的token
     */
    public String createToken(User user) {
        long now = System.currentTimeMillis(); // 当前时间戳
        long expire = now + 1000L * 60 * 60; // 过期时间（1小时）
        String token = Jwts.builder()
                .setSubject(user.getUname()) // 设置主题（用户名）
                .claim("roles", user.getUidentity()) // 存储用户权限信息
                .setIssuedAt(new Date(now)) // 设置签发时间
                .setExpiration(new Date(expire)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secret) // 设置签名算法和密钥
                .compact();
        return token;
    }

    /**
     * 验证token，并解析出用户名
     *
     * @param token 需要验证的token
     * @return 返回提取出的用户名
     */
    public static String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证token是否有效
     *
     * @param token 需要验证的token
     * @return 返回是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取用户权限信息
     *
     * @param token 需要提取用户权限信息的token
     * @return 返回提取出的用户权限信息
     */
    public static String getRolesFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return (String) claims.get("roles");
        } catch (Exception e) {
            return null;
        }
    }
}
