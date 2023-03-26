package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.appksa.warehousemanager.adapter.SupplyListAdapter;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.Comparator;

public class SupplyListActivity extends AppCompatActivity {
    public static boolean isChanged;
    RecyclerView supplyListRecycler;
    private Comparator<SupplyItem> titleComparator;
    private int titleComparatorIterator = 1;
    private Comparator<SupplyItem> dateComparator;
    private int dateComparatorIterator = 0;
    private Comparator<SupplyItem> startAmountComparator;
    private int startAmountComparatorIterator = 0;
    private Comparator<SupplyItem> restAvailableAmountComparator;
    private int restAvailableAmountComparatorIterator = 0;
    private Comparator<SupplyItem> currentComparator;
    ImageView imgSortTitle;
    ImageView imgSortDate;
    ImageView imgSortStartAmount;
    ImageView imgSortRestAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_list);

//        System.out.println("\t\t\t\t\tSupplyListActivity Created");

        isChanged = false;

        supplyListRecycler = findViewById(R.id.supply_list_recycler);

        titleComparator = Comparator.comparing(SupplyItem::getTitle);
        startAmountComparator = Comparator.comparing(SupplyItem::getStartAmount);
        restAvailableAmountComparator = Comparator.comparing(SupplyItem::getRestAvailableAmount);
        dateComparator = Comparator.comparing(SupplyItem::getDate);

        imgSortTitle = findViewById(R.id.sort_img_title);
        imgSortDate = findViewById(R.id.sort_img_date);
        imgSortStartAmount = findViewById(R.id.sort_img_start_amount);
        imgSortRestAmount = findViewById(R.id.sort_img_rest_amount);

        currentComparator = titleComparator.thenComparing(dateComparator.reversed());
        MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
        updateRecycler();

        imgSortTitle.setImageResource(R.drawable.triangle_selected_down);
    }
    @Override
    protected void onResume() {
        super.onResume();

        if(isChanged){
            MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
            updateRecycler();
            isChanged = false;
        }

//        System.out.println("\t\t\t\t\tSupplyListActivity Resumed");
    }
    @Override
    protected void onDestroy() {
//        System.out.println("\t\t\t\t\tSupplyListActivity Destroyed");
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
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onCreateButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.putExtra("supplyItemId", MainActivity.warehouseState.getIdGen());
        intent.putExtra("isCreation", true);
        startActivity(intent);
    }

//    public void onUpdateActivity(View view) {
//        recreate();
//    }

    public void onTitleSortClick(View view) {
        titleComparatorIterator++;
        dateComparatorIterator = 0;
        startAmountComparatorIterator = 0;
        restAvailableAmountComparatorIterator = 0;

        switch(titleComparatorIterator % 3){
            case 1:
                imgSortTitle.setImageResource(R.drawable.triangle_selected_down);
                currentComparator = titleComparator.thenComparing(dateComparator.reversed());
                break;
            case 2:
                imgSortTitle.setImageResource(R.drawable.triangle_selected_up);
                currentComparator = (titleComparator.reversed()).thenComparing(dateComparator);
                titleComparatorIterator++;
                break;
        }

        imgSortDate.setImageResource(R.drawable.triangle_empty_side);
        imgSortStartAmount.setImageResource(R.drawable.triangle_empty_side);
        imgSortRestAmount.setImageResource(R.drawable.triangle_empty_side);

        MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
        updateRecycler();
    }

    public void onStartAmountSortClick(View view) {
        titleComparatorIterator= 0;
        dateComparatorIterator = 0;
        startAmountComparatorIterator++;
        restAvailableAmountComparatorIterator = 0;

        switch(startAmountComparatorIterator % 3){
            case 1:
                imgSortStartAmount.setImageResource(R.drawable.triangle_selected_down);
                currentComparator = (startAmountComparator.reversed()).thenComparing(titleComparator).thenComparing(dateComparator.reversed());
                break;
            case 2:
                imgSortStartAmount.setImageResource(R.drawable.triangle_selected_up);
                currentComparator = startAmountComparator.thenComparing(titleComparator.reversed()).thenComparing(dateComparator);
                startAmountComparatorIterator++;
                break;
        }

        imgSortTitle.setImageResource(R.drawable.triangle_empty_side);
        imgSortDate.setImageResource(R.drawable.triangle_empty_side);
        imgSortRestAmount.setImageResource(R.drawable.triangle_empty_side);

        MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
        updateRecycler();
    }

    public void onRestAmountSortClick(View view) {
        titleComparatorIterator = 0;
        dateComparatorIterator = 0;
        startAmountComparatorIterator = 0;
        restAvailableAmountComparatorIterator++;

        switch(restAvailableAmountComparatorIterator % 3){
            case 1:
                imgSortRestAmount.setImageResource(R.drawable.triangle_selected_down);
                currentComparator = (restAvailableAmountComparator.reversed()).thenComparing(titleComparator).thenComparing(dateComparator.reversed());
                break;
            case 2:
                imgSortRestAmount.setImageResource(R.drawable.triangle_selected_up);
                currentComparator = restAvailableAmountComparator.thenComparing(titleComparator.reversed()).thenComparing(dateComparator);
                restAvailableAmountComparatorIterator++;
                break;
        }

        imgSortTitle.setImageResource(R.drawable.triangle_empty_side);
        imgSortDate.setImageResource(R.drawable.triangle_empty_side);
        imgSortStartAmount.setImageResource(R.drawable.triangle_empty_side);

        MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
        updateRecycler();
    }

    public void onDateSortClick(View view) {
        titleComparatorIterator = 0;
        dateComparatorIterator++;
        startAmountComparatorIterator = 0;
        restAvailableAmountComparatorIterator = 0;

        switch(dateComparatorIterator % 3){
            case 1:
                imgSortDate.setImageResource(R.drawable.triangle_selected_down);
                currentComparator = (dateComparator.reversed()).thenComparing(titleComparator);
                break;
            case 2:
                imgSortDate.setImageResource(R.drawable.triangle_selected_up);
                currentComparator = dateComparator.thenComparing(titleComparator.reversed());
                dateComparatorIterator++;
                break;
        }

        imgSortTitle.setImageResource(R.drawable.triangle_empty_side);
        imgSortStartAmount.setImageResource(R.drawable.triangle_empty_side);
        imgSortRestAmount.setImageResource(R.drawable.triangle_empty_side);

        MainActivity.warehouseState.getSupplyItemsList().sort(currentComparator);
        updateRecycler();
    }
}