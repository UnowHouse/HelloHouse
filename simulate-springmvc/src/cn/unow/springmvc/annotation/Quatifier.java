package cn.unow.springmvc.annotation;

import java.lang.annotation.*;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.annotation
 *  @文件名:   Quatifier
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:20
 *  @描述：    TODO
 */

@Target({ElementType.FIELD})//作用在字段上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Quatifier {
    String value() default "";
}
