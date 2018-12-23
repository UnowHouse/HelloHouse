package cn.unow.springmvc.servlet;

import cn.unow.springmvc.annotation.Controller;
import cn.unow.springmvc.annotation.Quatifier;
import cn.unow.springmvc.annotation.RequestMappring;
import cn.unow.springmvc.annotation.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.servlet
 *  @文件名:   DispatcherServlet
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:33
 *  @描述：    TODO
 */
public class DispatcherServlet extends HttpServlet {

    //保存扫描到的所有包名
    List<String> packageNames = new ArrayList<String>();

    //所有类的实例，key是注解的value，value是注解对应的类的实例对象
    Map<String, Object> instanceMap = new HashMap<String, Object>();

    /**
     * 处理器映射到的方法对象集合
     * key是地址栏上项目上下文后面接上的地址
     * value是对应的方法对象也就是Method对象
     * */
    Map<String, Object> handerMap = new HashMap<String, Object>();

    /**
     * 加载容器
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {

        scanPackage("cn.unow.springmvc");

        try {
            filterInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        buildHandleMap();

        ioc();

    }

    /**
     * 扫描全局变量instanceMap中所有注有@Controller注解的类
     * 扫描注有@Controller注解的类中所有的私有成员变量
     * 获取注有@Quatifier的私有成员变量字段
     * 通过@Quatifier的value，注入他们的实例
     */
    private void ioc() {

        if(this.instanceMap.isEmpty())
            return;

        for(Map.Entry<String,Object> entry : this.instanceMap.entrySet()){

            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for( Field field : fields ){

                field.setAccessible(true);

                if(field.isAnnotationPresent(Quatifier.class)){

                    Quatifier quatifier = field.getAnnotation(Quatifier.class);
                    String value = quatifier.value();

                    try {
                        field.set(entry.getValue(),instanceMap.get(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }

    /**
     * 扫描所有注有@Controller的类
     * 扫描他们注有@RequestMapping的方法
     * 将他们@RequestMapping的value和@Controller的value拼接
     * 形成 url 作为映射关系
     * 将url作为key，将method作为value，形成映射关系
     * 将他们的映射关系加载到全局变量handerMap
     * 同时也将他们的映射关系实例加载到全局变量instanceMap
     */
    private void buildHandleMap() {

        if(instanceMap.size() <= 0)
            return;

        Map<String,Object> temp = new HashMap<>();

        for(Map.Entry<String, Object> entry : this.instanceMap.entrySet()){
            if(entry.getValue().getClass().isAnnotationPresent(Controller.class)){

                Controller controller = entry.getValue().getClass().getAnnotation(Controller.class);
                String controllerValue = controller.value();
                Object cObj = instanceMap.get(controllerValue);

                Method[] methods = entry.getValue().getClass().getMethods();

                for(Method method : methods){

                    if(method.isAnnotationPresent(RequestMappring.class)){

                        String value = method.getAnnotation(RequestMappring.class).value();
                        String urlKey = "/"+controllerValue+"/"+value;
                        this.handerMap.put(urlKey,method);
                        temp.put(urlKey,cObj);
                    }
                }

            }else{
                continue;
            }

        }
        if(!temp.isEmpty()){
            for(Map.Entry<String, Object> entry : temp.entrySet()){
                this.instanceMap.put(entry.getKey(),entry.getValue());
            }
        }


    }

    /**
     * 扫描 packageNames 中所有的含有Controller注解，Service注解的类
     * 并将他们的注解value作为key，将他们的实例作为value，加载到全局变量instanceMap
     */
    private void filterInstance() throws Exception {
        if(this.packageNames.size() <= 0)
            return;

        for(String className : this.packageNames){

            Class<?> cName = Class.forName(className.replace(".class","").trim());

            if(cName.isAnnotationPresent(Controller.class)){

                Object instance = cName.newInstance();

                Controller controller = cName.getAnnotation(Controller.class);
                String key = controller.value();

                this.instanceMap.put(key,instance);

            }else if(cName.isAnnotationPresent(Service.class)){

                Object instance = cName.newInstance();

                Service service = cName.getAnnotation(Service.class);
                String key = service.value();

                this.instanceMap.put(key,instance);

            }else{
                continue;
            }

        }
        System.out.println(this.instanceMap.size());
    }

    /**
     * 扫描指定包下所有的类，并将他们加载到全部变量packageNames中
     * @param packagePath
     */
    private void scanPackage(String packagePath) {

        String location = "/" + replaceTo(packagePath);
        System.out.println("扫描到的地址："+location);

        URL url = this.getClass().getClassLoader().getResource(location);
        System.out.println("url:"+url);

        String filePath = url.getFile();
        File file = new File(filePath);
        String[] fileList  = file.list();

        for( String path : fileList ){
            File eachFile = new File(filePath + "/" + path);

            if(eachFile.isDirectory()){

                scanPackage(packagePath+"."+eachFile.getName());

            }else {

                String packName = packagePath+"."+eachFile.getName();
                String pName = packName.substring(0, packName.lastIndexOf(".class"));
                this.packageNames.add(pName);

            }
        }
        System.out.println("所有的包名加类名："+this.packageNames);
    }

    /**
     * 替换“.” 为 “/”
     * @param packageName
     * @return
     */
    private String replaceTo(String packageName) {

        return packageName.replaceAll("\\.","/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    /**
     * 获取请求的uri,将ContextPath部分除去
     * 得到的是一个Controller+RequestMappring的路径
     * 通过这个key，从全局变量insranceMap中查找该key的值
     * 获取到一个实例，并执行这个实例的方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        System.out.println("uri路径："+requestURI);

        String contextPath = req.getContextPath();
        String path = requestURI.replace(contextPath, "");
        System.out.println(path);


        Method method = (Method) handerMap.get(path);
        System.out.println(method);

        Object o = instanceMap.get(path);
        System.out.println(o);

        try {
            method.invoke(o,req,resp,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
