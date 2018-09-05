package com.spider.jwt.util;

import com.spider.util.Strings;

import java.util.Set;

/**
 * jwt 过滤器辅助类
 *
 * @author liuzhongkai
 */
public class JwtFilterTool {


    /**
     * 判断该set集合中的字符串包含指定的字符串
     *
     * @param uriSet 需要判断的集合
     * @param uri    依据
     * @return true 匹配, false 不匹配
     */
    public static boolean isSetItemContains(Set<String> uriSet, String uri) {
        for (String forUri : uriSet) {
            if (Strings.isNotEmpty(forUri)) {
                int index = uri.indexOf(forUri);
                if (index == 0 || index == 1)
                    return true;
            }
        }
        return false;
    }

}
