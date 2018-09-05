package com.spider.model.enums;

import com.spider.model.vo.ResultInfo;

/**
 * 请求相应对象
 * <p>
 *
 * @author liuzhongkai
 */
public enum ResultEnum {
    /**
     * 标注请求成功
     */
    SUCCESS("success", "0"),
    /**
     * 标注请求失败 系统异常
     */
    UNKNOWN_ERROR("error", "1");
    private ResultInfo resultInfo;

    ResultEnum(String msg, String result) {
        resultInfo = new ResultInfo(msg, result);
    }

    public ResultInfo getInfo() {
        return resultInfo;
    }
}
