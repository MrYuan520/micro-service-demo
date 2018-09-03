package com.yb.course.dto;

import com.yb.thrift.user.dto.TeacherDTO;

import java.io.Serializable;

/**
 * @author boyuan
 * @create 2018-08-31 14:12
 */
public class CourseDTO implements Serializable {

    private int id;
    private String title;
    private String descirotion;
    private TeacherDTO teacherDto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescirotion() {
        return descirotion;
    }

    public void setDescirotion(String descirotion) {
        this.descirotion = descirotion;
    }

    public TeacherDTO getTeacherDto() {
        return teacherDto;
    }

    public void setTeacherDto(TeacherDTO teacherDto) {
        this.teacherDto = teacherDto;
    }
}
