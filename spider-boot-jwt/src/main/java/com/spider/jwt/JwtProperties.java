package com.spider.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * jwt拦截配置
 *
 * @author liuzhongkai
 */
@ConfigurationProperties(prefix = "spider.jwt")
public class JwtProperties {

    /**
     * 需要拦截的uri
     */
    private Set<String> interceptUris;

    /**
     * 合格不需要拦截的uri
     */
    private Set<String> qualifiedUris;

    /**
     * jsonWebToken 请求头配置名称
     */
    private String headerName = "Authorization";

    /**
     * 密钥
     */
    private String secretKey = "pua4tHj^N18qojvRu00HRIkMtR#yCyfv";

    /**
     * 签名算法
     */
    private String signatureAlgorithm = "HS256";

    /**
     * Filter拦截地址
     */
    private String urlPatterns = "/*";

    /**
     * token保持时间
     */
    private long keepTime = 1000 * 60 * 30;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Set<String> getInterceptUris() {
        return interceptUris;
    }

    public void setInterceptUris(Set<String> interceptUris) {
        this.interceptUris = interceptUris;
    }

    public Set<String> getQualifiedUris() {
        return qualifiedUris;
    }

    public void setQualifiedUris(Set<String> qualifiedUris) {
        this.qualifiedUris = qualifiedUris;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public long getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(long keepTime) {
        this.keepTime = keepTime;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
