package com.spider.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * 对于jsonWebToken 的创建和校验
 *
 * @author liuzhongkai
 */
public class JwtTool {

    /**
     * 校验jsonWebToken是否匹配私钥
     *
     * @param jsonWebToken 需要的校验jsonWebToken
     * @param secretKey    密钥
     * @return payload
     * @throws Exception 校验失败
     */
    public static Claims verifyJWT(String jsonWebToken,  String secretKey) throws Exception {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(jsonWebToken).getBody();
    }

    /**
     * 生成jsonWebToken
     *
     * @param claims        payload
     * @param secretKey     私钥
     * @param encryptionWay 算法
     * @return jsonWebToken
     */
    public static String generateJWT( Claims claims,  String secretKey, String encryptionWay) {
        SignatureAlgorithm signatureAlgorithm;
        switch (encryptionWay) {
            case "NONE":
                signatureAlgorithm = SignatureAlgorithm.NONE;
                break;
            case "HS256":
                signatureAlgorithm = SignatureAlgorithm.HS256;
                break;
            case "HS384":
                signatureAlgorithm = SignatureAlgorithm.HS384;
                break;
            case "HS512":
                signatureAlgorithm = SignatureAlgorithm.HS512;
                break;
            case "RS256":
                signatureAlgorithm = SignatureAlgorithm.RS256;
                break;
            case "RS384":
                signatureAlgorithm = SignatureAlgorithm.RS384;
                break;
            case "RS512":
                signatureAlgorithm = SignatureAlgorithm.RS512;
                break;
            case "ES256":
                signatureAlgorithm = SignatureAlgorithm.ES256;
                break;
            case "ES384":
                signatureAlgorithm = SignatureAlgorithm.ES384;
                break;
            case "ES512":
                signatureAlgorithm = SignatureAlgorithm.ES512;
                break;
            case "PS256":
                signatureAlgorithm = SignatureAlgorithm.PS256;
                break;
            case "PS384":
                signatureAlgorithm = SignatureAlgorithm.PS384;
                break;
            case "PS512":
                signatureAlgorithm = SignatureAlgorithm.PS512;
                break;
            default:
                throw new RuntimeException("unsupported signature algorithm is not found");
        }

        return generateJWT(claims, secretKey, signatureAlgorithm);
    }

    /**
     * 生成jsonWebToken,密钥格式为HS256
     *
     * @param claims    payload
     * @param secretKey 私钥
     * @return jsonWebToken
     */
    public static String generateJWT( Claims claims,  String secretKey) {
        return generateJWT(claims, secretKey, SignatureAlgorithm.HS256);
    }

    /**
     * 生成jsonWebToken
     *
     * @param claims             payload
     * @param secretKey          私钥
     * @param signatureAlgorithm 加密方式
     * @return jsonWebToken
     */
    public static String generateJWT( Claims claims,  String secretKey, SignatureAlgorithm signatureAlgorithm) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).signWith(signatureAlgorithm, signingKey).compact();
    }
}
