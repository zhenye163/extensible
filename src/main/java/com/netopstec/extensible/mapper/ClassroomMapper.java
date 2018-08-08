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
}
