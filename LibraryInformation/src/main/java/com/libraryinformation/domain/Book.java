package com.libraryinformation.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Book {
    @TableId(value = "bid")
    private String bid;
    private String bname;
    private String bauthor;
    private String bstatus;
    // 逻辑删除0:未删除 1:已删除
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
