package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.service.ClassroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Resource
    private ClassroomMapper classroomMapper;

    @Override
    public List<Classroom> findAll() {
        return classroomMapper.findAll();
    }
}
