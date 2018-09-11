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

    /**
     * 查一个学生的信息
     * @param studentId 学生Id
     * @return 学生信息
     */
    Student findOne(Integer studentId);

    /**
     * 新增一名学生
     * @param student  学生参数
     * @return 是否新增成功
     */
    String insertOne(Student student);

    /**
     * 更新某个学生的信息
     * @param student 学生参数
     * @return 是否更改成功
     */
    String updateOne(Student student);

    /**
     * 删除某个学生的信息（这里是逻辑删除，而不是物理删除）
     * @param studentId 学生id
     * @return 是否删除成功
     */
    String deleteOne(Integer studentId);
}
