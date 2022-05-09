package io.github.jefferyeven.jwt_authority;

import com.example.springbootsecurityjwtdemo.bean.dto.AuthoritiesPermissionDto;
import com.example.springbootsecurityjwtdemo.mapper.AuthoritiesPermissionMapper;
import io.github.jefferyeven.jwt_authority.authenization.AbstractAuthenization;
import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import io.github.jefferyeven.jwt_authority.bean.AuthenizationState;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class DynamicAuthenization extends AbstractAuthenization {
    @Autowired
    JwtSecurityConfig jwtSecurityConfig;
    @Autowired
    AuthoritiesPermissionMapper authoritiesPermissionMapper;
    private final String noMatch = "don't find match url";
    Map<String,Set<String>> urlAuthoritiesMap = new HashMap<>();
    @Override
    public AuthenizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(jwtSecurityConfig.haveSqlUpdate){
            updateUrlPermissionMap();
        }
        String findUrl = findMathUrl(request.getRequestURI());
        if (noMatch.equals(findUrl)) {
            return AuthenizationState.UnAuthenizateState;
        }
        Set<String> needAuthority = urlAuthoritiesMap.get(findUrl);
        VerifyTokenResult verifyTokenResult = AuthenizationStrategyManger.getStrategyTokenVerifyer().verifyToken(request,response);
        if(!verifyTokenResult.isPassVerify()){
            return AuthenizationState.NoPassState;
        }
        List<String> authorities = verifyTokenResult.getAuthorities();
        for(String authority:authorities){
            if(needAuthority.contains(authority)){
                return AuthenizationState.PassState;
            }
        }
        return AuthenizationState.NoPassState;
    }
    public void updateUrlPermissionMap(){
        urlAuthoritiesMap.clear();
        if(jwtSecurityConfig.haveSqlUpdate){
            List<AuthoritiesPermissionDto> authoritiesPermissionDtoList = authoritiesPermissionMapper.selectAllAuthoritiesPermissionDto();

            for(AuthoritiesPermissionDto authoritiesPermissionDto:authoritiesPermissionDtoList){
                String url = authoritiesPermissionDto.getUrl();
                String name = authoritiesPermissionDto.getName();
                Set<String> urlAuthorities = urlAuthoritiesMap.getOrDefault(url,new HashSet<>());
                urlAuthorities.add(name);
                urlAuthoritiesMap.put(url,urlAuthorities);
            }
        }
        jwtSecurityConfig.haveSqlUpdate = false;
    }


    private String findMathUrl(String url) {
        if (urlAuthoritiesMap.containsKey(url)) {
            return url;
        }
        String[] mathes = url.split("/");
        for (int i = mathes.length - 1; i >= 0; i--) {
            StringBuilder res = new StringBuilder();
            for (int j = 0; j < i; j++) {
                res.append(mathes[j]).append("/");
            }
            res.append("*");
            if (urlAuthoritiesMap.containsKey(res.toString())) {
                return res.toString();
            }
        }
        return noMatch;
    }
}
