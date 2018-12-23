# 模仿SpringMVC 

注：知识的巩固和代码的实践来自从csdn博主，感谢他的总结。

#1.关于注解的学习


##1.1.注解的基础

###1.1.1.注解的定义
Java文件叫做Annotation，用`@interface`表示。

###1.1.2.元注解
`@interface`上面按需要注解上一些东西，包括`@Retention`、`@Target`、`@Document`、`@Inherited`四种。

###1.1.3.注解的保留策略

- @Retention(RetentionPolicy.SOURCE)  
  注解仅存在于源码中，在class字节码文件中不包含

- @Retention(RetentionPolicy.CLASS)    
  默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得

- @Retention(RetentionPolicy.RUNTIME) 
  注解会在class字节码文件中存在，在运行时可以通过反射获取到

###1.1.4.注解的作用目标

- @Target(ElementType.TYPE)                      
  接口、类、枚举、注解

- @Target(ElementType.FIELD)                     
  字段、枚举的常量

- @Target(ElementType.METHOD)                 
  方法

- @Target(ElementType.PARAMETER)            
  方法参数

- @Target(ElementType.CONSTRUCTOR)       
  构造函数

- @Target(ElementType.LOCAL_VARIABLE)   
  局部变量

- @Target(ElementType.ANNOTATION_TYPE) 
  注解

- @Target(ElementType.PACKAGE)               
  包  

###1.1.5.注解包含在javadoc中

@Documented

###1.1.6.注解可以被继承

@Inherited

###1.1.7.注解解析器
