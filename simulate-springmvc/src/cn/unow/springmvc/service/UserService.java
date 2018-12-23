package cn.unow.springmvc.service;

import java.util.Map;

/*
 *  @项目名：  simulate-springmvc 
 *  @包名：    cn.unow.springmvc.service
 *  @文件名:   UserService
 *  @创建者:   Unow
 *  @创建时间:  2018/12/23 11:21
 *  @描述：    TODO
 */
public interface UserService {
    int insert(Map map);

    int delete(Map map);

    int update(Map map);

    int select(Map map);
}
