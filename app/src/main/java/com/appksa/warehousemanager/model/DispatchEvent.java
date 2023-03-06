package com.appksa.warehousemanager.model;

public class DispatchEvent {

    private int amount;
    private String contractor;

    public DispatchEvent(int amount, String contractor) {
        this.amount = amount;
        this.contractor = contractor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }
}
