package com.libraryinformation.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Borrow {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String bid;
    private String uid;
    private String bdate;
    private String rdate;
    // 逻辑删除0:未删除 1:已删除
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

}
