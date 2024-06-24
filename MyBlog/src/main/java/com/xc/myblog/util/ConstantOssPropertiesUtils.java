package com.xc.myblog.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantOssPropertiesUtils implements InitializingBean {

    @Value("${tencent.yun.secret-id}")
    private String secretId;

    @Value("${tencent.yun.secret-key}")
    private String secretKey;

    @Value("${tencent.yun.storage-bucket}")
    private String storageBucket;
    @Value("${tencent.yun.place}")
    private String place;

    public static String Tencent_secretId;
    public static String Tencent_secretKey;
    public static String Tencent_storageBucket;
    public static String Tencent_place;


    @Override
    public void afterPropertiesSet() throws Exception {
        Tencent_secretId = secretId;
        Tencent_secretKey = secretKey;
        Tencent_storageBucket = storageBucket;
        Tencent_place = place;
    }
}