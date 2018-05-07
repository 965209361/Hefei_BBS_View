package com.model;

public class Hefei_bbs {
    private Integer id;

    private String listurl;

    private String title;

    private String content;

    private String fbsj;

    private String comment;

    private Integer commentcount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListurl() {
        return listurl;
    }

    public void setListurl(String listurl) {
        this.listurl = listurl == null ? null : listurl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFbsj() {
        return fbsj;
    }

    public void setFbsj(String fbsj) {
        this.fbsj = fbsj == null ? null : fbsj.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    @Override
    public String toString() {
        return "Hefei_bbs{" +
                "id=" + id +
                ", listurl='" + listurl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", fbsj='" + fbsj + '\'' +
                ", comment='" + comment + '\'' +
                ", commentcount=" + commentcount +
                '}';
    }
}