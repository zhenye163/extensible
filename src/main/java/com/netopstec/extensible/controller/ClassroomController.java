package com.netopstec.extensible.controller;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhenye 2018/8/7
 */
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @RequestMapping("/findAll")
    public List<Classroom> findAll(){
        return classroomService.findAll();
    }

    @GetMapping("/findOne/{classroomId}")
    public Classroom findOne(@PathVariable("classroomId") Integer classroomId){
        return classroomService.findOne(classroomId);
    }

    @PostMapping("/insertOne")
    public String insertOne(Classroom classroom){
        return classroomService.insertOne(classroom);
    };

    @PutMapping("/updateOne")
    public String updateOne(Classroom classroom){
        return classroomService.updateOne(classroom);
    }

    @DeleteMapping("/deleteOne/{classroomId}")
    public String deleteOne(@PathVariable("classroomId") Integer classroomId){
        return classroomService.deleteOne(classroomId);
    }
}
