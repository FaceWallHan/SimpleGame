package com.example.simple.bean;

public class BeanNews {
    private int newsId;
    private String newsType,picture,content,title,url;
    private String publicTime;
    private String praiseCount;

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        //发布时间
        this.publicTime = publicTime;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        //评论数
        this.praiseCount = praiseCount;
    }


    public BeanNews() {
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
