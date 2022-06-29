package com.atguigu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class User {
    //@TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private Integer age;
    private String email;

    //逻辑删除标志
    @TableLogic
    private Integer deleted;
}
