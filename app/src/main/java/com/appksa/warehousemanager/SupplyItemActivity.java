package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appksa.warehousemanager.adapter.DispatchEventAdapter;
import com.appksa.warehousemanager.dialog.AcceptDeletionSupplyItemDialogFragment;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class SupplyItemActivity extends AppCompatActivity {

    SupplyItem currentSupplyItem;
    private int currentSupplyItemInd;
    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_item);

        System.out.println("\t\t\t\t\tSupplyItemActivity Created");

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isChanged = getIntent().getBooleanExtra("isChanged", false);
        System.out.println("Флаг isChanged - " + isChanged);

        MainActivity.bufferItem = null;
        currentSupplyItem = getSupplyItemById(id);
        if(currentSupplyItem == null){
            //if here then Error
            setTitle(R.string.error_message);
        }else {
            setTitle(R.string.show_item_message + " позиция с id " + id);

            TextView textTitle = findViewById(R.id.textView_title);
            TextView textDate = findViewById(R.id.textView_date);
            TextView textStartAmount = findViewById(R.id.textView_start_amount);
            TextView textRestAmount = findViewById(R.id.textView_rest_amount);
            TextView textComment = findViewById(R.id.textView_comment);

            textTitle.setText(currentSupplyItem.getTitle());
            textDate.setText(currentSupplyItem.getDate());
            textStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
            textRestAmount.setText(String.valueOf(currentSupplyItem.getRestAmount()));
            textComment.setText(currentSupplyItem.getComment());

            RecyclerView dispatchEventRecycler = findViewById(R.id.recycler_dispatch_events);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            dispatchEventRecycler.setLayoutManager(layoutManager);

            DispatchEventAdapter dispatchEventAdapter = new DispatchEventAdapter(this, currentSupplyItem.getDispatchEventsList());
            dispatchEventRecycler.setAdapter(dispatchEventAdapter);
        }
    }

    @Override
    protected void onResume() {
        System.out.println("\t\t\t\t\tSupplyItemActivity Resumed");
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tSupplyItemActivity Destroyed");
        super.onDestroy();
    }

    protected SupplyItem getSupplyItemById(Long id){
        List<SupplyItem> currList = MainActivity.warehouseState.getSupplyItemsList();
        currentSupplyItemInd = 0;
        for(SupplyItem currItem : currList){
            if(currItem.getId().equals(id)){
                return currItem;
            }
            currentSupplyItemInd++;
        }
        return null;
    }

    public void onAllPositionsButtonClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        if(isChanged) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else{
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        startActivity(intent);
    }

    public void onChangeButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.putExtra("supplyItemId", currentSupplyItem.getId());
        startActivity(intent);
    }

    public void onDeleteButtonClick(View view) {
        AcceptDeletionSupplyItemDialogFragment myDialogFragment = new AcceptDeletionSupplyItemDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        myDialogFragment.show(fragmentManager, "deleteConfirm");
    }

    public void acceptDeletionDialogClicked() {
        MainActivity.warehouseState.getSupplyItemsList().remove(currentSupplyItemInd);
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}