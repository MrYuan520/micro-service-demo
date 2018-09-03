package com.yb.course.filter;

import com.yb.thrift.user.dto.UserDTO;
import com.yuanbo.user.client.LoginFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author boyuan
 * @create 2018-08-31 15:58
 */
public class CourseFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user",userDTO);
    }
}
