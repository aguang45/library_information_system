package com.libraryinformation.config;

import com.libraryinformation.util.TokenUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，验证Token
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取Token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                // 解析Token并验证
                new TokenUtil().validateToken(token);
//                Jwts.parser().setSigningKey("mySecretKey").parseClaimsJws(token);
                // Token验证通过，继续处理请求
                return true;
            } catch (JwtException e) {
                // Token验证失败，返回401 Unauthorized状态码
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } else {
            // 如果请求头中没有Token，返回401 Unauthorized状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
