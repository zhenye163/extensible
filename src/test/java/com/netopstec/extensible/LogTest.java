package com.netopstec.extensible;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;


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
}
