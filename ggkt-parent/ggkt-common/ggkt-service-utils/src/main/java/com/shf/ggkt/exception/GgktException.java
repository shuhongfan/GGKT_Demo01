package com.shf.ggkt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建自定义异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgktException extends RuntimeException {
    private Integer code;
    private String msg;
}