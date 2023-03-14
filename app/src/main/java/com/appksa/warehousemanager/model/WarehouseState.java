package com.appksa.warehousemanager.model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WarehouseState {

    private Long idGen;
    private Queue<LogBookItem> logBookSaveItemsQueue;
    private Queue<LogBookItem> logBookChangeItemsQueue;
    private ArrayList<LogBookItem> logBookSaveArrayForJson;
    private ArrayList<LogBookItem> logBookChangeArrayForJson;
    private List<SupplyItem> supplyItemsList;

    public WarehouseState(Long idGen, List<SupplyItem> supplyItemsList) {
        this.idGen = idGen;
        this.supplyItemsList = supplyItemsList;
    }

    public Long getIdGen() {
        System.out.println("set id " + (idGen+1));
        return ++idGen;
    }

    public void changeState(){
        if(logBookSaveItemsQueue != null){
            logBookSaveArrayForJson = new ArrayList<>();
            logBookChangeArrayForJson = new ArrayList<>();
            for(int i = 0; i < 50; i++){
                logBookSaveArrayForJson.add(logBookSaveItemsQueue.poll());
                logBookChangeArrayForJson.add(logBookChangeItemsQueue.poll());
            }
            logBookSaveItemsQueue = null;
            logBookChangeItemsQueue = null;
        }else{
            logBookSaveItemsQueue = new LinkedList<>();
            logBookChangeItemsQueue = new LinkedList<>();
            for(int i = 0; i < 50; i++){
                logBookSaveItemsQueue.add(logBookSaveArrayForJson.get(i));
                logBookChangeItemsQueue.add(logBookChangeArrayForJson.get(i));
            }
            logBookSaveArrayForJson = null;
            logBookChangeArrayForJson = null;
        }
    }

    public void setIdGen(Long idGen) {
        this.idGen = idGen;
    }

    public List<SupplyItem> getSupplyItemsList() {
        return supplyItemsList;
    }

    public void setSupplyItemsList(List<SupplyItem> supplyItemsList) {
        this.supplyItemsList = supplyItemsList;
    }

    public Queue<LogBookItem> getLogBookSaveItemsQueue() {
        return logBookSaveItemsQueue;
    }


    public void setLogBookSaveItemsQueue(Queue<LogBookItem> logBookSaveItemsQueue) {
        this.logBookSaveItemsQueue = logBookSaveItemsQueue;
    }

    public Queue<LogBookItem> getLogBookChangeItemsQueue() {
        return logBookChangeItemsQueue;
    }

    public void setLogBookChangeItemsQueue(Queue<LogBookItem> logBookChangeItemsQueue) {
        this.logBookChangeItemsQueue = logBookChangeItemsQueue;
    }
    public void addSaveLog(int operationCode){
        Date date = new Date();
        @SuppressLint("DefaultLocale") String currentMoment = String.format("%td.%<tm.%<tY / %<tR", date);
        if(operationCode == 1){
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Сохранено состояние склада", operationCode));
        } else if (operationCode == 2) {
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Выгружен файл состояния", operationCode));
        }else{
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "received incorrect code", 400));
        }
    }
    public void addChangeLog(String itemTitle, int operationCode){
        String action = "";
        switch(operationCode){
            case 3:
                action = "Добавлена позиция";
                break;
            case 4:
                action = "Изменена позиция";
                break;
            case 5:
                action = "Удалена позиция";
                break;
            case 6:
                action = "Добавлена отгрузка";
                break;
            case 7:
                action = "Изменена отгрузка";
                break;
            case 8:
                action = "Удалена отгрузка";
                break;
            default:
                operationCode = 400;
                action = "received incorrect code";
                break;
        }
        logBookChangeItemsQueue.poll();
        logBookChangeItemsQueue.add(new LogBookItem(action, itemTitle, operationCode));
    }
}
