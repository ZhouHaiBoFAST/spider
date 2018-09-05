package com.spider.jwt.web.filter;

import com.spider.jwt.JwtProperties;
import com.spider.jwt.util.JwtTool;
import io.jsonwebtoken.Claims;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * jwt 数据对象()
 *
 * @author liuzhongkai
 */
public class JwtModel {

    static final ThreadLocal<Claims> CLAIMS_CONTAINER = new ThreadLocal<>();

    private JwtProperties properties;

    public JwtModel(JwtProperties properties) {
        this.properties = properties;
    }

    /**
     * 向Response 相应中写入jwt标准中的playload
     *
     * @param claims playload
     */
    public void sendClaims(Claims claims) {
        if (claims == null)
            throw new NullPointerException();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletResponse response = servletRequestAttributes.getResponse();
        long nowTime = System.currentTimeMillis();
        claims.setNotBefore(new Date(nowTime));
        claims.setExpiration(new Date(nowTime + properties.getKeepTime()));
        String jsonWebToken = JwtTool.generateJWT(claims, properties.getSecretKey(), properties.getSignatureAlgorithm());
        response.setHeader(properties.getHeaderName(), jsonWebToken);
    }

    /**
     * 获取当前request 请求中的playload
     *
     * @return playload
     */
    public Claims getClaims() {
        return CLAIMS_CONTAINER.get();
    }
}
