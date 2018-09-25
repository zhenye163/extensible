package com.netopstec.extensible.mapper;

import com.netopstec.extensible.annotation.DataSourceType;
import com.netopstec.extensible.common.DataSourceTypeEnum;
import com.netopstec.extensible.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhenye 2018/8/8
 */
@Mapper
public interface StudentMapper {
    /**
     * 遍历master数据库中的学生表信息
     * @return 学生列表结果集
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Student> findAllInMaster();

    /**
     * 清空slave数据库中的student表信息
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void truncateInSlave();

    /**
     * 遍历学生表
     * @return 学生列表结果集
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    List<Student> findAll();

    /**
     * 按主键id查询学生信息
     * @param studentId 学生主键id
     * @return 学生信息
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    Student findById(Integer studentId);

    /**
     * 新增一名学生
     * @param student  学生参数
     * @return 是否新增成功
     */
    @DataSourceType(DataSourceTypeEnum.master)
    Integer insertOne(Student student);

    /**
     * 更新学生信息
     * @param student 学生对象
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void updateOne(Student student);

    /**
     * 删除一个学生的记录
     * @param studentId 学生主键id
     */
    @DataSourceType(DataSourceTypeEnum.master)
    void deleteById(Integer studentId);

    /**
     * 查找某个班级的所有学生
     * @param classroomId 班级Id
     * @return 学生列表
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    List<Student> findByClassroomId(Integer classroomId);

    /**
     * 测试多数据源是否配置成功
     */
    @DataSourceType(DataSourceTypeEnum.master)
    Student findOneInMaster(Integer studentId);

    @DataSourceType(DataSourceTypeEnum.slave)
    Student findOneInSlave(Integer studentId);

    /**
     * 查询master数据库中，最近半小时被操作过的student数据
     * @return studentList
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Student> findOperatedInPastHalfHourInMaster();

    /**
     * 查询slave数据库中，是否存在该条student记录
     * @param studentId 学生主键id
     * @return 是否存在(存在时，返回1；不存在时，返回0)
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    Integer isExistInSlave(Integer studentId);

    /**
     * 将student列表记录，批量插入slave数据库中
     * @param toInsertStudentList student列表记录
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void batchInsertInSlave(List<Student> toInsertStudentList);

    /**
     * 批量更新slave数据库中的student列表记录
     * @param toUpdateStudentList student列表记录
     */
    @DataSourceType(DataSourceTypeEnum.slave)
    void batchUpdateInSlave(List<Student> toUpdateStudentList);

    /**
     * 查询master数据库中，昨天被操作过的student数据
     * @return studentList
     */
    @DataSourceType(DataSourceTypeEnum.master)
    List<Student> findOperatedYesterdayInMaster();
}
