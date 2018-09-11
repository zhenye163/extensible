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

    /**
     * 查一个班级的信息
     * @param classroomId 班级Id
     * @return 学生信息
     */
    Classroom findOne(Integer classroomId);

    /**
     * 新开一个班级
     * @param classroom  班级参数
     * @return 是否新增成功
     */
    String insertOne(Classroom classroom);

    /**
     * 更新某个班级的信息
     * @param classroom 班级参数
     * @return 是否更改成功
     */
    String updateOne(Classroom classroom);

    /**
     * 删除某个班级的信息（这里是逻辑删除，而不是物理删除）
     * @param classroomId 班级id
     * @return 是否删除成功
     */
    String deleteOne(Integer classroomId);
}
