package com.libraryinformation.config;

import com.libraryinformation.util.TokenUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
                String username = TokenUtil.getUsernameFromToken(token);
                String rolesFromToken = TokenUtil.getRolesFromToken(token);
                if (username == null) {
                    throw new JwtException("Invalid token");
                }

                // 将用户信息存储到 HttpSession
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("roles", rolesFromToken);

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
