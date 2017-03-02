package com.sitemap.weifang.model;

import java.io.Serializable;

/**
 * @desc 查询实体类 个人
 * Created by chenmeng on 2016/12/21.
 */
public class SearchPersonModel{

    /**
     * result : 1
     * xm : 蒋丽
     * code : 370721197005163649
     * personID : a7aaebfb22b4490f8b0d416e047ae374
     */

    private String result;
    private String xm;
    private String code;
    private String personID;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
