package com.sep.admin.utils;

import com.sep.admin.model.Employee;
import com.sep.admin.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TokenUtils {
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmployeeService employeeService;

    private static final int token_expire = 30; // token有效期

    public String create(Integer userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        token = userId.toString()+"_"+token;
        redisUtil.setEx(token,token,token_expire , TimeUnit.MINUTES);
        return token;
    }

    public Employee getCurrentAdminUname(HttpServletRequest request){
        //从cookie里获取token
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("Authentication")){
                String token = cookie.getValue();
                if(StringUtils.isNotBlank(token)){
                    Integer employeeId =  Integer.parseInt(token.split("_")[0]);
                    Employee employee = employeeService.getById(employeeId);
                    return employee;
                }
            }
        }
        return null;
    }

}