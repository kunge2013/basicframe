package com.basic.core.jpa.controller;

import com.basic.core.jpa.bean.UserEntity;
import com.basic.core.jpa.dao.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/4/4
 * Time：15:36
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserJPA userJPA;

    /**
     * 查询用户列表方法
     * @return
     */
    @RequestMapping(value ="/list" ,method = RequestMethod.GET)
    public List<UserEntity> list(){
        return userJPA.findAll();
    }

    /**
     * 添加、更新用户方法
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public UserEntity save(UserEntity entity)
    {
        return userJPA.save(entity);
    }

//    /**
//     * 删除用户方法
//     * @param id 用户编号
//     * @return
//     */
//    @RequestMapping(value = "/delete",method = RequestMethod.GET)
//    public List<UserEntity> delete(Long id)
//    {
//        userJPA.delete(id);
//        return userJPA.findAll();
//    }

    /**
     * 分页查询测试
     * @param page 传入页码，从1开始
     * @return
     */
    @RequestMapping(value = "/cutpage")
    public List<UserEntity> cutPage(int page)
    {
        UserEntity user = new UserEntity();
        user.setSize(2);
        user.setSord("desc");
        user.setPage(page);
        //获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //设置排序对象参数
        Sort sort = new Sort(sort_direction, user.getSidx());
        //创建分页对象
        PageRequest pageRequest = new PageRequest(user.getPage() - 1,user.getSize(),sort);
        //执行分页查询
        return userJPA.findAll(pageRequest).getContent();
    }

}
