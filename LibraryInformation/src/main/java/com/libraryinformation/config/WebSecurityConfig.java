package com.libraryinformation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryinformation.controller.Code;
import com.libraryinformation.controller.Result;
import com.libraryinformation.domain.LoginResponse;
import com.libraryinformation.domain.User;
import com.libraryinformation.domain.UserSecurity;
import com.libraryinformation.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * spring security配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/checkCode").permitAll()  //验证码
                .mvcMatchers("/users/register").permitAll() //注册
                .mvcMatchers("/users/id/**").permitAll()    //根据id查询用户是否存在
                .anyRequest().authenticated();  //其他请求都需要认证
        http.csrf().disable(); //关闭csrf
        //设置登录成功的处理器
        http.formLogin().successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    //获取登录成功的用户信息
                    User user = ((UserSecurity) authentication.getPrincipal()).getUser();
                    //清除密码和手机号
                    user.setPassword(null);
                    user.setUphone(null);
                    //生成token
                    String token = TokenUtil.createToken(user);
//                    log.info(token);
                    //将token返回给前端
                    Result httpResult = new Result(Code.LOGIN_OK, new LoginResponse(token, user), "登录成功");
                    String responseJson = objectMapper.writeValueAsString(httpResult);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.println(responseJson);
                    writer.flush();
                }) //配置认证成功处理器
                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) -> {
                    Result httpResult = new Result(Code.LOGIN_ERR, null,"登录失败");
                    String responseJson = objectMapper.writeValueAsString(httpResult);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    writer.println(responseJson);
                    writer.flush();
                }) //配置认证失败处理器
                .permitAll(); //配置登录接口
        //配置访问拒绝处理器
        http.exceptionHandling().accessDeniedHandler((HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            Result httpResult = new Result(401,  null,"权限不足");
            String responseJson = objectMapper.writeValueAsString(httpResult);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.println(responseJson);
            writer.flush();
        });
    }

    //配置密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
