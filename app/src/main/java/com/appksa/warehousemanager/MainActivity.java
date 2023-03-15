package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appksa.warehousemanager.json.JsonHelper;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.LogBookItem;
import com.appksa.warehousemanager.model.SummaryInformation;
import com.appksa.warehousemanager.model.SupplyItem;
import com.appksa.warehousemanager.model.WarehouseState;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    public static WarehouseState warehouseState;
    public static Queue<LogBookItem> logBookSaveItemsQueue;
    public static Queue<LogBookItem> logBookChangeItemsQueue;
    SummaryInformation summaryInformation = new SummaryInformation();
    JsonHelper helper;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.appksa.warehousemanager", MODE_PRIVATE);
        helper = new JsonHelper();

        if (prefs.getBoolean("firstRun", true)) {
            generateTestPositions();
            prefs.edit().putBoolean("firstRun", false).apply();
            warehouseState.updateLogLists();
            helper.exportToJsonAndInternalSave(this, warehouseState);
        }else{
            warehouseState = helper.importFromJsonFromInternalFile(this);
            warehouseState.updateLogQueues();
        }

        System.out.println("\t\t\t\t\tMainActivity Created");
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateSummaryInformation();
        System.out.println("\t\t\t\t\tMainActivity Resumed");
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tMainActivity Destroyed");
        super.onDestroy();
    }
    public void generateTestPositions(){
        warehouseState = new WarehouseState(0L, null);

        SupplyItem supplyItem1 = new SupplyItem(warehouseState.getIdGen(),"S-101/SC 06x06 SC Керамический наполнитель", "06.03.2023", 650, R.color.app_custom_background_light_grey, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem2 = new SupplyItem(warehouseState.getIdGen(),"S-106/AC 08x20 HX+ Керамический наполнитель", "25.03.2023", 500, R.color.app_custom_background_red, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem3 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "25.03.2023", 500, R.color.app_custom_background_orange, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem4 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2024", 500, R.color.app_custom_background_yellow, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem5 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "14.03.2023", 500, R.color.app_custom_background_green, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem6 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 500, R.color.app_custom_background_blue, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem7 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 600, R.color.app_custom_background_purple, getList(),"Test comment line. Here will be comments about this supply item!", false);
        SupplyItem supplyItem8 = new SupplyItem(warehouseState.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 700, R.color.app_custom_background_grey, getList(),"Test comment line. Here will be comments about this supply item!", false);
        List<SupplyItem> supplyItemsList = new ArrayList<>();
        supplyItemsList.add(supplyItem1);
        supplyItemsList.add(supplyItem2);
        supplyItemsList.add(supplyItem3);
        supplyItemsList.add(supplyItem4);
        supplyItemsList.add(supplyItem5);
        supplyItemsList.add(supplyItem6);
        supplyItemsList.add(supplyItem7);
        supplyItemsList.add(supplyItem8);

        Queue<LogBookItem> logBookSaveItemsQueue = new LinkedList<>();
        Queue<LogBookItem> logBookChangeItemsQueue = new LinkedList<>();

        try {
            for (int i = 0; i < 50; i++) {
                logBookSaveItemsQueue.add(new LogBookItem("iterator - " + i, "testComment",0));
                logBookChangeItemsQueue.add(new LogBookItem("iterator - " + i, "testComment",0));
            }
        }catch(IllegalStateException ex){
            System.out.println("IllegalStateException catch");
        }

        warehouseState.setSupplyItemsList(supplyItemsList);
        MainActivity.logBookSaveItemsQueue = logBookSaveItemsQueue;
        MainActivity.logBookChangeItemsQueue = logBookChangeItemsQueue;
    }

    private List<DispatchEvent> getList(){
        List<DispatchEvent> dispatchEventsList = new ArrayList<>();
        dispatchEventsList.add(new DispatchEvent(25,"contractor_one", "15.02.19", warehouseState.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(50,"contractor_two", "17.08.20", warehouseState.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(75,"contractor_three", "25.06.21", warehouseState.getIdGen(),true));
        dispatchEventsList.add(new DispatchEvent(25,"contractor_four", "23.06.21", warehouseState.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(5,"contractor_five", "08.04.22", warehouseState.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(5,"contractor_six", "09.09.22", warehouseState.getIdGen(),false));
        return dispatchEventsList;
    }

    protected void updateSummaryInformation(){
        summaryInformation.updateAmounts();

        TextView valueFinalPositions = findViewById(R.id.value_final_positions);
        TextView valueFinalFactual = findViewById(R.id.value_final_factual);
        TextView valueFinalAvailable = findViewById(R.id.value_final_available);
        TextView valueConsumablePositions = findViewById(R.id.value_consumable_positions);
        TextView valueConsumableFactual = findViewById(R.id.value_consumable_factual);
        TextView valueConsumableAvailable = findViewById(R.id.value_consumable_available);

        valueFinalPositions.setText(String.valueOf(summaryInformation.getFinalProductPositionsAmount()));
        valueFinalFactual.setText(String.valueOf(summaryInformation.getFinalProductFactualRestAmount()));
        valueFinalAvailable.setText(String.valueOf(summaryInformation.getFinalProductAvailableRestAmount()));
        valueConsumablePositions.setText(String.valueOf(summaryInformation.getConsumableMaterialPositionsAmount()));
        valueConsumableFactual.setText(String.valueOf(summaryInformation.getConsumableMaterialFactualRestAmount()));
        valueConsumableAvailable.setText(String.valueOf(summaryInformation.getConsumableMaterialAvailableRestAmount()));
    }

    public static void addSaveLog(int operationCode){
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
    public static void addChangeLog(String itemTitle, int operationCode){
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

    public void onSupplyListActivityClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onLogBookActivityClick(View view) {
        Intent intent = new Intent(this, LogBookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onAboutProgramActivityClick(View view) {
        Intent intent = new Intent(this, AboutProgramActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onSaveClick(View view) {
        addSaveLog(1);
        warehouseState.updateLogLists();
        if(helper.exportToJsonAndInternalSave(this, warehouseState)){
            Toast.makeText(getApplicationContext(), "Состояние сохранено успешно", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "СОХРАНЕНИЕ НЕ УДАЛОСЬ!", Toast.LENGTH_LONG).show();
        }
    }
    public void onExportFileClick(View view) {
        addSaveLog(2);
        warehouseState.updateLogLists();
        if(helper.exportToJsonAndExternalSave(this, warehouseState)){
            helper.exportToJsonAndInternalSave(this, warehouseState);//сохранение состояния для сохранения лога (не интуитивно понятно что лог не сохранится после выхода)
            Toast.makeText(getApplicationContext(), "Файл выгружен успешно", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "ВЫГРУЗКА НЕ УДАЛОСЬ!", Toast.LENGTH_LONG).show();
        }
    }
}