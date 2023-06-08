package com.libraryinformation.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class User {
    @TableId(value = "uid")
    private String uid;
    private String uname;
    private String password;
    private String uphone;
    private String uidentity;
    //逻辑删除,1标记删除，0未删除
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;
}
