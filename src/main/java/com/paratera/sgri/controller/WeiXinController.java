package com.paratera.sgri.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paratera.sgri.config.Consts;
import com.paratera.sgri.entity.PositionEntity;
import com.paratera.sgri.utils.CheckUtil;
import com.paratera.sgri.utils.MenuUtil;
import com.paratera.sgri.utils.MessageUtil;
import com.paratera.sgri.utils.ResourceUtil;


/**
 * 与微信对接登录验证
 * 
 * @author guoxiaohu
 *
 */
@RestController
public class WeiXinController {

    @Autowired
    private PositionEntity post;

    @Autowired
    private MenuUtil menuUtil;

    @RequestMapping(value = "wx")
    public void loginWeiXin(HttpServletRequest req, HttpServletResponse res) {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        PrintWriter out = null;
        try {
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                out = res.getWriter();
                out.println(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }


    @RequestMapping(value = "wx", method = RequestMethod.POST)
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        Map<String, String> map = MessageUtil.xmlToMap(req);
        //ResourceUtil.uploadBrief("C:\\Users\\Administrator\\Desktop\\111111.mp3", "voice");
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String MsgTyep = map.get("MsgType");
        String message = "";
        MessageUtil mess = new MessageUtil();
        if (Consts.MESSAGE_TEXT.equals(MsgTyep)) {
            String Content = map.get("Content");
            if (Content.equals("?")) {
                Content = menuUtil.getHelpApi();
                message = mess.initMessageText(FromUserName, ToUserName, Content);
            } else if (Content.equals("1")) {
                Content = "有朋自远方来,不亦乐乎!";
                message = mess.initMessageText(FromUserName, ToUserName, Content);
            } else if (Content.equals("2")) {
                String MediaId = ResourceUtil.getBrief("image");
                Content = MediaId;
                message = mess.initMessageImage(FromUserName, ToUserName, Content);
            } else if (Content.equals("3")) {
                String MediaId = ResourceUtil.getBrief("voice");
                String Format = "amr";
                message = mess.initMessageVoice(FromUserName, ToUserName, MediaId, Format);
            } else if (Content.equals("4")) {
                String MediaId = ResourceUtil.getBrief("image");
                message = mess.initMessageMusic(FromUserName, ToUserName, MediaId);
            } else if (Content.equals("5")) {
                String MediaId = ResourceUtil.getBrief("video");
                message = mess.initMessageVideo(FromUserName, ToUserName, MediaId);
            }
        } else if (Consts.MESSAGE_IMAGE.equals(MsgTyep)) {
            String MediaId = map.get("MediaId");
            message = mess.initMessageImage(FromUserName, ToUserName, MediaId);
        } else if (Consts.MESSAGE_VOICE.equals(MsgTyep)) {
            String MediaId = map.get("MediaId");
            String Format = map.get("Format");
            message = mess.initMessageVoice(FromUserName, ToUserName, MediaId, Format);
        } else if (Consts.MESSAGE_VIDEO.equals(MsgTyep)) {
            String MediaId = map.get("MediaId");
            message = mess.initMessageVideo(FromUserName, ToUserName, MediaId);
        } else if (Consts.MESSAGE_LOCATION.equals(MsgTyep)) {
            String Location_X = map.get("Location_X");
            String Location_Y = map.get("Location_Y");
            String Label = map.get("Label");
            message = mess.initMessageLocation(FromUserName, ToUserName, Location_X, Location_Y, Label);
        } else if (Consts.MESSAGE_EVENT.equals(MsgTyep)) {
            String event = map.get("Event");
            StringBuilder sb = new StringBuilder();
            if (Consts.MESSAGE_CLICK.toUpperCase().equals(event)) {
                String eventKey = map.get("EventKey");
                if ("about".equals(eventKey)) {
                    sb.append("本公众号为测试公众号!").append("\n");
                    sb.append("获取更多详情,请关注'小虎国'公众号!");
                } else if ("help".equals(eventKey)) {
                    String content = menuUtil.getHelpApi();
                    sb.append(content);
                } else if ("weather".equals(eventKey)) {
                    sb.append(menuUtil.getWeather());
                } else if ("todayOfHistory".equals(eventKey)) {
                    sb.append(menuUtil.getHistory());
                } else if ("joke".equals(eventKey)) {
                    sb.append(menuUtil.getJoke());
                } else if ("news".equals(eventKey)) {
                    sb.append(menuUtil.getNews());
                }
            } else if (Consts.MESSAGE_SUBSCRIBE.equals(event)) {
                sb.append("欢迎关注本微信公众号!!!");
            } else if (Consts.MESSAGE_LOCATION.toUpperCase().equals(event)) {
                post.setLatitude(map.get("Latitude"));
                post.setLongitude(map.get("Longitude"));
            }
            message = mess.initMessageEvent(FromUserName, ToUserName, sb.toString());
        }
        System.err.println(message);
        out = res.getWriter();
        out.write(message);
        out.close();
    }
}
