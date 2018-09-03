package com.yuanbo.user.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yb.thrift.user.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author boyuan
 * @create 2018-08-31 10:48
 */
public abstract class LoginFilter implements Filter {

    private static Cache<String,UserDTO> cache = CacheBuilder.newBuilder().maximumSize(10000)
            .expireAfterWrite(3, TimeUnit.MINUTES).build();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)){
            Cookie[] cookies = request.getCookies();
            if (cookies != null){
                for (Cookie c : cookies){
                    if(c.getName().equals("token")){
                        token = c.getValue();
                    }
                }
            }
        }
        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)){
            userDTO = cache.getIfPresent(token);
            if(userDTO == null){
                userDTO = requestUserInfo(token);
                if(userDTO != null){
                    cache.put(token,userDTO);
                }
            }
        }
        if(userDTO == null){
            response.sendRedirect("http://192.168.5.103:8082/user/login");
            return;
        }

        login(request,response,userDTO);
        filterChain.doFilter(request,response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO
            userDTO);

    private UserDTO requestUserInfo(String token) {
        String url = "http://192.168.5.103/user/authentication";
        HttpClient client  = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        post.addHeader("token",token);
        InputStream content =null;
        try {
            nvps.add(new BasicNameValuePair("token",token));
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new RuntimeException("request user info failed! StatusLine:"+response.getStatusLine());
            }
            content = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            int len = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((len = content.read(bytes))>0){
                stringBuilder.append(new String(bytes,0,len));
            }
            UserDTO userDTO = new ObjectMapper().readValue(stringBuilder.toString(),UserDTO.class);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (content != null){
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
