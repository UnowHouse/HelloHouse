package cn.unow.springmvc.service.impl;

import cn.unow.springmvc.annotation.Service;
import cn.unow.springmvc.service.UserService;

import java.util.Map;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.service.impl
 *  @文件名:   UserServiceImpl
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:21
 *  @描述：    TODO
 */

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Override
    public int insert(Map map) {
        System.out.println("调用了插入方法");
        return 0;
    }

    @Override
    public int delete(Map map) {
        System.out.println("调用了删除方法");
        return 0;
    }

    @Override
    public int update(Map map) {
        System.out.println("调用了更新方法");
        return 0;
    }

    @Override
    public int select(Map map) {
        System.out.println("调用了选择方法");
        return 0;
    }
}
