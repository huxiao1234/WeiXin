package com.paratera.sgri.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.paratera.sgri.config.Consts;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * 素材管理类
 */
public class ResourceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);


    /**
     * 上传临时素材
     * 
     * @return
     * 
     * @throws IOException
     */
    public static void uploadBrief(String path, String type) throws IOException {
        String access_token = TokenUtil.getAccess_token();
        File file = new File(path);
        if (!file.exists()) {
            LOGGER.error("path:{}不存在,请检查。", path);
            System.exit(0);
        }
        String url = Consts.HTTP + Consts.PATH + "/media/upload?access_token=" + access_token + "&type=" + type + "";
        URL urlObj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setRequestMethod(Consts.POST);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        //设置请求头信息  
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        //设置边界  
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流  
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        //输出表头  
        out.write(head);

        //文件正文部分  
        //把文件已流文件的方式 推入到url中  
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while (( bytes = in.read(bufferOut) ) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        //结尾部分  
        byte[] foot = ( "\r\n--" + BOUNDARY + "--\r\n" ).getBytes("utf-8");//定义最后数据分隔线  
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应  
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while (( line = reader.readLine() ) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObj = JSONObject.fromObject(result);
        String typeName = "media_id";
        if ("thumb".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        Jedis jedis = RedisUtil.getJedis();
        if ("image".equals(type)) {
            jedis.sadd("imageMediaId", mediaId);
        } else if ("voice".equals(type)) {
            jedis.sadd("voiceMediaId", mediaId);
        } else if ("video".equals(type)) {
            jedis.sadd("videoMediaId", mediaId);
        } else if ("thumb".equals(type)) {
            jedis.sadd("thumbMediaId", mediaId);
        }
        RedisUtil.returnResource(jedis);
    }


    /**
     * 从缓存中获取临时素材的mediaId
     * 
     * @return
     * 
     * @throws URISyntaxException
     */
    public static String getBrief(String type) throws URISyntaxException {
        Set<String> mediaIds = Sets.newHashSet();
        Jedis jedis = RedisUtil.getJedis();
        if ("image".equals(type)) {
            mediaIds = jedis.smembers("imageMediaId");
        } else if ("voice".equals(type)) {
            mediaIds = jedis.smembers("voiceMediaId");
        } else if ("video".equals(type)) {
            mediaIds = jedis.smembers("videoMediaId");
        } else if ("thumb".equals(type)) {
            mediaIds = jedis.smembers("thumbMediaId");
        }
        String mediaId = "";
        Iterator<String> it = mediaIds.iterator();
        while (it.hasNext()) {
            mediaId = it.next();
            break;
        }
        return mediaId;
    }


}
