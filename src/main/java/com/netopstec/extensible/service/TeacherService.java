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

    /**
     * 查一个老师的信息
     * @param teacherId 老师Id
     * @return 老师信息
     */
    Teacher findOne(Integer teacherId);

    /**
     * 新增一名老师
     * @param teacher  老师参数
     * @return 是否新增成功
     */
    String insertOne(Teacher teacher);

    /**
     * 更新某个老师的信息
     * @param teacher 老师参数
     * @return 是否更改成功
     */
    String updateOne(Teacher teacher);

    /**
     * 删除某个老师的信息（这里是逻辑删除，而不是物理删除）
     * @param teacherId 老师id
     * @return 是否删除成功
     */
    String deleteOne(Integer teacherId);
}
