package com.yb.thrift.user.dto;

import javax.jws.soap.SOAPBinding;

/**
 * @author boyuan
 * @create 2018-08-31 14:18
 */
public class TeacherDTO extends UserDTO {

    private String intro;
    private int stars;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
