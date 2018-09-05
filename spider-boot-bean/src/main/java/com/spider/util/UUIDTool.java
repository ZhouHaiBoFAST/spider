package com.spider.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author liuzhongkai
 */
public class UUIDTool {

    public UUIDTool() {}

    /**
     * 获取无横杠uuid字符串
     */
    public static String getNoHorizontalUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
