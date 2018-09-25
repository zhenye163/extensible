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
     * 遍历master数据库中的老师表信息
     * @return 老师列表结果集
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Teacher> findAllInMaster();

    /**
     * 清空slave数据库中的teacher表信息
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void truncateInSlave();

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

    /**
     * 查询master数据库中，最近半小时被操作过的teacher数据
     * @return teacherList
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Teacher> findOperatedInPastHalfHourInMaster();

    /**
     * 查询slave数据库中，是否存在该条teacher记录
     * @param teacherId 老师主键id
     * @return 是否存在(存在时，返回1；不存在时，返回0)
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    Integer isExistInSlave(Integer teacherId);

    /**
     * 将teacher列表记录，批量插入slave数据库中
     * @param toInsertTeacherList teacher列表记录
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void batchInsertInSlave(List<Teacher> toInsertTeacherList);

    /**
     * 批量更新slave数据库中的teacher列表记录
     * @param toUpdateTeacherList teacher列表记录
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void batchUpdateInSlave(List<Teacher> toUpdateTeacherList);

    /**
     * 查询master数据库中，昨天被操作过的teacher数据
     * @return teacherList
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Teacher> findOperatedYesterdayInMaster();
}
