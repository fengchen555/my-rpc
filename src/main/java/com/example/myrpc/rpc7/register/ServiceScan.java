package com.example.myrpc.rpc7.register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 应用于类
@Retention(RetentionPolicy.RUNTIME) // 在运行时保留注解信息
public @interface ServiceScan {
    String value() default "com.example.myrpc.rpc7"; // 默认扫描包
}
