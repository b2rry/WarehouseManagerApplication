package com.appksa.warehousemanager.model;

import java.util.List;

public class SupplyItem{

    protected Long id;
    protected String title;
    protected String date;
    protected Integer startAmount;
    protected Integer restAvailableAmount;
    protected Integer restFactualAmount;
    protected Integer bgColor;//на самом деле тут лежит id цвета из ресурсов
    protected List<DispatchEvent> dispatchEventsList;
    protected String comment;
    protected Boolean consumableMaterial;

    public SupplyItem(Long id, String title, String date, int startAmount, int bgColor, List<DispatchEvent> dispatchEventsList, String comment, boolean consumableMaterial) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startAmount = startAmount;
        this.bgColor = bgColor;
        this.dispatchEventsList = dispatchEventsList;
        this.comment = comment;
        this.consumableMaterial = consumableMaterial;
        setCorrectRestAmounts();
    }
    public SupplyItem(){
    }

    public SupplyItem(Long id){
        this.id = id;
    }

    public void setCorrectRestAmounts(){
        restAvailableAmount = startAmount;
        for(DispatchEvent eventItem: dispatchEventsList){
            restAvailableAmount -= eventItem.getAmount();
        }
        restFactualAmount = restAvailableAmount;
        for(DispatchEvent eventItem: dispatchEventsList){
            if(eventItem.isPlaned()) {
                restFactualAmount += eventItem.getAmount();
            }
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

    public Integer getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
        setCorrectRestAmounts();
    }

    public Integer getRestAvailableAmount() {
        return restAvailableAmount;
    }

    public Integer getRestFactualAmount() {
        return restFactualAmount;
    }

    public Integer getBgColor() {
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

    public Boolean isConsumableMaterial() {
        return consumableMaterial;
    }

    public void setConsumableMaterial(boolean consumableMaterial) {
        this.consumableMaterial = consumableMaterial;
    }
}
