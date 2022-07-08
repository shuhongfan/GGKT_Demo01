package com.shf.ggkt.exception;

import com.shf.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice  // aop 面向切面编程，不改变源代码情况下，增强功能
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     * 异常处理类编写针对异常处理的方法，方法上面添加注解 @ExceptionHandler(
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    /**
     * 添加异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result error(GgktException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }
}