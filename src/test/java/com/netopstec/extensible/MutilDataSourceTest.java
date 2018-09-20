package com.netopstec.extensible;

import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhenye 2018/9/20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MutilDataSourceTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void mutilDataSourceTest(){
        log.info("将要查的是extensible_master里面的信息：");
        Integer studentId = 1;
        Student student1 = studentMapper.findOneInMaster(studentId);
        log.info("extensible_master中id为{}的学生信息如下：{}",studentId,student1);
        Student student2 = studentMapper.findOneInSlave(studentId);
        log.info("extensible_slave中id为{}的学生信息如下：{}",studentId,student2);
    }
}
