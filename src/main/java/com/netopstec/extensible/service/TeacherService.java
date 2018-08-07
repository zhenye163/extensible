package com.netopstec.extensible.service;

import com.netopstec.extensible.entity.Teacher;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
public interface TeacherService {
    /**
     * 遍历所有的教师
     * @return 教师列表
     */
    List<Teacher> findAll();
}
