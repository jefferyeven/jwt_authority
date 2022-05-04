package io.github.jefferyeven.jwt_authority.annoation;

import io.github.jefferyeven.jwt_authority.bean.PermissionLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface NeedAuthorize {
    /**
     * 设置所需要权限的等级
     */
    PermissionLevel authorizeLevel() default PermissionLevel.HAVE_TOKEN;

    /**
     * 设置该注解所需要的权限
     */
    String[] authorties() default {};
}
