package cn.unow.springmvc.annotation;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.annotation
 *  @文件名:   RequestMappring
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:17
 *  @描述：    TODO
 */

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMappring {
    String value() default "";
}
