package com.paratera.sgri.utils;

import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;


/**
 * 校验请求类
 * 
 * @author guoxiaohu
 *
 */
public class CheckUtil {

    private static final char[] HEXDIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String TOKEN = "WeiXin";

    /**
     * 比较signature与加密后的值相等
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(str);
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s);
        }
        String tmp = encode(sb.toString());
        return signature.equals(tmp);
    }

    /**
     * sha1加密
     * 
     * @param str
     * @return
     */
    private static String encode(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            MessageDigest mess = MessageDigest.getInstance("SHA1");
            mess.update(str.getBytes("UTF-8"));
            String text = getFormatText(mess.digest());
            return text;
        } catch (Exception e) {
            return null;
        }

    }

    private static String getFormatText(byte[] digest) {
        int len = digest.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            byte by = digest[i];
            sb.append(HEXDIGITS[by >>> 4 & 0x0f]);
            sb.append(HEXDIGITS[by & 0x0f]);
        }
        return sb.toString();
    }

}
