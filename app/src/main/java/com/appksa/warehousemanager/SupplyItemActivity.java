package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
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

        currentSupplyItem = getSupplyItemById(id);
        if(currentSupplyItem == null){
            //if here then Error
            setTitle(R.string.error_message);
        }else {
            setTitle(R.string.show_item_message + " позиция с id " + id);

            TextView textTitle = findViewById(R.id.textView_title);
            TextView textDate = findViewById(R.id.textView_date);
            TextView textStartAmount = findViewById(R.id.textView_start_amount);
            TextView textRestAvailableAmount = findViewById(R.id.textView_rest_available_amount);
            TextView textRestFactualAmount = findViewById(R.id.textView_rest_factual_amount);
            TextView textComment = findViewById(R.id.textView_comment);
            CardView cardBackgroundColor = findViewById(R.id.card_view_background_color);
            TextView textIsConsumable = findViewById(R.id.text_view_is_consumable_material);
            TextView textUnits1 = findViewById(R.id.text_view_units_first);
            TextView textUnits2 = findViewById(R.id.text_view_units_second);
            TextView textUnits3 = findViewById(R.id.text_view_units_fifth); //еще две в др активностях

            textTitle.setText(currentSupplyItem.getTitle());
            textDate.setText(currentSupplyItem.getDate());
            textStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
            textRestAvailableAmount.setText(String.valueOf(currentSupplyItem.getRestAvailableAmount()));
            textRestFactualAmount.setText(String.valueOf(currentSupplyItem.getRestFactualAmount()));
            textComment.setText(currentSupplyItem.getComment());
            cardBackgroundColor.setCardBackgroundColor(ContextCompat.getColor(this, currentSupplyItem.getBgColor()));
            textIsConsumable.setText(currentSupplyItem.isConsumableMaterial() ? R.string.yes_message : R.string.no_message);
            RecyclerView dispatchEventRecycler = findViewById(R.id.recycler_dispatch_events);
            textUnits1.setText(currentSupplyItem.isConsumableMaterial() ? R.string.kg_units_field : R.string.pieces_units_field);
            textUnits2.setText(currentSupplyItem.isConsumableMaterial() ? R.string.kg_units_field : R.string.pieces_units_field);
            textUnits3.setText(currentSupplyItem.isConsumableMaterial() ? R.string.kg_units_field : R.string.pieces_units_field);

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
            SupplyListActivity.isChanged = true;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }else{
            SupplyListActivity.isChanged = false;
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
        SupplyListActivity.isChanged = true;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}