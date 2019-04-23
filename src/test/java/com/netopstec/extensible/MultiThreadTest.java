package com.netopstec.extensible;

/**
 * @author zhenye 2019/3/18
 */
public class MultiThreadTest {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        ready = true;
        number = 42;
    }
}
