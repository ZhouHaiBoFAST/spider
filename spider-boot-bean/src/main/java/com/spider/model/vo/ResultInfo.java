package com.spider.model.vo;

/**
 *
 *
 * @author liuzhongkai
 */
public class ResultInfo {

    /**
     * 请求结果消息
     */
    private String msg;

    /**
     * 请求状态码
     */
    private String result;

    public ResultInfo(){

    }

    public ResultInfo(String msg, String result) {
        this.msg = msg;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public String getResult() {
        return result;
    }

}
