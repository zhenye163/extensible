package com.netopstec.extensible.mapper;

import com.netopstec.extensible.annotation.DataSourceType;
import com.netopstec.extensible.common.DataSourceTypeEnum;
import com.netopstec.extensible.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    @DataSourceType(DataSourceTypeEnum.slave)
    List<Classroom> findAll();

    /**
     * 按id查询该班级的详情
     * @param classroomId 班级id
     * @return 班级详情
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    Classroom findById(Integer classroomId);

    /**
     * 新开一个班级
     * @param classroom  班级参数
     * @return 是否新增成功
     */
    @DataSourceType(DataSourceTypeEnum.master)
    Integer insertOne(Classroom classroom);

    /**
     * 更新班级信息
     * @param classroom 班级对象
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void updateOne(Classroom classroom);

    /**
     * 删除一个班级的记录
     * @param classroomId 班级主键id
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void deleteById(Integer classroomId);

    /**
     * 按年级以及班号，查班级情况
     * @param grade 年级
     * @param classNo 班号
     * @return 班级情况
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    List<Classroom> findByGradeAndClassNo(@Param("grade") Integer grade, @Param("classNo") Integer classNo);

}
