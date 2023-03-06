package com.appksa.warehousemanager.model;

public class LogBookItem {

    private String createTime;
    private String logComment;

    public LogBookItem(String createTime, String logComment) {
        this.createTime = createTime;
        this.logComment = logComment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLogComment() {
        return logComment;
    }

    public void setLogComment(String logComment) {
        this.logComment = logComment;
    }
}
