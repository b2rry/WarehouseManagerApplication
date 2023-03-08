package com.appksa.warehousemanager.model;

public class DispatchEvent {

    private int amount;
    private String contractor;
    private String dispatchDate;
    private Long dispatchId;

    public DispatchEvent(int amount, String contractor, String dispatchDate, Long dispatchId) {
        this.amount = amount;
        this.contractor = contractor;
        this.dispatchDate = dispatchDate;
        this.dispatchId = dispatchId;
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

    public String getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(String dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }
}
