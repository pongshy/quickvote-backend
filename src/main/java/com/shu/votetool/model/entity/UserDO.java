package com.shu.votetool.model.entity;

public class UserDO {
    private String openid;

    private String wname;

    private String wimage;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname == null ? null : wname.trim();
    }

    public String getWimage() {
        return wimage;
    }

    public void setWimage(String wimage) {
        this.wimage = wimage == null ? null : wimage.trim();
    }
}