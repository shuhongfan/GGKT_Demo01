package com.shf.my_ggkt_mp_demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * mybatis-plus主键生成策略
 *     AUTO(0),     自动增长，表主键字段设置自动增长，表主键字段类型int类型
 *     NONE(1),
 *     INPUT(2),        输入id值
 *     ASSIGN_ID(3),        mp默认策略，生成19位数字值
 *     ASSIGN_UUID(4),     生成数字和字母值
 */
@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer deleted;
}
