package com.paratera.sgri.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weixin")
public class ConfigParams {
    private static String appid;
    private static String appsecret;
    private static String appCode;

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        ConfigParams.appid = appid;
    }

    public static String getAppsecret() {
        return appsecret;
    }

    public static void setAppsecret(String appsecret) {
        ConfigParams.appsecret = appsecret;
    }

    public static String getAppCode() {
        return appCode;
    }

    public static void setAppCode(String appCode) {
        ConfigParams.appCode = appCode;
    }


}
