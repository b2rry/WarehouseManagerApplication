package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.model.BufferSupplyItem;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.LogBookItem;
import com.appksa.warehousemanager.model.SupplyItem;
import com.appksa.warehousemanager.model.WarehouseState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static WarehouseState warehouseState;
    WarehouseState tempClassForId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("\t\t\t\t\tMainActivity Created");

        generateTestPositions();
    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("\t\t\t\t\tMainActivity Resumed");
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tMainActivity Destroyed");
        super.onDestroy();
    }
    public void generateTestPositions(){
        tempClassForId = new WarehouseState(-1L, null, null);
        //bufferItem = new BufferSupplyItem(tempClassForId.getIdGen(),"BUFFER MAIN INIT!!!","","",0,null,"");

        SupplyItem supplyItem1 = new SupplyItem(tempClassForId.getIdGen(),"S-101/SC 06x06 SC Керамический наполнитель", "06.03.2023", 650, R.color.app_custom_background_light_grey, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem2 = new SupplyItem(tempClassForId.getIdGen(),"S-106/AC 08x20 HX+ Керамический наполнитель", "25.03.2023", 500, R.color.app_custom_background_red, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem3 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "25.03.2023", 500, R.color.app_custom_background_orange, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem4 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2024", 500, R.color.app_custom_background_yellow, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem5 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "14.03.2023", 500, R.color.app_custom_background_green, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem6 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 500, R.color.app_custom_background_blue, getList(),"Test comment line. Here will be comments about this supply item!", true);
        SupplyItem supplyItem7 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 600, R.color.app_custom_background_purple, getList(),"Test comment line. Here will be comments about this supply item!", false);
        SupplyItem supplyItem8 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "28.03.2023", 700, R.color.app_custom_background_grey, getList(),"Test comment line. Here will be comments about this supply item!", false);
        List<SupplyItem> supplyItemsList = new ArrayList<>();
        supplyItemsList.add(supplyItem1);
        supplyItemsList.add(supplyItem2);
        supplyItemsList.add(supplyItem3);
        supplyItemsList.add(supplyItem4);
        supplyItemsList.add(supplyItem5);
        supplyItemsList.add(supplyItem6);
        supplyItemsList.add(supplyItem7);
        supplyItemsList.add(supplyItem8);

        LogBookItem logBookItem1 = new LogBookItem("06.03.2023 / 15:00 MSK","Add all test items.");
        LogBookItem logBookItem2 = new LogBookItem("06.03.2023 / 16:30 MSK","Save some more information.");
        List<LogBookItem> logBookItemsList = new ArrayList<>();
        logBookItemsList.add(logBookItem1);
        logBookItemsList.add(logBookItem2);

        warehouseState = new WarehouseState((tempClassForId.getIdGen()-1), supplyItemsList, logBookItemsList);
    }

    private List<DispatchEvent> getList(){
        List<DispatchEvent> dispatchEventsList = new ArrayList<>();
        dispatchEventsList.add(new DispatchEvent(25,"contractor_one", "15.02.19", tempClassForId.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(50,"contractor_two", "17.08.20", tempClassForId.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(75,"contractor_three", "25.06.21", tempClassForId.getIdGen(),true));
        dispatchEventsList.add(new DispatchEvent(25,"contractor_four", "23.06.21", tempClassForId.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(5,"contractor_five", "08.04.22", tempClassForId.getIdGen(),false));
        dispatchEventsList.add(new DispatchEvent(5,"contractor_six", "09.09.22", tempClassForId.getIdGen(),false));
        return dispatchEventsList;
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
}