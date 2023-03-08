package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.LogBookItem;
import com.appksa.warehousemanager.model.SupplyItem;
import com.appksa.warehousemanager.model.WarehouseState;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static WarehouseState warehouseState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateTestPositions();



    }

    public void generateTestPositions(){
        WarehouseState tempClassForId = new WarehouseState(10L, null, null);

        DispatchEvent dispatchEvent1 = new DispatchEvent(25,"contractor_one", "15.02.19", tempClassForId.getIdGen());
        DispatchEvent dispatchEvent2 = new DispatchEvent(50,"contractor_two", "17.08.20", tempClassForId.getIdGen());
        DispatchEvent dispatchEvent3 = new DispatchEvent(75,"contractor_three", "25.06.21", tempClassForId.getIdGen());
        DispatchEvent dispatchEvent4 = new DispatchEvent(25,"contractor_four", "23.06.21", tempClassForId.getIdGen());
        DispatchEvent dispatchEvent5 = new DispatchEvent(5,"contractor_five", "08.04.22", tempClassForId.getIdGen());
        DispatchEvent dispatchEvent6 = new DispatchEvent(5,"contractor_six", "09.09.22", tempClassForId.getIdGen());
        List<DispatchEvent> dispatchEventsList = new ArrayList<>();
        dispatchEventsList.add(dispatchEvent1);
        dispatchEventsList.add(dispatchEvent2);
        dispatchEventsList.add(dispatchEvent3);
        dispatchEventsList.add(dispatchEvent4);
        dispatchEventsList.add(dispatchEvent5);
        dispatchEventsList.add(dispatchEvent6);

        SupplyItem supplyItem1 = new SupplyItem(tempClassForId.getIdGen(),"S-101/SC 06x06 SC Керамический наполнитель", "06.03.2023", 300, "green", dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem2 = new SupplyItem(tempClassForId.getIdGen(),"S-106/AC 08x20 HX+ Керамический наполнитель", "06.03.2023", 400, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem3 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 300, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem4 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 200, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem5 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 150, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem6 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 100, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem7 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 150, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
        SupplyItem supplyItem8 = new SupplyItem(tempClassForId.getIdGen(),"MG 20 Кукурузный гранулят (0.7 - 1.5мм)", "06.03.2023", 200, null, dispatchEventsList,"Test comment line. Here will be comments about this supply item!");
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

        warehouseState = new WarehouseState(24L, supplyItemsList, logBookItemsList);
    }


    public void onSupplyListActivityClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onLogBookActivityClick(View view) {
        Intent intent = new Intent(this, LogBookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onAboutProgramActivityClick(View view) {
        Intent intent = new Intent(this, AboutProgramActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
//change intent flags, add activities and layouts, add recycler adapters