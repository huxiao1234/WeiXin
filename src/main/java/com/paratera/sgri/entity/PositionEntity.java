package com.paratera.sgri.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 地理位置
 * 
 * @author Administrator
 *
 */
@Component
@Scope(value = "singleton")
public class PositionEntity {

    /**
     * 地理位置纬度
     */
    private  String Latitude;

    /**
     * 地理位置经度
     */
    private  String Longitude;

    public  String getLatitude() {
        return Latitude;
    }

    public  void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public  String getLongitude() {
        return Longitude;
    }

    public  void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
