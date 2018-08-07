package com.netopstec.extensible.controller;

import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/all")
    public List<Teacher> findAll(){
        return teacherService.findAll();
    }
}
