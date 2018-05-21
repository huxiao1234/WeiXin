package com.paratera.sgri.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.config.Consts;
import com.paratera.sgri.entity.menu.Button;
import com.paratera.sgri.entity.menu.ClickButton;
import com.paratera.sgri.entity.menu.Menu;
import com.paratera.sgri.entity.PositionEntity;
import com.paratera.sgri.entity.menu.ViewButton;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Component
public class MenuUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuUtil.class);

    @Autowired
    private PositionEntity post;

    /**
     * 初始化菜单
     * 
     * @return
     */
    public String initMenu() {
        Menu menu = new Menu();

        ClickButton clickButton1 = new ClickButton();
        clickButton1.setName("笑话段子");
        clickButton1.setType(Consts.MESSAGE_CLICK);
        clickButton1.setKey("joke");

        ClickButton clickButton2 = new ClickButton();
        clickButton2.setName("音乐");
        clickButton2.setType(Consts.MESSAGE_CLICK);
        clickButton2.setKey("music");

        ClickButton clickButton3 = new ClickButton();
        clickButton3.setName("新闻");
        clickButton3.setType(Consts.MESSAGE_CLICK);
        clickButton3.setKey("news");

        ClickButton clickButton4 = new ClickButton();
        clickButton4.setName("历史上的今天");
        clickButton4.setType(Consts.MESSAGE_CLICK);
        clickButton4.setKey("todayOfHistory");

        ClickButton clickButton5 = new ClickButton();
        clickButton5.setName("天气");
        clickButton5.setType(Consts.MESSAGE_CLICK);
        clickButton5.setKey("weather");

        ClickButton clickButton6 = new ClickButton();
        clickButton6.setName("扫一扫");
        clickButton6.setType(Consts.MESSAGE_SCANCODE_PUSH);
        clickButton6.setKey("richScan_1");

        ClickButton clickButton7 = new ClickButton();
        clickButton7.setName("扫一扫 带提示");
        clickButton7.setType(Consts.MESSAGE_SCANCODE_WAITMSG);
        clickButton7.setKey("richScan_2");

        ClickButton clickButton8 = new ClickButton();
        clickButton8.setName("系统拍照发图");
        clickButton8.setType(Consts.MESSAGE_PIC_SYSPHOTO);
        clickButton8.setKey("hairFigure_1");

        ClickButton clickButton9 = new ClickButton();
        clickButton9.setName("拍照或者相册发图");
        clickButton9.setType(Consts.MESSAGE_PIC_PHOTO_OR_ALBUM);
        clickButton9.setKey("hairFigure_2");

        ClickButton clickButton10 = new ClickButton();
        clickButton10.setName("微信相册发图");
        clickButton10.setType(Consts.MESSAGE_PIC_WEIXIN);
        clickButton10.setKey("hairFigure_3");

        ViewButton viewButton1 = new ViewButton();
        viewButton1.setName("百度一下");
        viewButton1.setType(Consts.MESSAGE_VIEW);
        viewButton1.setUrl("http://www.baidu.com");

        ClickButton clickButton12 = new ClickButton();
        clickButton12.setName("发送位置");
        clickButton12.setType(Consts.MESSAGE_LOCATION_SELECT);
        clickButton12.setKey("location");

        ClickButton clickButton13 = new ClickButton();
        clickButton13.setName("赞一下");
        clickButton13.setType(Consts.MESSAGE_CLICK);
        clickButton13.setKey("praise");

        ClickButton clickButton14 = new ClickButton();
        clickButton14.setName("帮助");
        clickButton14.setType(Consts.MESSAGE_CLICK);
        clickButton14.setKey("help");

        ClickButton clickButton15 = new ClickButton();
        clickButton15.setName("关于");
        clickButton15.setType(Consts.MESSAGE_CLICK);
        clickButton15.setKey("about");

        Button button1 = new Button();
        button1.setName("按钮一");
        button1.setSub_button(new Button[]{clickButton1, clickButton2, clickButton3, clickButton4, clickButton5});

        Button button2 = new Button();
        button2.setName("按钮二");
        button2.setSub_button(new Button[]{clickButton6, clickButton7, clickButton8, clickButton9, clickButton10});

        Button button3 = new Button();
        button3.setName("按钮三");
        button3.setSub_button(new Button[]{viewButton1, clickButton12, clickButton13, clickButton14, clickButton15});

        menu.setButton(new Button[]{button1, button2, button3});

        return JSONObject.fromObject(menu).toString();
    }

    /**
     * 创建菜单
     * 
     * @param token
     * @param menu
     * @return
     * @throws Exception
     */
    public JSONObject createMenu() throws Exception {
        Map<String, String> querys = getToken();
        String menu = initMenu();
        String path = Consts.PATH + Consts.MENU + "/create";
        HttpResponse response = HttpUtil.doPost(Consts.HTTP, path, "POST", new HashMap<String, String>(), querys, menu);
        String str = EntityUtils.toString(response.getEntity());
        JSONObject json = JSONObject.fromObject(str);
        LOGGER.info("createMenu:{}", json);
        return json;
    }

    /**
     * 获取菜单
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public JSONObject getMenu() throws Exception {
        Map<String, String> querys = getToken();
        String path = Consts.PATH + Consts.MENU + "/get";
        HttpResponse response = HttpUtil.doGet(Consts.HTTP, path, "GET", new HashMap<String, String>(), querys);
        String str = EntityUtils.toString(response.getEntity());
        JSONObject json = JSONObject.fromObject(str);
        LOGGER.info("getMenu:{}", json);
        return json;
    }

    /**
     * 删除菜单
     * 
     * @param token
     * @return
     * @throws Exception
     */
    public JSONObject deleteMenu() throws Exception {
        Map<String, String> querys = getToken();
        String path = Consts.PATH + Consts.MENU + "/delete";
        HttpResponse response = HttpUtil.doGet(Consts.HTTP, path, "GET", new HashMap<String, String>(), querys);
        String str = EntityUtils.toString(response.getEntity());
        JSONObject json = JSONObject.fromObject(str);
        LOGGER.info("deleteMenu:{}", json);
        return json;
    }


    public Map<String, String> getToken() {
        String access_Token = TokenUtil.getAccess_token();
        Map<String, String> querys = Maps.newHashMap();
        querys.put("access_token", access_Token);
        return querys;
    }


    /**
     * 帮助文档
     * 
     * @return
     */
    public String getHelpApi() {
        StringBuilder sb = new StringBuilder();
        sb.append("欢迎关注本公众号:").append("\n");
        sb.append("1:\t").append("文本消息").append("\n");
        sb.append("2:\t").append("图片消息").append("\n");
        sb.append("3:\t").append("语音消息").append("\n");
        sb.append("4:\t").append("音乐消息").append("\n");
        sb.append("5:\t").append("视频消息").append("\n");
        sb.append("6:\t").append("图文消息").append("\n");
        sb.append("输入对应序号获取服务!");
        return sb.toString();
    }

    /**
     * 获取天气信息
     * 
     * @return
     */
    public String getWeather() {
        String appcode = ConfigParams.getAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        String content = "";
        if (StringUtils.isNotBlank(post.getLatitude()) && StringUtils.isNotBlank(post.getLongitude())) {
            String location = post.getLatitude() + "," + post.getLongitude();
            querys.put("location", location);
            try {
                HttpResponse response =
                        HttpUtil.doGet(Consts.WEATHER_HTTP, Consts.WEATHER, Consts.GET, headers, querys);
                String str = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.fromObject(str);
                content = parseWeatherJson(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            content = "对不起,您在暂时不能使用该功能。若您想要使用该功能,请打开提供位置信息。";
        }
        return content;
    }

    /**
     * 解析天气JSON数据
     * 
     * @param json
     */
    private String parseWeatherJson(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        if (json.size() > 0) {
            JSONObject result = json.getJSONObject("result");
            if (null != result && result.size() > 0) {
                String city = result.getString("city");
                JSONArray array = (JSONArray) result.get("daily");
                StringBuilder content = new StringBuilder();
                int size = array.size();
                for (int i = 0; i < size; i++) {
                    JSONObject dataJson = (JSONObject) array.get(i);
                    String date = dataJson.getString("date");
                    String week = dataJson.getString("week");
                    JSONObject day = dataJson.getJSONObject("day");
                    String weather = day.getString("weather");
                    content.append("日期:\t").append(date).append("\n");
                    content.append("星期:\t").append(week).append("\n");
                    content.append("天气情况:\t").append(weather).append("\n");
                    content.append("------------------").append("\n");
                }
                content.setLength(content.length() - 1);
                sb.append("城市:").append(city).append("\n");
                sb.append("------------------").append("\n");
                sb.append(content);
            }
        }
        return sb.toString();
    }


    /**
     * 获取历史上的今天
     */
    @SuppressWarnings("deprecation")
    public String getHistory() {
        String appcode = ConfigParams.getAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        Integer day = new Date().getDate();
        Integer month = new Date().getMonth() + 1;
        String realDay = day > 10 ? String.valueOf(day) : ( "0" + String.valueOf(day) );
        String realMonth = month > 10 ? String.valueOf(month) : ( "0" + String.valueOf(month) );
        String monthDay = realMonth + realDay;
        querys.put("day", monthDay);
        String content = "";
        try {
            HttpResponse response = HttpUtil.doGet(Consts.HISTORY_HTTP, Consts.HISTORY, Consts.GET, headers, querys);
            String str = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(str);
            content = parseHistoryJson(json, realDay, realMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 解析历史上的今天的数据
     * 
     * @param json
     */
    private String parseHistoryJson(JSONObject json, String realDay, String realMonth) {
        String monthDay = realMonth + "-" + realDay;
        StringBuilder sb = new StringBuilder();
        sb.append("历史上的今天发生:").append("\n");
        JSONArray array = json.getJSONObject("resultBody").getJSONArray("list");
        int size = array.size();
        int k = 1;
        for (int i = 0; i < size; i++) {
            JSONObject jo = (JSONObject) array.get(i);
            String year = jo.getString("year");
            String title = jo.getString("title");
            sb.append(k++).append(":").append(" ");
            sb.append(year + "-" + monthDay).append("  ");
            sb.append(title).append(".").append("\n");
        }
        return sb.toString();
    }


    /**
     * 获取笑话
     * 
     * @return
     */
    public String getJoke() {
        String appcode = ConfigParams.getAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("pagenum", "1");
        querys.put("pagesize", "1");
        querys.put("sort", "rand");
        String content = "";
        try {
            HttpResponse response = HttpUtil.doGet(Consts.JOKE_HTTP, Consts.JOKE, Consts.GET, headers, querys);
            String str = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(str);
            content = parseJokeJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 解析笑话JSON数据
     * 
     * @param json
     * @return
     */
    private String parseJokeJson(JSONObject json) {
        StringBuilder sb = new StringBuilder();
        JSONObject result = json.getJSONObject("result");
        JSONArray array = result.getJSONArray("list");
        int size = array.size();
        for (int i = 0; i < size; i++) {
            JSONObject jo = array.getJSONObject(i);
            String content = (String) jo.get("content");
            sb.append(content);
        }
        return sb.toString().trim();
    }

    /**
     * 获取今天新闻
     * 
     * @return
     */
    public String getNews() {
        String appcode = ConfigParams.getAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("type", "top");
        String content = "";
        try {
            HttpResponse response = HttpUtil.doGet(Consts.NEWS_HTTP, Consts.NEWS, Consts.GET, headers, querys);
            String str = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(str);
            content = parseNewsJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 解析新闻数据
     * 
     * @param json
     * @return
     */
    private String parseNewsJson(JSONObject json) {
        JSONObject result = json.getJSONObject("result");
        JSONArray array = result.getJSONArray("data");
        int size = array.size();
        StringBuilder sb = new StringBuilder();
        int rus = 1;
        for (int i = 0; i < size; i++) {
            if (rus > 10) {
                break;
            } else {
                JSONObject data = array.getJSONObject(i);
                sb.append(rus++).append(": ");
                sb.append(data.get("title")).append("\n");
                sb.append(data.get("url")).append("\n");
                sb.append("-----------------------").append("\n");
            }
        }
        return sb.toString();
    }
}
