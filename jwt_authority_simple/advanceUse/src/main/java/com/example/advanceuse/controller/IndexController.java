package com.example.advanceuse.controller;

import com.alibaba.fastjson.JSON;
import com.example.advanceuse.bean.entity.UrlAuthorities;
import com.example.advanceuse.bean.entity.Users;
import com.example.advanceuse.mapper.UrlAuthoritiesMapper;
import com.example.advanceuse.mapper.UsersMapper;
import com.example.advanceuse.security.TokenUtil;
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
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    UrlAuthoritiesMapper urlAuthoritiesMapper;
    @RequestMapping("hello")
    public String hello(){
        return "no need token hello";
    }

    @RequestMapping("login")
    public String login(String name,String password){
        Users users = usersMapper.selectUserByName(name,password);
        if (users==null){
            return "登录失败";
        }
        return tokenUtil.sign(String.valueOf(users.getId()),users.getUsername(),JSON.parseObject(users.getAuthorities(),List.class));
    }
    @RequestMapping("error")
    public Map<String,String> error(HttpServletRequest httpServletRequest){
        Map<String,String> map = new HashMap<>();
        map.put("code","500");
        Exception e = (Exception) httpServletRequest.getAttribute("exception");
        map.put("msg",e.getMessage());
        return map;
    }
    @RequestMapping("register")
    public String register(String name,String password,String authoritiesString){
        List<String> authorities = new ArrayList<>();
        String[] split = authoritiesString.split(",");
        for (String s:split){
            if(!"".equals(s.trim())){
                authorities.add(s);
            }
        }
        if(authorities.size()==0){
            return "error";
        }
        Users users = new Users();
        users.setUsername(name);
        users.setPassword(password);
        users.setAuthorities(JSON.toJSONString(authorities));
        usersMapper.addUser(users);
        return "success";
    }
    @RequestMapping("addUrlAuthorities")
    public String addUrlAuthorities(String url,String authoritiesString){
        List<String> authorities = new ArrayList<>();
        String[] split = authoritiesString.split(",");
        for (String s:split){
            if(!"".equals(s.trim())){
                authorities.add(s);
            }
        }
        if(authorities.size()==0){
            return "error";
        }
        UrlAuthorities urlAuthorities = new UrlAuthorities();
        urlAuthorities.setUrl(url);
        urlAuthorities.setAuthorities(JSON.toJSONString(authorities));
        urlAuthoritiesMapper.addUrlUrlAuthorities(urlAuthorities);
        return "success";
    }
}
