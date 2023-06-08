package com.libraryinformation.config;

import com.libraryinformation.util.TokenUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * token拦截器
 */
@Component
@Slf4j
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
            try {
                String strAuth = request.getHeader("Authorization");
//                log.info("Authorization:{}",strAuth);
                if(strAuth == null){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                String jwtToken = strAuth.replace("Bearer ", "");
                if(StringUtils.containsWhitespace(jwtToken)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                //校验jwt
                boolean verifyResult = TokenUtil.validateToken(jwtToken);
                if(!verifyResult){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }

                // 从Token中获取用户信息
                String userInfo = TokenUtil.getUsernameFromToken(jwtToken);
                String role = TokenUtil.getRolesFromToken(jwtToken);
                //将用户角色放入SimpleGrantedAuthority中
                List<SimpleGrantedAuthority> authorityList = Collections.singletonList(new SimpleGrantedAuthority(role));
                //将用户信息放入SecurityContextHolder中
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userInfo, null, authorityList));

                // Token验证通过，继续处理请求
                return true;
            } catch (JwtException e) {
                // Token验证失败，返回401 Unauthorized状态码
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
    }

}
