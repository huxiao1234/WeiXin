package com.paratera.sgri.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.config.Consts;
import com.paratera.sgri.pojo.AccessToken;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author 获取Token
 *
 */
@Component
public class TokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);

    @Scheduled(cron = "0 0 0/1 * * *")
    public static AccessToken getToken() {
        AccessToken token = new AccessToken();
        Map<String, String> querys = Maps.newHashMap();
        querys.put("grant_type", "client_credential");
        querys.put("appid", ConfigParams.getAppid().trim());
        querys.put("secret", ConfigParams.getAppsecret().trim());
        String path = Consts.PATH + Consts.TOKEN;
        JSONObject json = null;
        Jedis jedis = RedisUtil.getJedis();
        try {
            HttpResponse response = HttpUtil.doGet(Consts.HTTP, path, "GET", new HashMap<String, String>(), querys);
            //获取response的body
            String str = EntityUtils.toString(response.getEntity());
            json = JSONObject.fromObject(str);
            if (null != json) {
                token.setAccess_token(json.getString("access_token"));
                token.setExpires_in(json.getInt("expires_in"));
                jedis.set("access_token", json.getString("access_token"));
                jedis.expire("access_token", 60 * 60);
            }
            LOGGER.info("json:{}", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String getAccess_token() {
        Jedis jedis = RedisUtil.getJedis();
        String access_token = jedis.get("access_token");
        if (null == access_token) {
            AccessToken token = getToken();
            access_token = token.getAccess_token();
        }
        return access_token;
    }
}
