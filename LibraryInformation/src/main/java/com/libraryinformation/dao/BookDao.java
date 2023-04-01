package com.libraryinformation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libraryinformation.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface BookDao extends BaseMapper<Book> {

    /**
     * 查询所有图书id与图书名
     * @return
     */
    @Select("select bid,bname from book WHERE deleted=0; ")
    List<Book> getAll();

    /**
     * 更新图书状态
     * @param bid
     * @return
     */
    @Update("update book set bstatus = '已借出' where bid = #{bid}; ")
    Integer updateBstatusT(String bid);

    /**
     * 更新图书状态
     * @param bid
     * @return
     */
    @Update("update book set bstatus = '未借出' where bid = #{bid}; ")
    Integer updateBstatusF(String bid);

    /**
     * 根据id查询图书
     * @param bid
     * @return
     */
    @Select("select * from book where bid = #{bid}; ")
    Book selectById(String bid);
}
