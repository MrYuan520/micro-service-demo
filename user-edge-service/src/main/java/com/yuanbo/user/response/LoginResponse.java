package com.yuanbo.user.response;

/**
 * @author boyuan
 * @create 2018-08-30 17:22
 */
public class LoginResponse extends Response {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(String token){
        this.token = token;
    }

}


