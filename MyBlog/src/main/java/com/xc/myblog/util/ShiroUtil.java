package com.xc.myblog.util;

import com.xc.myblog.shiro.AccountProfile;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}