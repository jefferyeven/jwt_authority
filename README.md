

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
## 引入maven
```xml
<dependency>
    <groupId>io.github.jefferyeven</groupId>
    <artifactId>jwt_authority</artifactId>
</dependency>
```

## 1. 最简单
例子：https://github.com/jefferyeven/jwt_authority/tree/master/jwt_authority_simple/SimpleUse
### 1.1 设置jwt的一些配置
在application.properties上添加该项。
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

### 2.1 实现TokenVerifyer(自定义jwt token)
我们可以从header/session/cookie/requset读取token的值，然后验证url所需要的权限和自己有的权限，
具体的实现可以看下方的代码。
```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Component
public class TokenUtil implements TokenVerifyer {
    public String salt = "fdelsfjdo.243";
    public long expireTime = 100000000;
    String isser = "test";
    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(salt)).withIssuer(isser).build();
    @Override
    public VerifyTokenResult verifyToken(HttpServletRequest request, HttpServletResponse response) {
        VerifyTokenResult verifyTokenResult = new VerifyTokenResult();
        // 从head读取token
        String tokenName = "token";
        String token = request.getHeader(tokenName);
//        // 从session读取token
//        HttpSession httpSession = request.getSession();
//        token = (String) httpSession.getAttribute(tokenName);
//        // 从cookie读取token
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie:cookies){
//            System.out.println(cookie.getName());
//            if(cookie.getName().equals(tokenName)){
//                token = cookie.getValue();
//                System.out.println(token);
//                break;
//            }
//        }
//        // 从request读取token
//        token = request.getHeader(tokenName);

        if(token==null){
            throw new JwtSecurityException(JwtResponseMag.NoTokenError);
        }
        try {
            DecodedJWT jwt = verifier.verify(token);
            verifyTokenResult.setPassVerify(true);
            Claim authoritiesClaim =  jwt.getClaims().get("authorities");
            if(authoritiesClaim==null){
                return verifyTokenResult;
            }
            List<String> authorities = JSONObject.parseArray(authoritiesClaim.asString(),String.class);
            verifyTokenResult.setAuthorities(authorities);
        } catch (Exception e){
            verifyTokenResult.setPassVerify(false);
        }
        return verifyTokenResult;
    }
    public String sign(String userid, String name, List<String> authorities){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + expireTime);
            token = JWT.create()
                    .withIssuer(isser)
                    .withClaim("userid",userid)
                    .withClaim("username", name)
                    .withClaim("authorities", JSON.toJSONString(authorities))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(salt));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
}

```
在配置类 设置verifyer
```
    @Override
    public void config(AuthenizationConfig authenizationConfig){
        authenizationConfig.setStrategyTokenVerifyer(new TokenUtil());
    }
```
### 2.2开启注解
1. 在安全配置类开启使用注解
```
    @Override
    public void config(AuthenizationConfig authenizationConfig){
        // 开启注解
        authenizationConfig.setUseAnnoation(true);
    }
```
2.可以修饰类和修饰方法上(只支持加在controller)
```
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
```
### 2.3 设置配置类
```java
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

    @Override
    public void config(AuthenizationConfig authenizationConfig){
        authenizationConfig.setStrategyTokenVerifyer(new TokenUtil());
        // 开启注解
        authenizationConfig.setUseAnnoation(true);
    }

}
```
## 3. 进阶使用
自定义失败处理器，自定义验证策略，自定义动态的权限认证
