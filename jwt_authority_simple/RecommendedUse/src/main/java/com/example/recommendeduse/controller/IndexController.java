package com.example.recommendeduse.controller;

import com.example.recommendeduse.security.TokenUtil;
import io.github.jefferyeven.jwt_authority.utils.JwtSecurityTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    TokenUtil tokenUtil;
    @RequestMapping("hello")
    public String hello(){
        return "no need token hello";
    }
    @RequestMapping("loginUser")
    public String loginUser(){
        List<String> authority = new ArrayList<>();
        authority.add("user");
        return tokenUtil.sign("123","userTest",authority);
    }
    @RequestMapping("loginAdmin")
    public String loginAdmin(){
        List<String> authority = new ArrayList<>();
        authority.add("admin");
        return tokenUtil.sign("123","adminTest",authority);
    }
    @RequestMapping("error")
    public Map<String,String> error(HttpServletRequest httpServletRequest){
        Map<String,String> map = new HashMap<>();
        map.put("code","500");
        Exception e = (Exception) httpServletRequest.getAttribute("exception");
        map.put("msg",e.getMessage());
        return map;
    }
}
