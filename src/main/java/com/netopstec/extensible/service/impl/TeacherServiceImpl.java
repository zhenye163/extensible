package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.mapper.TeacherMapper;
import com.netopstec.extensible.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findAll() {
        log.info("获取所有的教师信息");
        return teacherMapper.findAll();
    }
}
