package com.yb.course.mapper;

import com.yb.course.dto.CourseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author boyuan
 * @create 2018-08-31 14:33
 */
@Mapper
public interface CourseMapper {
    @Select("select * from pe_course")
    List<CourseDTO> listCourse();

    @Select("select user_id from pr_user_course where course_id=${course_id}")
    Integer getCourseTeacher(@Param("course_id") int courseId);
}
