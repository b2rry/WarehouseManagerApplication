package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.adapter.SupplyListAdapter;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.Comparator;

public class SupplyListActivity extends AppCompatActivity {
    RecyclerView supplyListRecycler;
    public Comparator<SupplyItem> titleComparator;
    public Comparator<SupplyItem> dateComparator;
    public Comparator<SupplyItem> startAmountComparator;
    public Comparator<SupplyItem> restAvailableAmountComparator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_list);

        System.out.println("\t\t\t\t\tSupplyListActivity Created");

        supplyListRecycler = findViewById(R.id.supply_list_recycler);

        titleComparator = Comparator.comparing(SupplyItem::getTitle);
        startAmountComparator = Comparator.comparing(SupplyItem::getStartAmount);
        restAvailableAmountComparator = Comparator.comparing(SupplyItem::getRestAvailableAmount);
        dateComparator = Comparator.comparing(SupplyItem::getDate);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.warehouseState.getSupplyItemsList().sort(titleComparator.thenComparing(dateComparator.reversed()));
        updateRecycler();
        System.out.println("\t\t\t\t\tSupplyListActivity Resumed");
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tSupplyListActivity Destroyed");
        super.onDestroy();
    }

    protected void updateRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        supplyListRecycler.setLayoutManager(layoutManager);

        SupplyListAdapter supplyListAdapter = new SupplyListAdapter(this, MainActivity.warehouseState.getSupplyItemsList());
        supplyListRecycler.setAdapter(supplyListAdapter);
    }

    public void onMainActivityClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onCreateButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.putExtra("supplyItemId", MainActivity.warehouseState.getIdGen());
        intent.putExtra("isCreation", true);
        startActivity(intent);
    }

    public void onUpdateActivity(View view) {
        recreate();
    }

    public void onTitleSortClick(View view) {
        MainActivity.warehouseState.getSupplyItemsList().sort(titleComparator.thenComparing(dateComparator.reversed()));
        updateRecycler();
    }

    public void onStartAmountSortClick(View view) {
        MainActivity.warehouseState.getSupplyItemsList().sort((startAmountComparator.reversed()).thenComparing(titleComparator).thenComparing(dateComparator.reversed()));
        updateRecycler();
    }

    public void onRestAmountSortClick(View view) {
        MainActivity.warehouseState.getSupplyItemsList().sort((restAvailableAmountComparator.reversed()).thenComparing(titleComparator).thenComparing(dateComparator.reversed()));
        updateRecycler();
    }

    public void onDateSortClick(View view) {
        MainActivity.warehouseState.getSupplyItemsList().sort((dateComparator.reversed()).thenComparing(titleComparator));
        updateRecycler();
    }
}