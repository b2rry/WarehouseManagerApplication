package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.adapter.SupplyListAdapter;

public class SupplyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_list);

        RecyclerView supplyListRecycler = findViewById(R.id.supply_list_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        supplyListRecycler.setLayoutManager(layoutManager);

        SupplyListAdapter supplyListAdapter = new SupplyListAdapter(this, MainActivity.warehouseState.getSupplyItemsList());
        supplyListRecycler.setAdapter(supplyListAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onMainActivityClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onCreateButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("supplyItemId", MainActivity.warehouseState.getIdGen());
        startActivity(intent);
    }
}