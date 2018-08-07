package com.netopstec.extensible.domain;

import com.netopstec.extensible.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhenye 2018/8/7
 */
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
