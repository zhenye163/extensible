package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.domain.TeacherRepository;
import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
