package com.sitemap.weifang.model;

/**
 * @desc 返回结果信息
 * Created by chenmeng on 2016/12/20.
 */

public class ResultModel {
    private String result;//结果（1：成功，2：失败）
    private String errorMsg;//注册失败时的错误信息

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
}
