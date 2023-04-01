package com.libraryinformation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.libraryinformation.domain.Borrow;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
@Transactional
public interface BorrowDao extends BaseMapper<Borrow> {

    /**
     * 借书
     *
     * @param borrow
     * @return
     */
    @Insert("insert into borrow (bid, uid, bdate) values (#{bid}, #{uid}, #{bdate});")
    Integer borrowBook(@RequestBody Borrow borrow);

    /**
     * 还书
     *
     * @param borrow
     * @return
     */
    @Update("update borrow set rdate = #{rdate} where bid = #{bid} and rdate is null")
    Integer returnBook(@RequestBody Borrow borrow);

}
