package com.example.advanceuse.controller;

import io.github.jefferyeven.jwt_authority.annoation.NeedAuthorize;
import io.github.jefferyeven.jwt_authority.bean.PermissionLevel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@NeedAuthorize(authorizeLevel = PermissionLevel.HAVE_ANY_AUTHORITY,authorties = {"admin"})
public class UserController {
    @RequestMapping("/hello")
    public String hello(){
        return "user hello";
    }
    @RequestMapping("needAdmin")
    @NeedAuthorize(authorizeLevel = PermissionLevel.HAVE_ANY_AUTHORITY,authorties = {"admin"})
    public String needAdmin(){
        return "need admin";
    }
}
