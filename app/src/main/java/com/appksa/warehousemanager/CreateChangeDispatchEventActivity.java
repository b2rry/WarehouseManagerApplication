package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class CreateChangeDispatchEventActivity extends AppCompatActivity {

    private SupplyItem currentSupplyItem;
    private int currSupplyItemInd;
    private DispatchEvent currDispatchEvent;
    private int currDispatchEventInd;
    private boolean isNewDispatch;
    EditText editTextContractor;
    EditText editTextDate;
    EditText editTextAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_change_dispatch_event);

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isNewDispatch = getIntent().getBooleanExtra("isNewDispatch", true);
        Long eventId = getIntent().getLongExtra("eventId", 0);

        currentSupplyItem = getSupplyItemById(id);

        if(!isNewDispatch) {
            //change
            setTitle(R.string.change_dispatch_event_message + " изменение " + eventId + " eid");
            currDispatchEvent = getDispatchEventById(eventId);
        }else{
            //create
            setTitle(R.string.create_dispatch_event_message + " создание " + eventId + " eid");
            currDispatchEvent = new DispatchEvent(0, "CONTRACTOR", "DATE", eventId);
        }

        editTextContractor = findViewById(R.id.edit_text_create_disp_event_contractor);
        editTextDate = findViewById(R.id.edit_text_create_disp_event_date);
        editTextAmount = findViewById(R.id.edit_text_create_disp_event_amount);

        if(!isNewDispatch) {
            //change
            editTextContractor.setText(currDispatchEvent.getContractor());
            editTextDate.setText(currDispatchEvent.getDispatchDate());
            editTextAmount.setText(String.valueOf(currDispatchEvent.getAmount()));
        }

    }

    protected SupplyItem getSupplyItemById(Long id){
        List<SupplyItem> currList = MainActivity.warehouseState.getSupplyItemsList();
        currSupplyItemInd = 0;
        for(SupplyItem currItem : currList){
            System.out.println("needed id = " + id + ", check item id = " + currItem.getId());
            if(currItem.getId().equals(id)){
                return currItem;
            }
            currSupplyItemInd++;
        }
        return null;
    }
    protected DispatchEvent getDispatchEventById(Long eventId){
        List<DispatchEvent> currList = currentSupplyItem.getDispatchEventsList();
        currDispatchEventInd = 0;
        for(DispatchEvent currEvent : currList){
            if(currEvent.getDispatchId().equals(eventId)){
                return currEvent;
            }
            currDispatchEventInd++;
        }
        //if here then Error
        return null;
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onApplyButtonClick(View view) {
        boolean isException = false;
        int bufAmount = 777;//невозможное значение
        try {
            bufAmount = Integer.parseInt(String.valueOf(editTextAmount.getText()));
        }catch(NumberFormatException exception) {
            isException = true;
            Toast.makeText(getApplicationContext(), R.string.incorrect_amount_format_message, Toast.LENGTH_LONG).show();
        }
        if(!isException) {

            if (!isNewDispatch) {
                //change
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setAmount(bufAmount);
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setContractor(String.valueOf(editTextContractor.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setDispatchDate(String.valueOf(editTextDate.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmount();
            } else {
                //create
                currDispatchEvent.setAmount(bufAmount);
                currDispatchEvent.setContractor(String.valueOf(editTextContractor.getText()));
                currDispatchEvent.setDispatchDate(String.valueOf(editTextDate.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().add(currDispatchEvent);
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmount();
            }

            Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP); //нету FLAG_ACTIVITY_SINGLE_TOP тк нужно пересоздать create_item_act
            intent.putExtra("supplyItemId", currentSupplyItem.getId());
            startActivity(intent);
        }
    }
}