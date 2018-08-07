package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.domain.ClassroomRepository;
import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }
}
