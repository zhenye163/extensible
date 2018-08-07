package com.netopstec.extensible.service;

import com.netopstec.extensible.entity.Student;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
public interface StudentService {
    /**
     * 遍历所有的学生
     * @return 学生信息列表
     */
    List<Student> findAll();
}
