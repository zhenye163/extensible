package com.netopstec.extensible.service.impl;

import com.netopstec.extensible.domain.StudentRepository;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
