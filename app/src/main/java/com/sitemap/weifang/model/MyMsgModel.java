package com.sitemap.weifang.model;

/**
 * @desc 获取我的消息
 * Created by chenmeng on 2016/12/21.
 */
public class MyMsgModel {
    private String result;//结果
    private String errorMsg;//
    private String msg;//最新推送新闻标题
    private String creditService;//最新信用服务标题
    private String card;//最新名片添加申请

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreditService() {
        return creditService;
    }

    public void setCreditService(String creditService) {
        this.creditService = creditService;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
