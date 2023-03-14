package com.appksa.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appksa.warehousemanager.dialog.AcceptDeletionDispatchEventDialogFragment;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.List;

public class CreateChangeDispatchEventActivity extends AppCompatActivity {

    private SupplyItem currentSupplyItem; // поле для редактируемого item
    private int currSupplyItemInd; // поле для индекса в листе редактируемого item
    private DispatchEvent currDispatchEvent; // поле для редактируемого event
    private int currDispatchEventInd; // поле для индекса в листе редактируемого event
    private boolean isNewDispatch; // флаг создания нового event
    EditText editTextContractor;
    EditText editTextDate;
    EditText editTextAmount;
    SwitchCompat planedDispatchSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_change_dispatch_event);

        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Created");

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isNewDispatch = getIntent().getBooleanExtra("isNewDispatch", true);
        Long eventId = getIntent().getLongExtra("eventId", 0);  //забыл для чего

        currentSupplyItem = getSupplyItemById(id);

        if(isNewDispatch) {
            //create
            setTitle(R.string.create_dispatch_event_message + " создание " + eventId + " eid");
            currDispatchEvent = new DispatchEvent(0, "CONTRACTOR", "DATE", eventId, false);
        }else{
            //change
            setTitle(R.string.change_dispatch_event_message + " изменение " + eventId + " eid");
            currDispatchEvent = getDispatchEventById(eventId);
        }

        editTextContractor = findViewById(R.id.edit_text_create_disp_event_contractor);
        editTextDate = findViewById(R.id.edit_text_create_disp_event_date);
        editTextAmount = findViewById(R.id.edit_text_create_disp_event_amount);
        TextView textUnitsFourth = findViewById(R.id.text_view_units_fourth);
        planedDispatchSwitch = findViewById(R.id.planed_dispatch_event_switch);

        if(!isNewDispatch) {
            //change
            editTextContractor.setText(currDispatchEvent.getContractor());
            editTextDate.setText(currDispatchEvent.getDispatchDate());
            editTextAmount.setText(String.valueOf(currDispatchEvent.getAmount()));
            planedDispatchSwitch.setChecked(currDispatchEvent.isPlaned());
        }
        textUnitsFourth.setText(currentSupplyItem.isConsumableMaterial() ? R.string.kg_units_field : R.string.pieces_units_field);
    }

    @Override
    protected void onResume() {
        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Resumed");
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Destroyed");
        super.onDestroy();
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

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) { // переопределенный метод для скрытия клавитауры при нажатии не на клавиатуре
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

            if (isNewDispatch) {
                //create
                currDispatchEvent.setAmount(bufAmount);
                currDispatchEvent.setContractor(String.valueOf(editTextContractor.getText()));
                currDispatchEvent.setDispatchDate(String.valueOf(editTextDate.getText()));
                currDispatchEvent.setPlaned(planedDispatchSwitch.isChecked());
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().add(currDispatchEvent);
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
                MainActivity.warehouseState.addChangeLog(currentSupplyItem.getTitle(),6);
            } else {
                //change
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setAmount(bufAmount);
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setContractor(String.valueOf(editTextContractor.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setDispatchDate(String.valueOf(editTextDate.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setPlaned(planedDispatchSwitch.isChecked());
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
                MainActivity.warehouseState.addChangeLog(currentSupplyItem.getTitle(),7);
            }

            Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //intent.putExtra("isChanged", true);
            //intent.putExtra("supplyItemId", currentSupplyItem.getId());
            CreateChangeSupplyItemActivity.isChanged = true;
            startActivity(intent);
        }
    }
    public void onDeleteButtonClick(View view) {
        AcceptDeletionDispatchEventDialogFragment myDialogFragment = new AcceptDeletionDispatchEventDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        myDialogFragment.show(fragmentManager, "deleteConfirm");
    }
    public void acceptDeletionDialogClicked() {
        MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().remove(currDispatchEventInd);
        MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
        MainActivity.warehouseState.addChangeLog(currentSupplyItem.getTitle(),8);
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        CreateChangeSupplyItemActivity.isChanged = true;
        startActivity(intent);
    }
}