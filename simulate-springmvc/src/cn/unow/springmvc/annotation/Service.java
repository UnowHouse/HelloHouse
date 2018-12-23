package cn.unow.springmvc.annotation;

import java.lang.annotation.*;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.annotation
 *  @文件名:   Service
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:19
 *  @描述：    TODO
 */
@Target({ElementType.TYPE})//作用在类上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
