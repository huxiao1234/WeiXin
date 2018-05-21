package com.paratera.sgri.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.common.collect.Maps;
import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.config.Consts;
import com.paratera.sgri.entity.ImageEntity;
import com.paratera.sgri.entity.LocationEntity;
import com.paratera.sgri.entity.MusicEntity;
import com.paratera.sgri.entity.TextEntity;
import com.paratera.sgri.entity.VideoEntity;
import com.paratera.sgri.entity.VoiceEntity;
import com.paratera.sgri.pojo.Image;
import com.paratera.sgri.pojo.Music;
import com.paratera.sgri.pojo.Video;
import com.paratera.sgri.pojo.Voice;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    @Resource
    private ConfigParams config;

    /**
     * 将微信的请求参数转为map
     * 
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException {
        Map<String, String> map = Maps.newHashMap();
        SAXReader reader = new SAXReader();
        InputStream in = null;
        in = req.getInputStream();
        Document doc = reader.read(in);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element element : list) {
            map.put(element.getName(), element.getText());
        }
        in.close();
        return map;
    }

    /**
     * 将文本消息转为XML
     * 
     * @param <T>
     * 
     * @param message
     * @return
     */
    public <T> String entityToXML(T text) {
        XStream xstream = new XStream();
        xstream.alias("xml", text.getClass());
        return xstream.toXML(text);
    }

    /**
     * 发送文本消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageText(String FromUserName, String ToUserName, String content) {
        TextEntity text = new TextEntity();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        text.setMsgType(Consts.MESSAGE_TEXT);
        return entityToXML(text);
    }

    /**
     * 发送图片消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageImage(String FromUserName, String ToUserName, String MediaId) {
        ImageEntity image = new ImageEntity();
        Image im = new Image();
        image.setCreateTime(new Date().getTime());
        image.setMsgType(Consts.MESSAGE_IMAGE);
        image.setFromUserName(ToUserName);
        image.setToUserName(FromUserName);
        im.setMediaId(MediaId);
        image.setImage(im);
        return entityToXML(image);
    }

    /**
     * 发送语音消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageVoice(String FromUserName, String ToUserName, String MediaId, String Format) {
        VoiceEntity voice = new VoiceEntity();
        Voice vo = new Voice();
        voice.setCreateTime(new Date().getTime());
        voice.setFromUserName(ToUserName);
        voice.setToUserName(FromUserName);
        voice.setMsgType(Consts.MESSAGE_VOICE);
        vo.setMediaId(MediaId);
        vo.setFormat(Format);
        voice.setVoice(vo);
        return entityToXML(voice);
    }

    /**
     * 发送视频消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageVideo(String FromUserName, String ToUserName, String MediaId) {
        VideoEntity video = new VideoEntity();
        Video vo = new Video();
        video.setCreateTime(new Date().getTime());
        video.setFromUserName(ToUserName);
        video.setToUserName(FromUserName);
        video.setMsgType(Consts.MESSAGE_VIDEO);
        vo.setMediaId(MediaId);
        vo.setTitle("小视频");
        vo.setDescription("请点击观看小视频!");
        video.setVideo(vo);
        return entityToXML(video);
    }

    /**
     * 发送地理位置消息封装
     * 
     * @param label
     * @param scale
     * @param location_Y
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageLocation(String FromUserName, String ToUserName, String location_X, String location_Y,
            String Label) {
        LocationEntity loc = new LocationEntity();
        loc.setCreateTime(new Date().getTime());
        loc.setFromUserName(ToUserName);
        loc.setToUserName(FromUserName);
        loc.setMsgType(Consts.MESSAGE_TEXT);
        StringBuilder sb = new StringBuilder();
        sb.append("您所在的地址为是:").append(Label).append("。").append("\n");
        sb.append("地理位置维度为:").append(location_X).append("。").append("\n");
        sb.append("地理位置经度为:").append(location_Y).append("。");
        loc.setContent(sb.toString());
        return entityToXML(loc);
    }

    /**
     * 关注事件
     * 
     * @param fromUserName
     * @param toUserName
     * @return
     */
    public String initMessageEvent(String FromUserName, String ToUserName, String content) {
        TextEntity text = new TextEntity();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        text.setMsgType(Consts.MESSAGE_TEXT);
        return entityToXML(text);
    }

    /**
     * 发送音乐消息封装
     * 
     * @param FromUserName
     * @param ToUserName
     * @return
     */
    public String initMessageMusic(String FromUserName, String ToUserName, String MediaId) {
        MusicEntity music = new MusicEntity();
        Music mu = new Music();
        music.setToUserName(FromUserName);
        music.setFromUserName(ToUserName);
        music.setMsgType(Consts.MESSAGE_MUSIC);
        music.setCreateTime(new Date().getTime());
        mu.setThumbMediaId(MediaId);
        mu.setHQMusicUrl("http://weixintest.tunnel.echomod.cn/WeiXin/music.mp3");
        mu.setMusicUrL("http://weixintest.tunnel.echomod.cn/WeiXin/music.mp3");
        mu.setDescription("请点击播放音乐!");
        mu.setTitle("音乐");
        music.setMusic(mu);
        return entityToXML(music);
    }
}
