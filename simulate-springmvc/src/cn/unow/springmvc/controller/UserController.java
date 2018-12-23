package cn.unow.springmvc.controller;

import cn.unow.springmvc.annotation.Controller;
import cn.unow.springmvc.annotation.Quatifier;
import cn.unow.springmvc.annotation.RequestMappring;
import cn.unow.springmvc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.controller
 *  @文件名:   UserController
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:22
 *  @描述：    TODO
 */
@Controller("userController")
public class UserController {

    /**
     * 把UserService实例对象注入进来
     * */
    @Quatifier("UserServiceImpl")
    private UserService userService;

    @RequestMappring("insert.action")
    public String insert(HttpServletRequest request, HttpServletResponse response, String param){
        userService.insert(null);
        try {

            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ServletException e) {

            e.printStackTrace();

        }
        return null;

    }

    @RequestMappring("select.action")
    public String select(HttpServletRequest request, HttpServletResponse response, String param){

        userService.select(null);

        return null;
    }

    @RequestMappring("update.action")
    public String update(HttpServletRequest request, HttpServletResponse response, String param){

        userService.update(null);

        return null;
    }

    @RequestMappring("delete.action")
    public String delete(HttpServletRequest request, HttpServletResponse response, String param){

        userService.delete(null);

        return null;
    }

}
