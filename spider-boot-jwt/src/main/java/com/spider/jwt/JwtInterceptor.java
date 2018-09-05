package com.spider.jwt;

import io.jsonwebtoken.Claims;

/**
 * jwt标准校验的之后的具体扩展校验 具体校验是无序的
 *
 * @author liuzhongkai
 */
public interface JwtInterceptor {

    /**
     * 对jwt中的playload进行扩展校验
     *
     * @param claims playload
     * @return 是否校验通过 true 通过,false 不通过
     */
    public boolean verify(Claims claims);

}
