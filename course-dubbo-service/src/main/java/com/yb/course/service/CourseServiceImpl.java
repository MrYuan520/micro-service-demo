package com.yb.course.service;

import com.yb.course.dto.CourseDTO;
import com.yb.course.service.ICourseService;
import com.yb.course.mapper.CourseMapper;
import com.yb.thrift.user.UserInfo;
import com.yb.thrift.user.dto.TeacherDTO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author boyuan
 * @create 2018-08-31 14:26
 */
@Service
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courseDTOS = courseMapper.listCourse();
        if (courseDTOS != null){
            for (CourseDTO courseDTO:courseDTOS){
                Integer teacherId = courseMapper.getCourseTeacher(courseDTO.getId());
                if(teacherId != null){
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        courseDTO.setTeacherDto(tran2teacher(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        return courseDTOS;
    }

    private TeacherDTO tran2teacher(UserInfo userInfo) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(userInfo,teacherDTO);
        return teacherDTO;
    }
}
