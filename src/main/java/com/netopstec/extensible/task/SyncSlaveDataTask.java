package com.netopstec.extensible.task;

import com.netopstec.extensible.common.DataSourceContextHolder;
import com.netopstec.extensible.common.DataSourceTypeEnum;
import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.mapper.StudentMapper;
import com.netopstec.extensible.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 由于将数据进行了分库（master数据库：支持增、删、改操作；slave数据库：支持查操作）
 * 为了保证数据的最终一致性，需要制定一个定时任务：
 * 每过10分钟，要将master数据库最近半个小时更新过的数据，同步更新到slave数据库中
 * @author zhenye 2018/9/21
 */
@Component
@Slf4j
public class SyncSlaveDataTask implements QuartzScheduleTask{

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public void exec() {
        log.info("即将依据master数据库信息，来同步slave数据库中的信息");
        // 同步student相关的信息
        syncStudentInfo();
        // 同步teacher相关的信息
        syncTeacherInfo();
        // 同步classroom相关的信息
        syncClassroomInfo();
        log.info("成功同步master数据库与slave数据库的信息");
    }

    /**
     * 同步slave数据库中的student相关的信息
     */
    private void syncStudentInfo() {
        List<Student> studentList = studentMapper.findOperatedInPastHalfHourInMaster();
        List<Student> toInsertStudentList = new ArrayList<>();
        List<Student> toUpdateStudentList = new ArrayList<>();
        for (Student student : studentList){
            Integer isExist = studentMapper.isExistInSlave(student.getId());
            if (isExist == 1){
                toUpdateStudentList.add(student);
            } else {
                toInsertStudentList.add(student);
            }
        }
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            studentMapper.batchInsertInSlave(toInsertStudentList);
            studentMapper.batchUpdateInSlave(toUpdateStudentList);
            transactionManager.commit(status);
        } catch (Exception e){
            log.error("在同步slave数据库中的student表信息时出现异常，进行回滚。",e);
            transactionManager.rollback(status);
        }

    }

    /**
     * 同步slave数据库中的teacher相关的信息
     */
    private void syncTeacherInfo() {
        List<Teacher> teacherList = teacherMapper.findOperatedInPastHalfHourInMaster();
        List<Teacher> toInsertTeacherList = new ArrayList<>();
        List<Teacher> toUpdateTeacherList = new ArrayList<>();
        for (Teacher teacher : teacherList){
            Integer isExist = teacherMapper.isExistInSlave(teacher.getId());
            if (isExist == 1){
                toUpdateTeacherList.add(teacher);
            } else {
                toInsertTeacherList.add(teacher);
            }
        }
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            teacherMapper.batchInsertInSlave(toInsertTeacherList);
            teacherMapper.batchUpdateInSlave(toUpdateTeacherList);
            transactionManager.commit(status);
        } catch (Exception e){
            log.error("在同步slave数据库中的teacher表信息时出现异常，进行回滚。",e);
            transactionManager.rollback(status);
        }

    }

    /**
     * 同步slave数据库中的classroom相关的信息
     */
    private void syncClassroomInfo() {
        List<Classroom> classroomList = classroomMapper.findOperatedInPastHalfHourInMaster();
        List<Classroom> toInsertClassroomList = new ArrayList<>();
        List<Classroom> toUpdateClassroomList = new ArrayList<>();
        for (Classroom classroom : classroomList){
            Integer isExist = classroomMapper.isExistInSlave(classroom.getId());
            if (isExist == 1){
                toUpdateClassroomList.add(classroom);
            } else {
                toInsertClassroomList.add(classroom);
            }
        }
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.slave);
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            classroomMapper.batchInsertInSlave(toInsertClassroomList);
            classroomMapper.batchUpdateInSlave(toUpdateClassroomList);
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("在同步slave数据库中的classroom表信息时出现异常，进行回滚。",e);
            transactionManager.rollback(status);
        }
    }
}
