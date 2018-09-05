package com.spider.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作字符串的工具类
 *
 * @author liuzhongkai
 * @version 1.0
 */
public class Strings extends StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Strings.class);

    private static final Object MD5_LOCK = new Object();

    private static final Object SHA_LOCK = new Object();

    public final static String __ISO_8859_1;


    static {
        String iso = System.getProperty("ISO_8859_1");
        if (iso == null) {
            try {
                new String(new byte[]{20}, "ISO-8859-1");
                iso = "ISO-8859-1";
            } catch (UnsupportedEncodingException e) {
                iso = "ISO8859_1";
            }
        }
        __ISO_8859_1 = iso;
    }

    private static MessageDigest MD5;

    private static MessageDigest SHA;

    /**
     * 将字符串加密成md5格式
     *
     * @param password 加密字符串
     * @param bit      是否为32位
     * @return
     * @author liuzhongkai
     */
    public static String getMd5(String password, boolean bit) {
        try {
            final byte[] digest;
            synchronized (MD5_LOCK) {
                if (MD5 == null) {
                    MD5 = MessageDigest.getInstance("MD5");
                }
                MD5.reset();
                MD5.update(password.getBytes(__ISO_8859_1));
                digest = MD5.digest();

            }
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (bit)
                return buf.toString();
            else
                return buf.toString().substring(8, 24);

        } catch (Exception e2) {
            LOGGER.error(e2.getMessage());
            return null;
        }
    }

    /**
     * 将字符串加密成sha-1格式
     *
     * @param password 加密字符串
     * @return
     * @author liuzhongkai
     * @version 1.0
     */
    public static String getSha(String password) {
        try {
            final byte[] digest;
            synchronized (SHA_LOCK) {
                if (SHA == null) {
                    SHA = MessageDigest.getInstance("SHA-1");
                }
                SHA.reset();
                SHA.update(password.getBytes(__ISO_8859_1));
                digest = SHA.digest();

            }
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(8, 24);

        } catch (Exception e2) {
            LOGGER.error(e2.getMessage());
        }
        return null;
    }

    /**
     * 将字符串翻转位移
     *
     * @param from  所需处理的字符串
     * @param index 位移位数
     * @return
     * @author liuzhongkai
     * @version 1.0
     */
    public static String displacement(String from, int index) {
        from = reChange(from);
        StringBuilder sb = new StringBuilder(from.length());
        sb.append(reChange(from.substring(0, from.length() - index)));
        sb.append(reChange(from.substring(from.length() - index)));
        return sb.toString();
    }

    /**
     * 反转字符串,把from字符串反转过来 字符串求长度用length(),数组求长度用length
     *
     * @param from 字符串
     * @return
     * @author liuzhongkai
     * @version 1.0
     */
    public static String reChange(String from) {
        char[] froms = from.toCharArray();
        for (int i = 0; i < from.length() / 2; i++) {
            char temp = froms[i];
            froms[i] = froms[from.length() - 1 - i];
            froms[froms.length - 1 - i] = temp;
        }
        return String.valueOf(froms);
    }

    /**
     * 核对value是否为null，null字符串，空字符串
     *
     * @param value
     * @return
     * @author liuzhongkai
     * @version 1.0
     */
    public static boolean isBlankAndNullString(String value) {
        return Strings.isBlank(value) || "null".equalsIgnoreCase(value);
    }

    /**
     * 字符串是否为ok(忽略大小写比较)
     *
     * @param value 比较字符串
     * @return 是否为ok
     */
    public static boolean isOK(String value) {
        return "OK".equalsIgnoreCase(value);
    }

    private final static Pattern IS_NUMBER = Pattern.compile("-?[0-9]+.?[0-9]+");

    /**
     * 判断是否为数字
     *
     * @param value 字符串
     * @return 是否是数字
     */
    public static boolean isNumeric(String value) {
        Matcher isNum = IS_NUMBER.matcher(value);
        return isNum.matches();
    }

    /**
     * 字符串比较
     *
     * @param ruleValue 源数据
     * @param value     比较数据
     * @return 是否相同
     */
    public static boolean eqValue(String ruleValue, String value) {
        if (!Strings.isNumeric(value) && !Strings.isNumeric(ruleValue)) {
            return value.equalsIgnoreCase(ruleValue);
        } else {
            int intValue = (int) Float.parseFloat(value);
            float floatValue = Float.parseFloat(value);
            int intRuleValue = (int) Float.parseFloat(ruleValue);
            float floatRuleValue = Float.parseFloat(ruleValue);
            if (intValue == floatValue && intRuleValue == floatRuleValue)
                return intValue == intRuleValue;
            else
                return floatValue == floatRuleValue;
        }
    }

    /**
     * 随机获取一个简体中文
     */
    public static String generateChinese() {
        int hightPos, lowPos;
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        hightPos = (176 + Math.abs(localRandom.nextInt(39)));
        lowPos = (161 + Math.abs(localRandom.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (byte) hightPos;
        b[1] = (byte) lowPos;
        try {
            return new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            ;
        }
        return null;
    }

    public Strings() {
    }

}
