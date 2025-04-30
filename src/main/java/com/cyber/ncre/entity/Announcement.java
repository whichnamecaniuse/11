package com.cyber.ncre.entity;

import java.util.Date;

/**
 * 公告实体类
 */
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private String status;
    private Integer importance;
    private String imageUrl;
    private Date createTime;
    private Date publishTime;
    private Date updateTime;
    private String createdBy;

    public Announcement() {
    }

    public Announcement(Long id, String title, String content, String status, Integer importance, String imageUrl,
                      Date createTime, Date publishTime, Date updateTime, String createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.importance = importance;
        this.imageUrl = imageUrl;
        this.createTime = createTime;
        this.publishTime = publishTime;
        this.updateTime = updateTime;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", importance=" + importance +
                ", imageUrl='" + imageUrl + '\'' +
                ", createTime=" + createTime +
                ", publishTime=" + publishTime +
                ", updateTime=" + updateTime +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
} 