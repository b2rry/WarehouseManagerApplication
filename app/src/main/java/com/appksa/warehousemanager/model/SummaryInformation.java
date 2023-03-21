package com.appksa.warehousemanager.model;

import com.appksa.warehousemanager.MainActivity;

import java.util.List;

public class SummaryInformation { // оборудование(пред. назв. конечная продукция ) / расходный материал
    private int finalProductPositionsAmount;
    private int finalProductAvailableRestAmount;
    private int finalProductFactualRestAmount;
    private int consumableMaterialPositionsAmount;
    private int consumableMaterialAvailableRestAmount;
    private int consumableMaterialFactualRestAmount;

    public void updateAmounts(){

        finalProductPositionsAmount = 0;
        finalProductAvailableRestAmount = 0;
        finalProductFactualRestAmount = 0;
        consumableMaterialPositionsAmount = 0;
        consumableMaterialAvailableRestAmount = 0;
        consumableMaterialFactualRestAmount = 0;

        List<SupplyItem> actualSupplyItemsList = MainActivity.warehouseState.getSupplyItemsList();

        for(SupplyItem currentSupplyItem : actualSupplyItemsList){

            if(currentSupplyItem.isConsumableMaterial()){
                consumableMaterialPositionsAmount++;
                consumableMaterialAvailableRestAmount += currentSupplyItem.getRestAvailableAmount();
                consumableMaterialFactualRestAmount += currentSupplyItem.getRestFactualAmount();
            }else{
                finalProductPositionsAmount++;
                finalProductAvailableRestAmount += currentSupplyItem.getRestAvailableAmount();
                finalProductFactualRestAmount += currentSupplyItem.getRestFactualAmount();
            }
        }
    }

    public int getFinalProductPositionsAmount() {
        return finalProductPositionsAmount;
    }

    public int getFinalProductAvailableRestAmount() {
        return finalProductAvailableRestAmount;
    }

    public int getFinalProductFactualRestAmount() {
        return finalProductFactualRestAmount;
    }

    public int getConsumableMaterialPositionsAmount() {
        return consumableMaterialPositionsAmount;
    }

    public int getConsumableMaterialAvailableRestAmount() {
        return consumableMaterialAvailableRestAmount;
    }

    public int getConsumableMaterialFactualRestAmount() {
        return consumableMaterialFactualRestAmount;
    }
}
