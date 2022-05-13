package com.example.advanceuse.mapper;

import com.example.advanceuse.bean.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {
    @Select("select * from users where username=#{name} and password=#{password}")
    Users selectUserByName(String name,String password);
    @Insert("INSERT INTO users (username,password,authorities) VALUES (#{username},#{password},#{authorities})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int addUser(Users users);

}
