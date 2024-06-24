package com.xc.myblog.config;

import cn.hutool.core.img.Img;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ImgUploadConfig implements WebMvcConfigurer {
    /**
     * 解决图片上传无法立即显示，而需要重启才能访问的问题。
     * 这是因为对服务器的保护措施导致的，服务器不能对外部暴露真实的资源路径，需要配置虚拟路径映射访问。
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取文件真实路径
        String path = System.getProperty("user.dir") + "/src/main/resources/upload/blog/Img/";
        //用户头像
        String avatar = System.getProperty("user.dir") + "/src/main/resources/upload/user/avatar/";
        //
        registry.addResourceHandler("/upload/blog/Img/**").addResourceLocations("file:" + path);
        registry.addResourceHandler("/upload/user/avatar/**").addResourceLocations("file:" + avatar);
    }
}
