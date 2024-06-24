package com.xc.myblog.service;

import com.xc.myblog.entity.Skills;
import com.xc.myblog.entity.User;

import java.util.List;

public interface SkillsService {
    //获取所有Skills标签根据用户吗名
    public List<String> getSkillsByUsername(String username);
    //更新一个用户的所有skills
    public boolean updateSkills(List<String> skills, User user);
}
