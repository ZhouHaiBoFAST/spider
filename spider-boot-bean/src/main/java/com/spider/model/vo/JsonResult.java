/*
 * @JsonResult.java      1.0 2016.6/9/22
 *
 *  Copyright 2005-2016 © 蜘蛛网
 *  上海市长宁区协和路787号B楼北三层
 *  All rights reserved.
 *
 */
package com.spider.model.vo;


import com.alibaba.fastjson.JSON;
import com.spider.model.enums.ResultEnum;

import java.io.Serializable;

/**
 * 通用前端数据格式.
 *
 * @author liuzhongkai
 */
public class JsonResult<T> implements Serializable {

    /**
     * 数据
     */
    private T data;


    /**
     * 请求结果消息
     */
    private String msg;

    /**
     * 请求状态码
     */
    private String result;

    /**
     * 设置一个空的前端相应对象
     */
    public JsonResult() {
    }

    /**
     * 设置请求相应对象
     *
     * @param result 请求标注
     * @author liuzhongkai
     */
    public JsonResult(ResultInfo result) {
        this.msg = result.getMsg();
        this.result = result.getResult();

    }

    /**
     * 设置请求相应和请求相应数据
     *
     * @param data   相应数据
     * @param result 请求标注
     */
    public JsonResult(T data, ResultInfo result) {
        this.data = data;
        this.msg = result.getMsg();
        this.result = result.getResult();
    }

    /**
     * 获取 数据
     *
     * @return 数据
     */
    public T getData() {
        return this.data;
    }


    /**
     * 设置 数据
     *
     * @param data 数据
     * @return 实例本身
     */
    public JsonResult<T> setData(final T data) {
        this.data = data;
        return this;
    }


    /**
     * 获取 请求结果消息
     *
     * @return 请求结果消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 获取 请求状态码
     *
     * @return 请求状态码
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置 请求相应
     *
     * @param result 相应
     * @return 实例本身
     */
    public JsonResult<T> setBody(ResultInfo result) {
        this.msg = result.getMsg();
        this.result = result.getResult();
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static <T> JsonResult<T> success(T value) {
        return body(value, ResultEnum.SUCCESS.getInfo());
    }

    public static <T> JsonResult<T> error(T value) {
        return body(value, ResultEnum.UNKNOWN_ERROR.getInfo());
    }

    public static <T> JsonResult<T> body(T value, ResultInfo info) {
        return new JsonResult<>(value, info);
    }
}
