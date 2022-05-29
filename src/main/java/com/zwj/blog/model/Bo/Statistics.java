package com.zwj.blog.model.Bo;

import java.io.Serializable;

/**
 * 后台统计对象
 */
public class Statistics implements Serializable {

    private Integer articles;
    private Long comments;
    private Long links;
    private Long attachs;

    public Integer getArticles() {
        return articles;
    }

    public void setArticles(Integer articles) {
        this.articles = articles;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getLinks() {
        return links;
    }

    public void setLinks(Long links) {
        this.links = links;
    }

    public Long getAttachs() {
        return attachs;
    }

    public void setAttachs(Long attachs) {
        this.attachs = attachs;
    }

    @Override
    public String toString() {
        return "StatisticsBo{" +
                "articles=" + articles +
                ", comments=" + comments +
                ", links=" + links +
                ", attachs=" + attachs +
                '}';
    }
}
