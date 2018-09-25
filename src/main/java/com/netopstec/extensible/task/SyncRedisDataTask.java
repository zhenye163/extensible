package com.netopstec.extensible.task;

import com.netopstec.extensible.entity.Classroom;
import com.netopstec.extensible.entity.Student;
import com.netopstec.extensible.entity.Teacher;
import com.netopstec.extensible.mapper.ClassroomMapper;
import com.netopstec.extensible.mapper.StudentMapper;
import com.netopstec.extensible.mapper.TeacherMapper;
import com.netopstec.extensible.util.JsonUtil;
import com.netopstec.extensible.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 需要制定一个任务，来同步redis与master中的数据(明天凌晨1:30)
 * @author zhenye 2018/9/21
 */
@Component
@Slf4j
public class SyncRedisDataTask implements QuartzScheduleTask {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    public void exec() {
        log.info("即将依据master数据库信息，来同步Redis中的信息");
        List<Student> studentList = studentMapper.findOperatedYesterdayInMaster();
        for(Student student : studentList){
            Integer studentId = student.getId();
            if (student.getFlag() == 0) {
                redisUtil.hset("student",String.valueOf(studentId), JsonUtil.obj2String(student));
            } else {
                redisUtil.hdel("student",String.valueOf(studentId));
            }
        }
        List<Teacher> teacherList = teacherMapper.findOperatedYesterdayInMaster();
        for(Teacher teacher : teacherList){
            Integer teacherId = teacher.getId();
            if (teacher.getFlag() == 0){
                redisUtil.hset("teacher",String.valueOf(teacherId), JsonUtil.obj2String(teacher));
            } else {
                redisUtil.hdel("teacher",String.valueOf(teacherId));
            }
        }
        List<Classroom> classroomList = classroomMapper.findOperatedYesterdayInMaster();
        for(Classroom classroom : classroomList){
            Integer classroomId = classroom.getId();
            if(classroom.getFlag() == 0){
                redisUtil.hset("classroom",String.valueOf(classroomId), JsonUtil.obj2String(classroom));
            } else {
                redisUtil.hdel("classroom",String.valueOf(classroomId));
            }
        }
        log.info("成功同步master数据库与Redis中的信息");
    }
}
