package com.netopstec.extensible.mapper;

import com.netopstec.extensible.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhenye 2018/8/8
 */
@Mapper
public interface StudentMapper {
    /**
     * 遍历学生表
     * @return 学生列表结果集
     */
    List<Student> findAll();

    /**
     * 按主键id查询学生信息
     * @param studentId 学生主键id
     * @return 学生信息
     */
    Student findById(Integer studentId);

    /**
     * 新增一名学生
     * @param student  学生参数
     * @return 是否新增成功
     */
    Integer insertOne(Student student);

    /**
     * 更新学生信息
     * @param student 学生对象
     */
    void updateOne(Student student);

    /**
     * 删除一个学生的记录
     * @param studentId 学生主键id
     */
    void deleteById(Integer studentId);

    /**
     * 查找某个班级的所有学生
     * @param classroomId 班级Id
     * @return 学生列表
     */
    List<Student> findByClassroomId(Integer classroomId);
}
