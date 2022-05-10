package com.example.simple_use.controller;

import io.github.jefferyeven.jwt_authority.utils.JwtSecurityTokenUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    @RequestMapping("hello")
    public String hello(){
        return "no need token hello";
    }
    @RequestMapping("loginUser")
    public String loginUser(){
        List<String> authority = new ArrayList<>();
        authority.add("user");
        return JwtSecurityTokenUtil.sign("userTest",authority);
    }
    @RequestMapping("loginAdmin")
    public String loginAdmin(){
        List<String> authority = new ArrayList<>();
        authority.add("admin");
        return JwtSecurityTokenUtil.sign("adminTest",authority);
    }
}
