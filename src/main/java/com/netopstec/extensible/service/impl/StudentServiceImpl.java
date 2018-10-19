package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.constant.BaseConstant;
import com.netopstec.extensible.constant.ErrorCodeEnum;
import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.exception.BaseException;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.mapper.StudentMapper;
import com.netopstec.extensible.service.StudentService;
import com.netopstec.extensible.service.async.AsyncService;
import com.netopstec.extensible.util.JsonUtil;
import com.netopstec.extensible.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncService asyncService;

    @Override
    public List<Student> findAll() {
        log.info("获取所有的学生信息");
        Map<Object,Object> map = redisUtil.hmget("student");
        String studentJsonStr = map.values().toString();
        return JsonUtil.string2List(studentJsonStr,Student.class);
    }

    @Override
    public Student findOne(Integer studentId) {
        log.info("获取一个学生的信息");
        if (studentId == null){
            return null;
        }
        String studentStr = (String) redisUtil.hget("student", String.valueOf(studentId));
        if (studentStr == null || "".equals(studentStr.trim())){
            Student student = studentMapper.findById(studentId);
            if (student == null){
                throw new BaseException(ErrorCodeEnum.MISS_INFO_ERROR);
            }
            redisUtil.hset("student",String.valueOf(studentId),JsonUtil.obj2String(student));
            return student;
        }
        return JsonUtil.string2Obj(studentStr,Student.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertOne(Student student) {
        log.info("插入一条新的学生数据");
        Integer classroomId = student.getClassroomId();
        Classroom classroom = classroomMapper.findById(classroomId);
        if (classroom == null){
            throw new BaseException(ErrorCodeEnum.MISS_INFO_ERROR);
        }
        Integer studentId = studentMapper.insertOne(student);
        student.setId(studentId);
        redisUtil.hset("student",String.valueOf(studentId),JsonUtil.obj2String(student));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String updateOne(Student student) {
        log.info("更新一个学生的信息");
        Integer studentId = student.getId();
        Student originStudentInfo = studentMapper.findById(studentId);
        if (originStudentInfo == null){
            studentMapper.insertOne(student);
        } else {
            studentMapper.updateOne(student);
        }
        redisUtil.hset("student",String.valueOf(studentId),JsonUtil.obj2String(student));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String deleteOne(Integer studentId) {
        log.info("删除一个学生的信息");
        if (studentId == null){
            return BaseConstant.FAIL;
        }
        redisUtil.hdel("student",String.valueOf(studentId));
        studentMapper.deleteById(studentId);
        return BaseConstant.SUCCESS;
    }

    /**
     * 在同一个类中用@Async注解的异步方法会失效！！！
     */
    @Override
    public String testAsyncMethod() {
        log.info("开始执行自己的方法");
        for (int i = 0;i < 3;i++){
            asyncService.asyncMethod();
        }
        log.info("结束执行自己的方法");
        return BaseConstant.SUCCESS;
    }


}
