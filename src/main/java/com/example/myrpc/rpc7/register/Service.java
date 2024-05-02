package com.example.myrpc.rpc7.register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 应用于类、接口等
@Retention(RetentionPolicy.RUNTIME) // 在运行时保留注解信息，以便可以通过反射读取
public @interface Service {
    String value() default ""; // 可以用于指定服务名称等
}
