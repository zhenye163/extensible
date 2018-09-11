package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.constant.BaseConstant;
import com.netopstec.extensible.constant.ErrorCodeEnum;
import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.exception.BaseException;
import com.netopstec.extensible.mapper.TeacherMapper;
import com.netopstec.extensible.service.TeacherService;
import com.netopstec.extensible.util.JsonUtil;
import com.netopstec.extensible.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zhenye 2018/8/7
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Teacher> findAll() {
        log.info("获取所有的教师信息");
        Map<Object,Object> map = redisUtil.hmget("teacher");
        String teacherJsonStr = map.values().toString();
        return JsonUtil.string2List(teacherJsonStr,Teacher.class);
    }

    @Override
    public Teacher findOne(Integer teacherId) {
        log.info("获取一个教师的信息");
        if (teacherId == null){
            return null;
        }
        String teacherStr = (String) redisUtil.hget("teacher", String.valueOf(teacherId));
        if (teacherStr == null || "".equals(teacherStr.trim())){
            Teacher teacher = teacherMapper.findById(teacherId);
            if (teacher == null){
                throw new BaseException(ErrorCodeEnum.MISS_INFO_ERROR);
            }
            redisUtil.hset("teacher",String.valueOf(teacherId),JsonUtil.obj2String(teacher));
            return teacher;
        }
        return null;
    }

    @Override
    public String insertOne(Teacher teacher) {
        log.info("插入一条新的教师数据");
        Integer teacherId = teacherMapper.insertOne(teacher);
        teacher.setId(teacherId);
        redisUtil.hset("teacher",String.valueOf(teacherId),JsonUtil.obj2String(teacher));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String updateOne(Teacher teacher) {
        log.info("更新一个老师的信息");
        Integer teacherId = teacher.getId();
        Teacher originTeacherInfo = teacherMapper.findById(teacherId);
        if (originTeacherInfo == null){
            teacherMapper.insertOne(teacher);
        } else {
            teacherMapper.updateOne(teacher);
        }
        redisUtil.hset("student",String.valueOf(teacherId),JsonUtil.obj2String(teacher));
        return BaseConstant.SUCCESS;
    }

    @Override
    public String deleteOne(Integer teacherId) {
        log.info("删除一个老师的信息");
        if (teacherId == null){
            return BaseConstant.FAIL;
        }
        redisUtil.hdel("teacher",String.valueOf(teacherId));
        teacherMapper.deleteById(teacherId);
        return BaseConstant.SUCCESS;
    }
}
