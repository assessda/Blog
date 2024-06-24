package com.xc.myblog.service;

import com.xc.myblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface UserService extends IService<User> {
    //是否有相同的用户
    public Boolean haveSameUserName(String username);
    //获取用户信息
    public User getUserInfoById(Long id);
    //获取个人信息、获赞数、总访问量
    public Map<String, Object> getUserDetailedInfoByUsername(String username);
    //修改个人信息
    public Boolean updateUserInfo(User user);
    //修改密码
    public Boolean updatePassword(User user, String password);
}
