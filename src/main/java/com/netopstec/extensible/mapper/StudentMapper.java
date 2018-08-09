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
     * 新增一名学生
     * @param student  学生参数
     * @return 是否新增成功
     */
    Integer insertOne(Student student);
}
