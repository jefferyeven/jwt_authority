

# 前言
这个项目是面向jwt的，所以不需要进行登录验证。
这个项目是从https://gitee.com/jefferyeven/jwt-authenization 抽离出来的，
如果想看详细的开发与设计思路可以看一下上面的项目链接。下面我将介绍一些他的的些特性和使用教程。
# 原理
原理其实是非常简单，主要是通过过滤器进行鉴权，把没有通过权限认证的请求拦截下来，
通过的请求放行。

具体来说：当url来到过滤器时，首先会经过一个鉴权器链（责任链模式）找到能够处理该url的鉴权器
（当url进入鉴权器后，会转成一个字典树。将该字典树与鉴权器的hashmap进行匹配，
找到该与最详细的url所匹配的鉴权策略，找不到进入下一个鉴权器）。

# 特性
- 面向jwt
- 面向权限的（没有面向角色，只面向权限）
- 多种验证策略（have token/permit all/deny all/have any authority/have all authority）
- 灵活的设定访问url的权限 （基础鉴权器/注解鉴权器/自定义的鉴权器）
- 将 验证器 验证策略 鉴权器 都抽象出来便于灵活替换。

# 使用方式

## 1. 最简单
例子：
### 1.1 设置jwt的一些配置
```properties
#token的过期时间
jwt_token.expire_time = 10000000
#token发布者
jwt_token.isser = test
#token的加密的盐
jwt_token.token_secret = token123
#token放入header的name
jwt_token.token_header_name = token

```
### 1.2 extend 配置类 和加上 @Configuration
```java
import io.github.jefferyeven.jwt_authority.config.HttpConfig;
import io.github.jefferyeven.jwt_authority.config.JwtSecurityConfigAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecurityConfig extends JwtSecurityConfigAdapter {

    @Override
    public void config(HttpConfig httpConfig) {
        httpConfig.addConfigUrlsPermission("/admin/*").haveAnyAuthority("admin");
        httpConfig.addConfigUrlsPermission("/user/*").haveAnyAuthority("user","admin");
        httpConfig.addConfigUrlsPermission("index/*").permitAll();
        // 如果没有设置我们就默认允许
        httpConfig.getDefaultUrlConfig().permitAll();
    }
}
```
## 2. 推荐


