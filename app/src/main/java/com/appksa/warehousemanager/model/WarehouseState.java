package com.appksa.warehousemanager.model;

import com.appksa.warehousemanager.MainActivity;

import java.util.LinkedList;
import java.util.List;

public class WarehouseState {

    private Long idGen;
    private LinkedList<LogBookItem> logBookSaveArrayForJson;
    private LinkedList<LogBookItem> logBookChangeArrayForJson;
    private List<SupplyItem> supplyItemsList;

    public WarehouseState(Long idGen, List<SupplyItem> supplyItemsList) {
        this.idGen = idGen;
        this.supplyItemsList = supplyItemsList;
    }

    public Long getIdGen() {
//        System.out.println("set id " + (idGen+1));
        return ++idGen;
    }

    public void updateLogLists(){
            logBookSaveArrayForJson = new LinkedList<>();
            logBookChangeArrayForJson = new LinkedList<>();
            for (int i = 0; i < 50; i++) {
                logBookSaveArrayForJson = (LinkedList<LogBookItem>) MainActivity.logBookSaveItemsQueue;
                logBookChangeArrayForJson = (LinkedList<LogBookItem>) MainActivity.logBookChangeItemsQueue;
            }
    }

    public void updateLogQueues(){
        MainActivity.logBookSaveItemsQueue = new LinkedList<LogBookItem>();
        MainActivity.logBookChangeItemsQueue = new LinkedList<LogBookItem>();
            for(int i = 0; i < 50; i++){
                MainActivity.logBookSaveItemsQueue.add(logBookSaveArrayForJson.get(i));
                MainActivity.logBookChangeItemsQueue.add(logBookChangeArrayForJson.get(i));
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
}
