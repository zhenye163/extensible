package com.netopstec.extensible.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA,Secure Hash Algorithm 安全散列算法。
 * @author zhenye 2018/8/22
 */
public class SHAUtil {
    /*
        SHA与MD5均有MD4导出，因此强度和特性比较相似的，都是不可逆的算法。
        SHA与MD5的不同：
        1.对强行攻击的安全性，SHA-1摘要比MD5摘长，对强行攻击有更大的强度。
        2.对密码分析的安全性，由于MD5的设计，易受密码分析的攻击，SHA-1显得不易受这样的攻击。
        3.在相同的硬件上，SHA-1的运行速度比MD5慢。
     */

    /**
     * SHA加密，即安全散列算法加密
     * @param bytes 要加密的信息
     * @return 40位字符
     */
    public static String encrypt(byte[] bytes){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] resultBytes = messageDigest.digest(bytes);
            return Hex.encodeHexString(resultBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
