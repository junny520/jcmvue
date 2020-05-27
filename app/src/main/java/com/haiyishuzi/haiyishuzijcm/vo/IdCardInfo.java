package com.haiyishuzi.haiyishuzijcm.vo;

/**
 * FileName:IdCardInfo.java
 * 类的详细说明
 *
 * @author lavine
 * @Date 2019-07-29 17:27
 * @version:1.0.0
 */
import java.io.Serializable;

public class IdCardInfo implements Serializable {
    private static final long serialVersionUID = 409085379395233356L;
    private byte[] charInfo;
    private String imgPath;
    private String headPath;

    public IdCardInfo() {
    }

    public static long getSerialversionuid() {
        return 409085379395233356L;
    }

    public byte[] getCharInfo() {
        return this.charInfo;
    }

    public void setCharInfo(byte[] charInfo) {
        this.charInfo = charInfo;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getHeadPath() {
        return this.headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }
}
