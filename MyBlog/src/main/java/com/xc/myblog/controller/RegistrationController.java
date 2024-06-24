package com.xc.myblog.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.xc.myblog.common.lang.Result;
import com.xc.myblog.entity.User;
import com.xc.myblog.service.AddressService;
import com.xc.myblog.service.FileService;
import com.xc.myblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/create")
public class RegistrationController {
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    FileService fileService;

    @GetMapping("/test")
    public Object test() {
        return "This is a test page";
    }

    @PostMapping("/registration")
    public Result registrationUser(@RequestParam("user") String userStr) {
        User user = JSONUtil.parse(userStr).toBean(User.class);
        if (user == null || user.getUsername() == null || user.getPassword() == null ||  user.getEmail()  == null || user.getAvatar() == null) {
            return Result.fail("用户名、密码、头像、邮箱均不能为空");
        }
        if (user.getUsername().length() == 0 || user.getPassword().length() == 0 || user.getAvatar().length() == 0 || user.getEmail().length() ==0) {
            return Result.fail("用户名、密码、头像、邮箱均不能为空");
        }
        //设置默认值
        user.setStatus(1);
        user.setCreated(LocalDateTime.now());
        user.setPassword(SecureUtil.md5(user.getPassword()));
        //如果已经注册相同的名字，就不能注册
        if (userService.haveSameUserName(user.getUsername())) {
            return Result.fail("用户名已经注册");
        } else {
            userService.save(user);
            return Result.success("注册成功");
        }
    }

    @GetMapping("/checkUserName/{username}")
    public Result isSameUserName(@PathVariable("username") String username) {
        if (userService.haveSameUserName(username)) {
            return Result.success(201, "用户名已经注册", "");
        } else {
            return Result.success("用户名可用");
        }
    }

    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestPart MultipartFile avatar) throws IOException {
        //获取上传文件(返回的为文件存储的路径)
        String url = fileService.fileUpload(avatar);
        return Result.success("上传成功",url);
    }

    /*取消上传图片或者删除*/
    /*@DeleteMapping("/deleteAvatar")
    @ResponseBody
    public Result deleteBlogImage(@RequestParam("filename") String fileName) {
        //从文件名称获取
        //图片路径
        String path = System.getProperty("user.dir") + "/src/main/resources/upload/user/avatar/" + fileName;
        //如果不存在，那么删除失败
        File file = new File(path);
        if (!file.exists()) {
            return Result.fail("图片不存在！");
        } else {
            file.delete();
            return Result.success("删除成功");
        }
    }*/


}
