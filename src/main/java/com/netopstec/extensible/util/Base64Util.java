package com.netopstec.extensible.util;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 *  Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一。
 *
 * @author zhenye 2018/8/22
 */
public class Base64Util {

    /*
        我们知道Java中是用"8个二进制数字"表示一个实际的字节。
        比如：我要用Base64编码一个字符串“abc”，实际算法如下：
        'a','b','c'的ASCII标准编码分别为（十进制）97,98,99,因此用二进制表示“abc”字符串就是：
        01100001，01100010，01100011           ---3组，每组8字节
        Base64的原理：将这三组8字节，分成4组6字节
        011000，010110， 001001，100011         ---4组，每组6字节
        高位补0
        00011000，00010110， 00001001，00100011
        这四个二进制数组对应十进制的数值分别是：24，22，9，35，RFC2045（Base64解码表）分别为：Y,W,J,j
        即："abc"经过Base64编码后，为"YWJj"。这个过程是可逆的。
        ---每次Base64编码都会扩容33%
     */

    /**
     * Base64编码
     * @param bytes 待Base64编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] encode(byte[] bytes){
        return Base64.encodeBase64(bytes);
    }

    /**
     * Base64编码
     * @param message 待Base64编码的字符串
     * @return 编码后的字符串
     */
    public static String encode(String message){
        if (message == null){
            return null;
        }
        byte[] bytes = message.getBytes();
        byte[] result = Base64.encodeBase64(bytes);
        return new String(result);
    }

    /**
     * Base64解码
     * @param bytes 待Base64解码的字节数组
     * @return 解码后的字节数组
     */
    public static byte[] decode(byte[] bytes){
        return Base64.decodeBase64(bytes);
    }

    /**
     * Base64解码
     * @param message 待Base64解码的字符串
     * @return 解码后的字符串
     */
    public static String decode(String message){
        if (message == null){
            return null;
        }
        byte[] bytes = message.getBytes();
        byte[] result = Base64.decodeBase64(bytes);
        return new String(result);
    }

}
