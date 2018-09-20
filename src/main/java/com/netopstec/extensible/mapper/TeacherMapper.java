package com.netopstec.extensible.mapper;

import com.netopstec.extensible.annotation.DataSourceType;
import com.netopstec.extensible.common.DataSourceTypeEnum;
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
    @DataSourceType(DataSourceTypeEnum.slave)
    List<Teacher> findAll();

    /**
     * 按主键id查询教师信息
     * @param teacherId 教师主键id
     * @return 教师信息
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    Teacher findById(Integer teacherId);

    /**
     * 新增一名教师
     * @param teacher  教师参数
     * @return 是否新增成功
     */
    @DataSourceType(DataSourceTypeEnum.master)
    Integer insertOne(Teacher teacher);

    /**
     * 更新教师信息
     * @param teacher 教师对象
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void updateOne(Teacher teacher);

    /**
     * 删除一个教师的记录
     * @param teacherId 教师主键id
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void deleteById(Integer teacherId);
}
