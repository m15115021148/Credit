package com.sitemap.weifang.model;

/**
 * @desc 注册返回实体类
 * Created by chenmeng on 2016/12/20.
 */
public class RegisterModel {
    private String xm;//姓名或组织机构名称
    private String codeID;//code的id
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }
}
