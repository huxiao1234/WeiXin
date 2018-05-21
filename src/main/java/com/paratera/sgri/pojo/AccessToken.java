package com.paratera.sgri.pojo;

/**
 * 获取微信端的accessToken
 * 
 * @author guoxiaohu
 *
 */
public class AccessToken {
    /*
     * 获取得到的凭证
     */
    private String access_token;
    /*
     * 获取凭证有效的时间
     */
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
