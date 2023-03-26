package com.appksa.warehousemanager.model;

public class LogBookItem {

    private String infoField;
    private String commentField;
    private int operationCode;

    public LogBookItem(String infoField, String commentField, int operationCode) {
        this.infoField = infoField;
        this.commentField = commentField;
        this.operationCode = operationCode;
    }

    public String getInfoField() {
        return infoField;
    }

    public void setInfoField(String createTime) {
        this.infoField = infoField;
    }

    public String getCommentField() {
        return commentField;
    }

    public void setCommentField(String logComment) {
        this.commentField = commentField;
    }

    public int getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(int operationCode) {
        this.operationCode = operationCode;
    }
}
/*
    Коды операций:
    0 - код автогенерируемого объекта
    1 - код сохранения json файла объекта в internal хранилище
    2 - код сохранения json файла объекта в external хранилище
    3 - код добавления позиции supplyItem
    4 - код изменения позиции supplyItem
    5 - код удаления позиции supplyItem
    6 - код добавления позиции dispatchEvent
    7 - код изменения позиции dispatchEvent
    8 - код удаления позиции dispatchEvent
    9 - код сохранения exel файла в external хранилище
    10 - код загрузки exel файла из external хранилища
    400 - код получения некорректоного кода операции
*/