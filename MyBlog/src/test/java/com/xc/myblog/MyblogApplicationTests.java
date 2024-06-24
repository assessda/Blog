package com.xc.myblog;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xc.myblog.entity.Blog;
import com.xc.myblog.entity.User;
import com.xc.myblog.mapper.BlogMapper;
import com.xc.myblog.mapper.UserMapper;
import com.xc.myblog.service.AddressService;
import com.xc.myblog.service.BlogService;
import com.xc.myblog.service.UserService;
import com.xc.myblog.service.impl.AddressServiceImpl;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MyblogApplicationTests {
    @Autowired
    AddressService addressService;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;


    private static Page page = new Page(1, 5);

    @Test
    void contextLoads() {
        System.out.println(SecureUtil.md5("92b69e8389559abb871af962c8f016c4"));
    }

    @Test
    void testHost() throws UnknownHostException {
        System.out.println(addressService.getAddressAndPort());
    }

    @Test
    void testMyBatis() {
        List<String> labels = Arrays.asList(new String[]{"SpringBoot"});
        List<Long> ids = new ArrayList<>();
        ids.add(9l);
        ids.add(10l);
        ids.add(15l);
        ids.add(28l);
        ids.add(30l);
        //IPage<Blog> blogs = blogMapper.selectPageByCreated(new Page(1, 5), "createdAsc");
        IPage<Blog> blogs = blogMapper.selectPageOfList(new Page(1, 5), "praiseDesc", ids, labels);
        System.out.println(blogs.getTotal());
        System.out.println(blogs.getPages());
       for (Blog blog : blogs.getRecords()) {
           System.out.println(blog);
        }
    }

    @Test
    void testBlogService() {
        IPage page = blogService.getPageOfUsername(new Page(1, 5), "admin");
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
        for (Object record : page.getRecords()) {
            System.out.println(record);
        }
    }

    @Test
    void testUpdatePassword() {
        User user = new User();
        user.setId(11l);
        user.setPassword("111");
        userService.updatePassword(user, "111111");
    }

    @Test
    void testAddress() {
        System.out.println(addressService.getAddress());
    }

    @Test
    void testTencentCos() {

    }

    @Test
    void testSearchMapper() {
        IPage<Blog> springList = blogService.getPageOfSearch(page, "Spring", "viewsDesc");
        System.out.println(springList.getPages());
        System.out.println(springList.getTotal());
        for (Blog blog : springList.getRecords()) {
            System.out.println(blog);
        }
    }

    @Test
    void testSort() {
        IPage<Blog> springList = blogService.getPageOfInfo(page, "praiseDesc");
        System.out.println(springList.getPages());
        System.out.println(springList.getTotal());
        for (Blog blog : springList.getRecords()) {
            System.out.println(blog);
        }
    }

}
