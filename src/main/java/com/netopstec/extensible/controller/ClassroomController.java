package com.netopstec.extensible.controller;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping("/all")
    public List<Classroom> findAll(){
        return classroomService.findAll();
    }
}
