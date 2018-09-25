package com.netopstec.extensible.runner;

import com.netopstec.extensible.common.DataSourceContextHolder;
import com.netopstec.extensible.common.DataSourceTypeEnum;
import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.mapper.StudentMapper;
import com.netopstec.extensible.mapper.TeacherMapper;
import com.netopstec.extensible.util.JsonUtil;
import com.netopstec.extensible.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @author zhenye 2018/9/25
 */
@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 项目启动后，需要进行数据的初始化
     * @param args
     */
    @Override
    public void run(String... args){
        log.info("项目正常启动，开始初始化数据---依据master数据库的信息，同步Slave数据库以及Redis中的信息");
        List<Student> studentList = studentMapper.findAllInMaster();
        List<Teacher> teacherList = teacherMapper.findAllInMaster();
        List<Classroom> classroomList = classroomMapper.findAllInMaster();
        initStudentInfo(studentList);
        initTeacherInfo(teacherList);
        initClassroomInfo(classroomList);
        log.info("成功初始化，同步Slave数据库以及Redis中的信息");
    }

    /**
     * 同步学生相关的信息
     * @param studentList 学生列表信息
     */
    private void initStudentInfo(List<Student> studentList) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            studentMapper.truncateInSlave();
            studentMapper.batchInsertInSlave(studentList);
            transactionManager.commit(status);
            redisUtil.del("student");
            for (Student student : studentList){
                if (student.getFlag() == 0){
                    redisUtil.hset("student",String.valueOf(student.getId()), JsonUtil.obj2String(student));
                }
            }
        }catch (Exception e){
            log.error("同步slave数据库的student表信息时出错，进行事务回滚。",e);
            transactionManager.rollback(status);
        }
    }

    /**
     * 同步教师相关的信息
     * @param teacherList 教师信息列表
     */
    private void initTeacherInfo(List<Teacher> teacherList) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            teacherMapper.truncateInSlave();
            teacherMapper.batchInsertInSlave(teacherList);
            transactionManager.commit(status);
            redisUtil.del("teacher");
            for (Teacher teacher : teacherList){
                if (teacher.getFlag() == 0){
                    redisUtil.hset("teacher",String.valueOf(teacher.getId()), JsonUtil.obj2String(teacher));
                }
            }
        }catch (Exception e){
            log.error("同步slave数据库的teacher表信息时出错，进行事务回滚。",e);
            transactionManager.rollback(status);
        }
    }

    /**
     * 同步班级相关的信息
     * @param classroomList 班级信息列表
     */
    private void initClassroomInfo(List<Classroom> classroomList) {
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            classroomMapper.truncateInSlave();
            classroomMapper.batchInsertInSlave(classroomList);
            transactionManager.commit(status);
            redisUtil.del("classroom");
            for (Classroom classroom : classroomList){
                if (classroom.getFlag() == 0){
                    redisUtil.hset("classroom",String.valueOf(classroom.getId()), JsonUtil.obj2String(classroom));
                }
            }
        }catch (Exception e){
            log.error("同步slave数据库的classroom表信息时出错，进行事务回滚。",e);
            transactionManager.rollback(status);
        }

    }
}
