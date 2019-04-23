package com.netopstec.extensible.controller;

import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/findAll")
    public List<Teacher> findAll(){
        return teacherService.findAll();
    }

    @GetMapping("/findOne/{teacherId}")
    public Teacher findOne(@PathVariable("teacherId") Integer teacherId){
        return teacherService.findOne(teacherId);
    }

    @PostMapping("/insertOne")
    public String insertOne(Teacher teacher){
        return teacherService.insertOne(teacher);
    };

    @PutMapping("/updateOne")
    public String updateOne(Teacher teacher){
        return teacherService.updateOne(teacher);
    }

    @DeleteMapping("/deleteOne/{teacherId}")
    public String deleteOne(@PathVariable("teacherId") Integer teacherId){
        return teacherService.deleteOne(teacherId);
    }
}
