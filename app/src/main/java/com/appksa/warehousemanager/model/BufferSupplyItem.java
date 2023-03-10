package com.appksa.warehousemanager.model;

import java.util.List;

public class BufferSupplyItem {

    private Long id;
    private String title;
    private String date;
    private String startAmount;
    private String restAmount;
    private int bgColor;
    private List<DispatchEvent> dispatchEventsList;
    private String comment;

    public BufferSupplyItem(Long id, String title, String date, String startAmount, int bgColor, List<DispatchEvent> dispatchEventsList, String comment) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startAmount = startAmount;
        this.bgColor = bgColor;
        this.dispatchEventsList = dispatchEventsList;
        this.comment = comment;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(String startAmount) {
        this.startAmount = startAmount;
    }

    public String getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(String restAmount) {
        this.restAmount = restAmount;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public List<DispatchEvent> getDispatchEventsList() {
        return dispatchEventsList;
    }

    public void setDispatchEventsList(List<DispatchEvent> dispatchEventsList) {
        this.dispatchEventsList = dispatchEventsList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}