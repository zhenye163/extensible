package com.netopstec.extensible.service.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhenye 2018/10/16
 */
@Service
@Slf4j
public class AsyncService {

    @Async
    public void asyncMethod(){
        for (int i = 0;i < 5;i++){
            log.info("---{}---正在执行异步方法...",i);
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                log.error("阻塞导致方法执行失败");
            }
        }
    }
}
