package com.netopstec.extensible.service;

import com.netopstec.extensible.entity.Classroom;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
public interface ClassroomService {
    /**
     * 遍历所有的班级
     * @return 班级列表
     */
    List<Classroom> findAll();
}
