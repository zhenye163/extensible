package com.netopstec.extensible.entity;


import lombok.Data;

/**
 * @author zhenye 2018/8/7
 */
@Data
public class Student {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 性别（0：男、1：女）
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 班级id
     */
    private Integer classroomId;
    /**
     * （0：未删除，1：已删除）
     */
    private Integer flag;
}
