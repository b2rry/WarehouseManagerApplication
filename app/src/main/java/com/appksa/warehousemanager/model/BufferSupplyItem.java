package com.appksa.warehousemanager.model;

import java.util.List;

public class BufferSupplyItem {

    private Long id;
    private String title;
    private String date;
    private String startAmount;
    private String restAvailableAmount;
    private int bgColor;
    private List<DispatchEvent> dispatchEventsList;
    private String comment;
    private boolean consumableMaterial;

    public BufferSupplyItem(Long id, String title, String date, String startAmount, int bgColor, List<DispatchEvent> dispatchEventsList, String comment, boolean consumableMaterial) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startAmount = startAmount;
        this.bgColor = bgColor;
        this.dispatchEventsList = dispatchEventsList;
        this.comment = comment;
        this.consumableMaterial = consumableMaterial;
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

    public String getRestAvailableAmount() {
        return restAvailableAmount;
    }

    public void setRestAvailableAmount(String restAvailableAmount) {
        this.restAvailableAmount = restAvailableAmount;
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

    public boolean isConsumableMaterial() {
        return consumableMaterial;
    }

    public void setConsumableMaterial(boolean consumableMaterial) {
        this.consumableMaterial = consumableMaterial;
    }
}