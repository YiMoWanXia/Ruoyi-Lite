package com.ruoyi.lite.core.base.framework.web.domain;

import cn.hutool.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果
 *
 * @param <T> 数据类型
 * @author Fooyao
 */
@Setter
@Getter
@Schema(description = "统一响应结果")
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 警告状态码
     */
    public static final int HTTP_WARN_STATUS = 601;

    /**
     * 状态码
     */
    @Schema(description = "状态码，200成功 500失败 601警告")
    private int code;

    /**
     * 返回内容
     */
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 数据对象
     */
    @Schema(description = "业务数据")
    private T data;

    public R() {
    }

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     */
    public static <T> R<T> success() {
        return R.success("操作成功");
    }

    /**
     * 返回成功数据
     */
    public static <T> R<T> success(T data) {
        return R.success("操作成功", data);
    }

    /**
     * 返回成功消息
     */
    public static <T> R<T> success(String msg) {
        return R.success(msg, null);
    }

    /**
     * 返回成功消息
     */
    public static <T> R<T> success(String msg, T data) {
        return new R<>(HttpStatus.HTTP_OK, msg, data);
    }

    /**
     * 返回警告消息
     */
    public static <T> R<T> warn(String msg) {
        return R.warn(msg, null);
    }

    /**
     * 返回警告消息
     */
    public static <T> R<T> warn(String msg, T data) {
        return new R<>(HTTP_WARN_STATUS, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static <T> R<T> error() {
        return R.error("操作失败");
    }

    /**
     * 返回错误消息
     */
    public static <T> R<T> error(String msg) {
        return R.error(msg, null);
    }

    /**
     * 返回错误消息
     */
    public static <T> R<T> error(String msg, T data) {
        return new R<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static <T> R<T> error(int code, String msg) {
        return new R<>(code, msg, null);
    }

}
