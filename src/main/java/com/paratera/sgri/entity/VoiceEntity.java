package com.paratera.sgri.entity;

import com.paratera.sgri.pojo.Voice;

/**
 * 语音消息类型
 * 
 * @author guoxiaohu
 *
 */
public class VoiceEntity extends BaseEntity {
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }

}
