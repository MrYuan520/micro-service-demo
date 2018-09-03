package com.yuanbo.user.response;

import java.io.Serializable;

/**
 * @author boyuan
 * @create 2018-08-30 15:38
 */
public class Response implements Serializable {

    public static final Response USERNAME_PASSWORD_INVALID =new Response("1001", "username or password is error");
    public static final Response MOBILE_OR_EMAIl_REQUIRED =new Response("1002", "mobile_or_email_required");
    public static final Response SEND_VERIFYCODE_FAILED =new Response("1003", "send_verifycode_failed");
    public static final Response SUCCESS = new Response();
    public static final Response VERIFY_CODE_INVALID =new Response("1004",
            "verify_code_invalid");

    private String code;
    private String message;

    public Response(){
        this.code = "0";
        this.message = "success";
    }

    public Response(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static Response exception(Exception e){
        return new Response("9999",e.getMessage());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
