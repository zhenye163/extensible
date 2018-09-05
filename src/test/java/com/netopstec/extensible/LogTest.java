package com.netopstec.extensible;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.netopstec.extensible.util.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


/**
 * @author zhenye 2018/8/20
 */
public class LogTest {

    @Test
    public void logTest(){
        Logger fooLogger = (Logger) LoggerFactory.getLogger("com.foo");
        fooLogger.setLevel(Level.INFO);
        fooLogger.debug("the level debug of foo is disable");
        fooLogger.info("the level info of foo is able and foo's level is {}", fooLogger.getLevel());
        fooLogger.warn("the level warn of foo is able");
        fooLogger.error("the level error of foo is able");
        System.out.println("-----------------------------------------------------------------");
        Logger barLogger = (Logger) LoggerFactory.getLogger("com.foo.bar");
        barLogger.debug("the level debug of bar is disable");
        barLogger.info("the level info of bar is able and bar's level is {}",barLogger.getLevel());
        barLogger.warn("the level warn of bar is able");
        barLogger.error("the level error of bar is able");
    }

    @Test
    public void Base64Test(){
        String message = "abc";
        System.out.println("待Base64算法加密的字符串为：" + message);
        String secretMessage = Base64Util.encode(message);
        System.out.println("原始信息<" + message + ">，Base64加密后，信息为：" + secretMessage);
        String originMessage = Base64Util.decode(secretMessage);
        System.out.println("加密信息<" + secretMessage + ">，Base64解密后，信息为：" + originMessage);
    }

    @Test
    public void MD5Test(){
        String message = "123456";
        System.out.println("待MD5算法加密的字符串为：" + message);
        String secretMessage = MD5Util.encrypt(message.getBytes());
        System.out.println("原始信息<" + message + ">，MD5加密后，信息为：" + secretMessage);
    }

    @Test
    public void SHATest(){
        String message = "123456";
        System.out.println("待SHA算法加密的字符串为：" + message);
        String secretMessage = SHAUtil.encrypt(message.getBytes());
        System.out.println("原始信息<" + message + ">，SHA加密后，信息为：" + secretMessage);
    }

    @Test
    public void AESTest(){
        String message = "1";
        String encryptMessage = AESUtil.encrypt(message);
        System.out.println("加密前：" + message + ",加密后：" + encryptMessage);
        String decryptMessage = AESUtil.decrypt(encryptMessage);
        System.out.println("解密前：" + encryptMessage + ",解密后：" + decryptMessage);
    }

    @Test
    public void RSATest(){
        Map<String,Object> keyPairs = RSAUtil.generateKey();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPairs.get("publicKey");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPairs.get("privateKey");
        String message = "123456";
        System.out.println("将要加密的信息：" + message
                + ",生成的公钥：" + Base64.encodeBase64String(rsaPublicKey.getEncoded())
                + ",生成的私钥：" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
        String encryptMessage = RSAUtil.encrypt(message,rsaPrivateKey);
        System.out.println("私钥加密后的信息：" + encryptMessage);
        String decryptMessage = RSAUtil.decrypt(encryptMessage,rsaPublicKey);
        System.out.println("公钥解密后的信息：" + decryptMessage);
    }
}
