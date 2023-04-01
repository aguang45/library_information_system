package com.libraryinformation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libraryinformation.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface UserDao extends BaseMapper<User> {
    /**
     * 查询所有用户id与用户名
     * @return
     */
    @Select("select uid,uname from user WHERE deleted=0; ")
    List<User> getAll();

    /**
     * 根据id查询用户
     * @param uid
     * @return
     */
    @Select("select * from user where uid = #{uid}")
    User getById(String uid);
}
