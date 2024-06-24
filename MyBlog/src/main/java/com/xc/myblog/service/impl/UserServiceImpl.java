package com.xc.myblog.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.Skills;
import com.xc.myblog.entity.User;
import com.xc.myblog.mapper.SkillsMapper;
import com.xc.myblog.mapper.UserMapper;
import com.xc.myblog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public UserMapper getBaseMapper() {
        return super.getBaseMapper();
    }
    @Autowired
    UserMapper userMapper;
    @Autowired
    PraiseService praiseService;
    @Autowired
    BlogInfoService blogInfoService;
    @Autowired
    BlogService blogService;
    @Autowired
    SkillsService skillsService;

    /*判断名字是否相同*/
    @Override
    public Boolean haveSameUserName(String username) {
        UserMapper userMapper = this.getBaseMapper();
        //查询名称相同的数量
        int count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUserInfoById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public Map<String, Object> getUserDetailedInfoByUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        Map<String, Object> map = new HashMap<>();
        //查询点赞数
        Long praises = Long.valueOf(praiseService.getPraisesByUser(user)) ;
        //查询文章数
        Long blogCount = Long.valueOf(blogService.count(new QueryWrapper<Blog>().eq("user_id", user.getId())));
        //查询访问数
        Long views = 0l;
        for (Blog blog : blogService.list()) {
            //获取user的博客访问量
            if (blog.getUserId() == user.getId()) {
                views += blogInfoService.getViews(blog.getId());
            }
        }
        //查询商场标签
        List<String> skills = skillsService.getSkillsByUsername(username);
        user.setSkills(skills);
        //加入map
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("avatar", user.getAvatar());
        map.put("email", user.getEmail());
        map.put("praises", praises);
        map.put("views", views);
        map.put("blogCount", blogCount);
        map.put("profile", user.getProfile());
        map.put("skills", user.getSkills());
        return map;
    }

    @Override
    public Boolean updateUserInfo(User user) {
        //修改user表的信息
        userMapper.update(user, new QueryWrapper<User>().eq("id", user.getId()));
        //新增skills标签
        skillsService.updateSkills(user.getSkills(), user);
        return null;
    }

    @Override
    public Boolean updatePassword(User user, String newPassword) {
        //如果密码正确,否则抛出异常
        if (SecureUtil.md5(user.getPassword()).equals(userMapper.selectById(user.getId()).getPassword())) {
            //进行md5加密
            user.setPassword(SecureUtil.md5(newPassword));
            //修改密码
            userMapper.update(user, new QueryWrapper<User>().eq("id", user.getId()).select("password"));
            return true;
        } else {
            return false;
        }
    }


}
