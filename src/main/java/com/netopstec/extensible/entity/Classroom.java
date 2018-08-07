package com.netopstec.extensible.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zhenye 2018/8/7
 */
@Entity
@Data
public class Classroom {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 年级
     */
    private Integer grade;
    /**
     * 班号
     */
    private Integer classNo;
    /**
     * 语文老师id
     */
    private Integer chineseTeacherId;
    /**
     * 数学老师id
     */
    private Integer mathTeacherId;
    /**
     * 英文老师id
     */
    private Integer englishTeacherId;
    /**
     * （0：未删除，1：已删除）
     */
    private Integer flag;
}
