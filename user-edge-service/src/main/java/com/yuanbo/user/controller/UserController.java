package com.yuanbo.user.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.yb.thrift.user.UserInfo;
import com.yb.thrift.user.dto.UserDTO;
import com.yuanbo.user.redis.RedisClient;
import com.yuanbo.user.response.LoginResponse;
import com.yuanbo.user.response.Response;
import com.yuanbo.user.thrift.ServiceProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author boyuan
 * @create 2018-08-30 14:17
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;
    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestParam("username") String username, @RequestParam("password") String
            password) {

        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (userInfo == null) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        //生成token
        String token = getToken();

        //缓存用户
        redisClient.set(token, toDTO(userInfo), 3600);

        return new LoginResponse(token);

    }

    @RequestMapping(value = "/sendVeriftCode",method = RequestMethod.POST)
    @ResponseBody
    public Response sendVerifConde(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email) {
        String message = "Verify code is :";
        String code = randomCode("0123456789",6);
        try {
            boolean result = false;
            if (StringUtils.isNotBlank(mobile)) {
                result = serviceProvider.getMessageService().sendMobileMessage(mobile,message+code);
                redisClient.set(mobile,code);
            } else if (StringUtils.isNotBlank(email)) {
                result = serviceProvider.getMessageService().sendEmailMessage(email,message+code);
                redisClient.set(email,code);
            } else {
                return Response.MOBILE_OR_EMAIl_REQUIRED;
            }

            if(!result){
                return Response.SEND_VERIFYCODE_FAILED;
            }
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "mobile", required = false) String mobile,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam("verifyCode") String verifyCode) {
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (StringUtils.isNotBlank(mobile)) {
            String redisCode = redisClient.get(mobile);
            if (!verifyCode.equals(redisCode)){
                return Response.VERIFY_CODE_INVALID;
            }
        } else {
            String redisCode = redisClient.get(email);
            if (!verifyCode.equals(redisCode)){
                return Response.VERIFY_CODE_INVALID;
            }
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;

    }


    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    @ResponseBody
    public UserDTO authentication(@RequestParam("token") String token){
        System.out.println("sssssssssssss"+ token);
        return redisClient.get(token);
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }

    private String getToken() {

        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);

    }

    private String randomCode(String s, int size) {
        StringBuilder stringBuilder = new StringBuilder(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            stringBuilder.append(s.charAt(loc));
        }
        return stringBuilder.toString();
    }


    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
