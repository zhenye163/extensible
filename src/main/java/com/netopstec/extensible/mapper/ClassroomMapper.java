package com.netopstec.extensible.mapper;

import com.netopstec.extensible.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhenye 2018/8/8
 */
@Mapper
public interface ClassroomMapper {
    /**
     * 遍历班级表
     * @return 班级列表
     */
    List<Classroom> findAll();

    /**
     * 按id查询该班级的详情
     * @param classroomId 班级id
     * @return 班级详情
     */
    Classroom findById(Integer classroomId);
}
