package com.shf.ggkt.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 *
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;

    /**
     * 返回状态信息（成功 、 失败）
     */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = new Result<T>();
        if (body != null) {
            result.setData(body);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 操作成功，没有data数据
     * @return
     * @param <T>
     */
    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功，有data数据
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        return build(data,20000,"成功");
    }

    /**
     * 操作失败，没有data数据
     * @return
     * @param <T>
     */
    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败，有data数据
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        return build(data, 201,"失败");
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}