package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.service.ClassroomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
@Slf4j
public class ClassroomServiceImpl implements ClassroomService {
    @Resource
    private ClassroomMapper classroomMapper;

    @Override
    public List<Classroom> findAll() {
        log.info("获取所有的班级信息");
        return classroomMapper.findAll();
    }
}
