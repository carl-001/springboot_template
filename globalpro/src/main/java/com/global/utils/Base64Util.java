package com.global.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Author yanghanjin
 * @Description:
 * @Date 2019/4/12
 */
public class Base64Util {

    /**
     * 采用BASE64算法对字符串进行加密
     * @param base 原字符串
     * @return 加密后的字符串
     */
    public static final String encode(String base){
        return Base64Util.encode(base.getBytes());
    }

    /**
     * 采用BASE64算法对字节数组进行加密
     * @param baseBuff 原字节数组
     * @return 加密后的字符串
     */
    public static final String encode(byte[] baseBuff){
        return new BASE64Encoder().encode(baseBuff);
    }

    /**
     * 字符串解密，采用BASE64的算法
     * @param encoder 需要解密的字符串
     * @return 解密后的字符串
     */
    public static final String decode(String encoder){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(encoder);
            return new String(buf,"utf-8");
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * BASE64解密 * @param key * @return * @throws Exception
     */
    public static String decryptBASE64(String key) throws Exception {
        return new String((new BASE64Decoder()).decodeBuffer(key));
    }


    public static void main(String[] args) {
        String encode = encode("ZWI1ODFmOTktZGJlZi00NjVlLWIzYmQtNTY1YmRlZmI5ODRicGFzc0AxMjM=");
        String decode = decode("ZWI1ODFmOTktZGJlZi00NjVlLWIzYmQtNTY1YmRlZmI5ODRicGFzc0AxMjM=\n");
        System.out.println(encode);
        System.out.println(decode);
    }
}
