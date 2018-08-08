package com.netopstec.extensible.mapper;

import com.netopstec.extensible.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhenye 2018/8/8
 */
@Mapper
public interface TeacherMapper {
    /**
     * 遍历教师表
     * @return 教师列表
     */
    List<Teacher> findAll();
}
