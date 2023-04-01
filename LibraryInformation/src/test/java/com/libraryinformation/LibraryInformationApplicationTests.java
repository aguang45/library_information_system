package com.libraryinformation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libraryinformation.dao.BookDao;
import com.libraryinformation.dao.BorrowDao;
import com.libraryinformation.dao.UserDao;
import com.libraryinformation.domain.Book;
import com.libraryinformation.domain.Borrow;
import com.libraryinformation.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryInformationApplicationTests {
   /* @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowDao borrowDao;

    @Test
    void testUserInsert() {
        User user = new User();
        user.setUid("1234");
        user.setUname("123");
        user.setPassword("123");
        user.setUidentity("student");
        userDao.insert(user);
    }

    @Test
    void testUserDelete() {
        userDao.deleteById("1234");
    }

    @Test
    void testUserSelectAll() {
        System.out.println(userDao.selectList(null));
    }

    @Test
    void testUserSelectById() {
        System.out.println(userDao.selectById("1234"));
    }

    @Test
    void testBookInsert() {
        Book book = new Book();
        book.setBid("1234");
        book.setBname("123");
        book.setBauthor("123");
        book.setBstatus("123");
        bookDao.insert(book);
    }

    @Test
    void testBorrowDelete(){
        borrowDao.deleteById(1L);
    }

    @Test
    void testBorrowSelectAll(){
        System.out.println(borrowDao.selectList(null));
    }

    @Test
    void testGetByPage(){
        //IPage对象封装了分页操作相关的数据
        IPage page  = new Page(1,3);
        userDao.selectPage(page,null);
        System.out.println("当前页码值："+page.getCurrent());
        System.out.println("每页显示数："+page.getSize());
        System.out.println("一共多少页："+page.getPages());
        System.out.println("一共多少条数据："+page.getTotal());
        System.out.println("数据："+page.getRecords());
    }*/
}
