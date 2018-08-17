package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.mapper.StudentMapper;
import com.netopstec.extensible.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService{

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ClassroomMapper classroomMapper;

    @Override
    public List<Student> findAll() {
        log.info("获取所有的学生信息");
        return studentMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertOne(Student student) {
        log.info("插入一条新的学生数据");
        Integer classroomId = student.getClassroomId();
        Classroom classroom = classroomMapper.findById(classroomId);
        if (classroom == null){
            log.error("找不到这个班级");
            throw new RuntimeException("学生信息不全，无法入库");
        }
        Integer ID = studentMapper.insertOne(student);

        return null;
    }
}
