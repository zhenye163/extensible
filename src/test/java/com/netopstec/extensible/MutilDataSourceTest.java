package com.netopstec.extensible;

import com.netopstec.extensible.common.DataSourceContextHolder;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author zhenye 2018/9/20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MutilDataSourceTest {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Test
    public void transactionTest(){
        mutilDataSourceWithNoTransaction();
        log.info("--------------------------------------------");
        mutilDataSourceWithTransaction();
    }

    private void mutilDataSourceWithNoTransaction(){
        log.info("不添加事务控制，在一个service中使用多数据源");
        Student student1 = studentMapper.findOneInMaster(1);
        log.info("此时使用的数据源是：" + DataSourceContextHolder.getDataSourceType());
        log.info("此时该学生的信息为：" + student1);
        Student student2 = studentMapper.findOneInSlave(1);
        log.info("此时使用的数据源是：" + DataSourceContextHolder.getDataSourceType());
        log.info("此时该学生的信息为：" + student2);
    }

    private void mutilDataSourceWithTransaction(){
        log.info("添加事务控制，在一个service中使用多数据源");
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            Student student1 = studentMapper.findOneInMaster(1);
            log.info("此时使用的数据源是：" + DataSourceContextHolder.getDataSourceType());
            log.info("此时该学生的信息为：" + student1);
            Student student2 = studentMapper.findOneInSlave(1);
            log.info("此时使用的数据源是：" + DataSourceContextHolder.getDataSourceType());
            log.info("此时该学生的信息为：" + student2);
            transactionManager.commit(status);
        }catch(Exception e){
            transactionManager.rollback(status);
        }
    }
}
