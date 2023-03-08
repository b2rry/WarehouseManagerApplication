package com.appksa.warehousemanager.model;

import java.util.List;

public class SupplyItem {

    private Long id;
    private String title;
    private String date;
    private int startAmount;
    private int restAmount;
    private String bgColor;
    private List<DispatchEvent> dispatchEventsList;
    private String comment;

    //возможно нужны пустые конструкторы для десериализации из json

    public SupplyItem(Long id, String title, String date, int startAmount, String bgColor, List<DispatchEvent> dispatchEventsList, String comment) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startAmount = startAmount;
        this.bgColor = bgColor;
        this.dispatchEventsList = dispatchEventsList;
        this.comment = comment;
        setCorrectRestAmount();
    }

    public void setCorrectRestAmount(){
        restAmount = startAmount;
        for(DispatchEvent eventItem: dispatchEventsList){
            restAmount -= eventItem.getAmount();
        }
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

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
        setCorrectRestAmount();
    }

    public int getRestAmount() {
        return restAmount;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
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
