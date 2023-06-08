package com.libraryinformation.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.libraryinformation.dao.UserDao;
import com.libraryinformation.domain.User;
import com.libraryinformation.domain.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现UserDetailsService接口，用于加载用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("loadUserByUsername: " + username);
        User user = userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUid, username));
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        } else {
            //将用户信息封装到UserDetail中
            UserSecurity userDetail = new UserSecurity(user);
            //将用户身份封装到UserDetail中
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(user.getUidentity()));
            userDetail.setAuthorities(authorityList);

//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));

            return userDetail;
        }

    }
}
