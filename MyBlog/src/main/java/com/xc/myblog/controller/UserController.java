package com.xc.myblog.controller;


import com.xc.myblog.common.lang.Result;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.UserService;
import com.xc.myblog.util.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2022-02-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public Result save(@Valid @RequestBody User user) {
        userService.save(user);
        return Result.success(user);
    }

    @GetMapping("/userDetailed/{username}")
    public Result getUserDetailed(@PathVariable("username") String username) {
        Map<String, Object> map = userService.getUserDetailedInfoByUsername(username);
        return Result.success(map);
    }

    @PutMapping("/modify")
    @RequiresAuthentication
    public Result modifyUser(@RequestBody User user) {
        if (ShiroUtil.getProfile().getId() == user.getId()) {
            userService.updateUserInfo(user);
            return Result.success("修改成功");
        } else {
            return Result.fail("无权操作");
        }
    }

    @PutMapping("/modify/password")
    @RequiresAuthentication
    public Result modifyUserPassword(@RequestBody Map<String, String> map) {
        User user = new User();
        String newPassword = map.get("newPassword");
        user.setPassword(map.get("oldPassword"));
        user.setId(Long.valueOf(map.get("userId")));
        if (ShiroUtil.getProfile().getId() == user.getId()) {
            //修改成功
            if (userService.updatePassword(user, newPassword)) {
                return Result.success("修改成功");
            } else {
                //修改失败
                return Result.fail(400, "密码错误", null);
            }

        } else {
            return Result.fail("无权操作");
        }
    }



}
