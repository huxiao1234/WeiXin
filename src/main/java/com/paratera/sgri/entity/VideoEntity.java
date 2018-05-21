package com.paratera.sgri.entity;

import com.paratera.sgri.pojo.Video;

/**
 * 视频消息类型
 * 
 * @author guoxiaohu
 *
 */
public class VideoEntity extends BaseEntity {
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }

}
