package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appksa.warehousemanager.adapter.EditDispatchEventsAdapter;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.ArrayList;
import java.util.List;

public class CreateSupplyItemActivity extends AppCompatActivity {

    private SupplyItem currentSupplyItem;
    private int currentSupplyItemInd;
    private boolean isCreationProcess;
    EditText editTextTitle;
    EditText editTextDate;
    EditText editTextStartAmount;
    EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_supply_item);

        Long id = getIntent().getLongExtra("supplyItemId", 0);

        currentSupplyItem = getSupplyItemById(id);

        if(currentSupplyItem != null) {
            //change
            setTitle(R.string.edit_item_message + " изменение " + id + " id");
            isCreationProcess = false;
        }else{
            //create
            setTitle(R.string.create_item_message + " создание " + id + " id");
            isCreationProcess = true;
        }

        editTextTitle = findViewById(R.id.edit_text_create_disp_event_contractor);
        editTextDate = findViewById(R.id.edit_text_create_disp_event_date);
        editTextStartAmount = findViewById(R.id.edit_text_create_disp_event_amount);
        editTextComment = findViewById(R.id.edit_text_comment);

        if(currentSupplyItem != null){
            //change
            editTextTitle.setText(currentSupplyItem.getTitle());
            editTextDate.setText(currentSupplyItem.getDate());
            editTextStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
            editTextComment.setText(currentSupplyItem.getComment());
        }else{
            //create
            currentSupplyItem = new SupplyItem(id, "NAME", "DATE", 0, null, new ArrayList<DispatchEvent>(), "");
        }

        RecyclerView editDispatchEventsRecycler = findViewById(R.id.recycler_edit_dispatch_events);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        editDispatchEventsRecycler.setLayoutManager(layoutManager);

        EditDispatchEventsAdapter editDispatchEventsAdapter = new EditDispatchEventsAdapter(this, currentSupplyItem.getDispatchEventsList(), currentSupplyItem.getId());
        editDispatchEventsRecycler.setAdapter(editDispatchEventsAdapter);

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

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, SupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP); //нету FLAG_ACTIVITY_SINGLE_TOP тк нужно пересоздать item тк вероятно была добавлена/изменена отгрузка
        intent.putExtra("supplyItemId", currentSupplyItem.getId());
        startActivity(intent);
    }

    public void onApplyButtonClick(View view) {

        boolean isException = false;
        int bufStartAmount = 777;//невозможное значение
        try {
            bufStartAmount = Integer.parseInt(String.valueOf(editTextStartAmount.getText()));
        }catch(NumberFormatException exception) {
            isException = true;
            Toast.makeText(getApplicationContext(), R.string.incorrect_prihod_format_message, Toast.LENGTH_LONG).show();
        }
        if(!isException) {

            if (!isCreationProcess) {
                //change
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setStartAmount(bufStartAmount);
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setTitle(String.valueOf(editTextTitle.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setDate(String.valueOf(editTextDate.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setComment(String.valueOf(editTextComment.getText()));
            } else {
                //create
                currentSupplyItem.setStartAmount(bufStartAmount);
                currentSupplyItem.setTitle(String.valueOf(editTextTitle.getText()));
                currentSupplyItem.setDate(String.valueOf(editTextDate.getText()));
                currentSupplyItem.setComment(String.valueOf(editTextComment.getText()));
                MainActivity.warehouseState.getSupplyItemsList().add(currentSupplyItem);
            }

            Intent intent = new Intent(this, SupplyItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP); //нету FLAG_ACTIVITY_SINGLE_TOP тк нужно пересоздать измененный item
            intent.putExtra("supplyItemId", currentSupplyItem.getId());
            startActivity(intent);
        }
    }

    public void onAddDispatchEventClick(View view) {
        if(isCreationProcess) {
            Toast.makeText(getApplicationContext(), R.string.decline_dispatch_event_message, Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, CreateDispatchEventActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("supplyItemId", currentSupplyItem.getId());
            intent.putExtra("isNewDispatch", true);
            intent.putExtra("eventId", MainActivity.warehouseState.getIdGen());
            startActivity(intent);
        }
    }
}
//нужно сохранять состояние активности (уже измененные, но не сохраненные поля ввода) при возврате из активности добавления/изменения отгрузки