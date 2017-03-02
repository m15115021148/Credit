package com.sitemap.weifang.model;

/**
 * @desc 新闻实体类
 * Created by chenmeng on 2016/12/21.
 */
public class NewsModel {

    /**
     * id : 6908
     * html : <p><a class="f-fl" href="http://www.wfcredit.gov.cn/detail.findnewsbyid?id=2616" target="_blank" title="滨州市地税局寒亭分局税银合作倡行守信激励精准破解小微企业融资难题 ">滨州市地税局寒亭分局税银合作倡行守信激励精准破解小微企业融资难题</a></p>

     <p>&nbsp;</p>

     <p><a href="http://baidu.com">aa</a></p>

     * ckType : 32
     * createTime : 1474268376000
     * img :
     * title : 滨州市地税局寒亭分局税银合作倡行守信激励精准破解小微企业融资难题
     * summary : 滨州市地税局寒亭分局税银合作倡行守信激励精准破解小微企业融资难题
     */

    private String id;//
    private String html;//内容
    private String ckType;//新闻类型
    private String createTime;//发布时间
    private String img;//图片
    private String title;//标题
    private String summary;//来源

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
