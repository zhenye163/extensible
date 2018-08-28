package com.netopstec.extensible.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5,Message-Digest Algorithm 5（信息摘要算法-5）
 * 能够将任意长度的字符，经过MD5加密后，生成32个字符。
 *
 * @author zhenye 2018/8/22
 */
public class MD5Util {

    /**
     * M5加密
     * @param bytes 要加密的信息
     * @return 32位字符
     */
    public static String encrypt(byte[] bytes){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] resultBytes = messageDigest.digest(bytes);
            return Hex.encodeHexString(resultBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Hex.encodeHexString(resultBytes);这句话的作用如下：
        public String encodeHexString(byte[] bytes){
            byte[] tempResult = messageDigest.digest(bytes);
            StringBuilder result = new StringBuilder();
            for (byte aTempResult : tempResult) {
                int val = ((int) aTempResult) & 0xff;
                if (val < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(val));
            }
            return result.toString();
        }

        由于计算机是用二进制（0/1）来存数值的。（存的是这个数值的补码）
        将一个较小类型的数值转为一个较大类型的数值（这里是byte > int）时，
        正数时，补码最左一位（符号位）是0，补码最左边会补相应个数的0。
        负数时，补码最左一位（符号位）是1，补码最左边会补相应个数的1。
        如：十进制的"-127",对应的二进制是：(原码)11111111---(补码)10000001，
        需要转成int类型时， jvm的处理是：
        补码：(11111111 11111111 11111111 10000001)---原码：(1000000 00000000 00000000 00000000 01111111)即-127
        但这里出现负数是没有意义的，与"0xff"进行位与运算，是保证出现的都是正数。
        (11111111 11111111 11111111 10000001) & (11111111) = (00000000 00000000 00000000 10000001)即127
    */
}
