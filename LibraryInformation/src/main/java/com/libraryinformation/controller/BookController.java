package com.libraryinformation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libraryinformation.dao.BookDao;
import com.libraryinformation.domain.Book;
import com.libraryinformation.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookDao bookDao;

    /**
     * 保存图书
     * @param book
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Book book) {
        int flag = bookDao.insert(book);
        return new Result(flag == 1 ? Code.SAVE_OK:Code.SAVE_ERR,flag);
    }

    /**
     * 更新图书
     * @param book
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Book book) {
        int flag = bookDao.updateById(book);
        return new Result(flag == 1 ? Code.UPDATE_OK:Code.UPDATE_ERR,flag);
    }

    /**
     * 删除图书
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        int flag = bookDao.deleteById(id);
        return new Result(flag == 1 ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }

    /**
     * 根据id查询图书
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        Book book = bookDao.selectById(id);
        Integer code = book != null ? Code.GET_OK : Code.GET_ERR;
        String msg = book != null ? "" : "数据查询失败，请重试！";
        return new Result(code,book,msg);
    }

    /**
     * 根据条件查询图书，并分页
     * @param page
     * @param size
     * @param bname
     * @param bauthor
     * @param bstatus
     * @return
     */
    @GetMapping("/page")
    public IPage<Book> getByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) String bid,
                                 @RequestParam(required = false) String bname,
                                 @RequestParam(required = false) String bauthor,
                                 @RequestParam(required = false) String bstatus
    ) {
        IPage<Book> userPage = new Page<>(page, size);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if (bid != null && !"".equals(bid)) {
            wrapper.like("bid", bid);
        }
        if (bname != null && !"".equals(bname)) {
            wrapper.like("bname", bname);
        }
        if (bauthor != null && !"".equals(bauthor)) {
            wrapper.like("bauthor", bauthor);
        }
        if (bstatus != null && !"".equals(bstatus)) {
            wrapper.like("bstatus", bstatus);
        }
        bookDao.selectPage(userPage, wrapper);
        return userPage;
    }

    /**
     * 查询所有图书
     * @return
     */
    @GetMapping
    public Result getAll() {
        List<Book> bookList = bookDao.getAll();
        Integer code = bookList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = bookList != null ? "" : "数据查询失败，请重试！";
        return new Result(code,bookList,msg);
    }

    /**
     * 批量删除图书
     * @param ids
     * @return
     */
    @DeleteMapping("/ids")
    public Result deleteByIds(@RequestBody List<String> ids) {
        int flag = bookDao.deleteBatchIds(ids);
        return new Result(flag == ids.size() ? Code.DELETE_OK:Code.DELETE_ERR,flag);
    }
}
