package com.xc.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xc.myblog.entity.Skills;

import com.xc.myblog.entity.User;
import com.xc.myblog.mapper.SkillsMapper;

import com.xc.myblog.mapper.UserMapper;
import com.xc.myblog.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SkillsServiceImpl extends ServiceImpl<SkillsMapper, Skills> implements SkillsService {
    @Autowired
    SkillsMapper skillsMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<String> getSkillsByUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        List<Skills> skills = skillsMapper.selectList(new QueryWrapper<Skills>().eq("user_id", user.getId()).select("skill"));
        List<String> ans = new ArrayList<>();
        for (Skills skill : skills) {
            ans.add(skill.getSkill());
        }
        return ans;
    }

    @Override
    public boolean updateSkills(List<String> skills, User user) {
        List<String> oldSkills = this.getSkillsByUsername(user.getUsername());
        //除旧迎新
        for (String oldSkill : oldSkills) {
            skillsMapper.delete(new QueryWrapper<Skills>().eq("skill", oldSkill));
        }
        for (String newSkill : skills) {
            skillsMapper.insert(new Skills(user.getId(), newSkill));
        }
        return true;
    }
}
