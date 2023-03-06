package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class SupplyItemActivity extends AppCompatActivity {

    SupplyItem currentSupplyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_item);

        Long id = getIntent().getLongExtra("supplyItemId", 0);

        currentSupplyItem = getSupplyItemById(id);
        setTitle(currentSupplyItem.getTitle());

    }

    protected SupplyItem getSupplyItemById(Long id){
        List<SupplyItem> currList = MainActivity.warehouseState.getSupplyItemsList();
        for(SupplyItem currItem : currList){
            if(currItem.getId().equals(id)){
                return currItem;
            }
        }
        return null;
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onChangeButtonClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);//нужно придумать как именно пересоздавать supply_item активность(из supply_list и при возврате из активности для редактирования(наверно стоит узнавать id в onResume а также передавать id в intent при переходе из редакт. активности))
        startActivity(intent);
    }
}