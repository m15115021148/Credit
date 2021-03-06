package com.sitemap.weifang.model;

/**
 * @desc 获取我的名片夹 实体类
 * Created by chenmeng on 2016/12/20.
 */
public class CardCaseModel {
    private String cardID;//名片id
    private String xm;//名片名称
    private String code;//编号
    private String userID;//用户id
    private String company;//所处公司（当返回””时，APP显示“无”）
    private String phone;//手机号
    private String email;//邮箱号

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
