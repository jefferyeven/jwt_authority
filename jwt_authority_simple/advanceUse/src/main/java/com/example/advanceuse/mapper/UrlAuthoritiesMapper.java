package com.example.advanceuse.mapper;

import com.example.advanceuse.bean.entity.UrlAuthorities;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UrlAuthoritiesMapper {
    @Select("SELECT * from urlauthorities where url = #{url}")
    UrlAuthorities getUrlAuthoritiesFromUrl(String url);

    @Insert("insert into urlauthorities(url,authorities) values(#{url},#{authorities})")
    int addUrlUrlAuthorities(UrlAuthorities urlAuthorities);
}
