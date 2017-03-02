package com.sitemap.weifang.model;

/**
 * @desc 获取我的信用服务 实体类
 * Created by chenmeng on 2016/12/23.
 */
public class CreditServiceModel {

    private String mc;//名称主题
    private String content;//详情
    private String result;//处理结果
    private String type;//类别  （1信用监督 2信用咨询 3信用申报 4信用异议）
    private String inputDate;//录入时间
    private String updateTime;//更新时间

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
