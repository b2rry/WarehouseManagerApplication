package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.adapter.LogBookItemAdapter;
import com.appksa.warehousemanager.adapter.SupplyListAdapter;
import com.appksa.warehousemanager.model.LogBookItem;

import java.sql.Array;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class LogBookActivity extends AppCompatActivity {
    RecyclerView logBookSavesRecycler;
    RecyclerView logBookChangesRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        logBookSavesRecycler = findViewById(R.id.recycler_save_queue);
        logBookChangesRecycler = findViewById(R.id.recycler_change_queue);

        System.out.println("\t\t\t\t\tLogBookActivity Created");
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateRequiredRecycler(logBookSavesRecycler, MainActivity.warehouseState.getLogBookSaveItemsQueue(), true);
        updateRequiredRecycler(logBookChangesRecycler, MainActivity.warehouseState.getLogBookChangeItemsQueue(), false);
        System.out.println("\t\t\t\t\tLogBookActivity Resumed");
    }

    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tLogBookActivity Destroyed");
        super.onDestroy();
    }

    protected void updateRequiredRecycler(RecyclerView currentRecycler, Queue<LogBookItem> currentQueue, boolean isSave){
        LinkedList<LogBookItem> currentList = (LinkedList<LogBookItem>) currentQueue;
        LogBookItem[] finalArray = new LogBookItem[50];
        int iterator = 50;
        for(LogBookItem currentObj : currentList){
            finalArray[--iterator] = currentObj;
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        currentRecycler.setLayoutManager(layoutManager);

        LogBookItemAdapter logBookItemAdapter = new LogBookItemAdapter(this, finalArray, isSave);
        currentRecycler.setAdapter(logBookItemAdapter);
    }

    public void onMainActivityClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}