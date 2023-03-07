package com.appksa.warehousemanager.model;

import java.util.ArrayList;
import java.util.List;

public class WarehouseState {

    private Long idGen;
    private List<SupplyItem> supplyItemsList;
    private List<LogBookItem> logBookItemsList;

    public WarehouseState(Long idGen, List<SupplyItem> supplyItemsList, List<LogBookItem> logBookItemsList) {
        this.idGen = idGen;
        this.supplyItemsList = supplyItemsList;
        this.logBookItemsList = logBookItemsList;
    }

    public Long getIdGen() {
        System.out.println("set id " + (idGen+1));
        return ++idGen;
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

    public List<LogBookItem> getLogBookItemsList() {
        return logBookItemsList;
    }

    public void setLogBookItemsList(List<LogBookItem> logBookItemsList) {
        this.logBookItemsList = logBookItemsList;
    }
}
