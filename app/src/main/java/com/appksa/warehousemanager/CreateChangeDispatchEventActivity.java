package com.appksa.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appksa.warehousemanager.dialog.AcceptDeletionDispatchEventDialogFragment;
import com.appksa.warehousemanager.exceptions.ValidationException;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateChangeDispatchEventActivity extends AppCompatActivity implements AcceptDeletionDispatchEventDialogFragment.DeletionDispatchEventDialogListener {
//в перспективе менять на фрагмент или диалог
    private SupplyItem currentSupplyItem; // поле для редактируемого item
    private int currSupplyItemInd; // поле для индекса в листе редактируемого item
    private DispatchEvent currDispatchEvent; // поле для редактируемого event
    private int currDispatchEventInd; // поле для индекса в листе редактируемого event
    private boolean isNewDispatch; // флаг создания нового event
    EditText editTextContractor;
    TextView editTextDate;
    EditText editTextAmount;
    SwitchCompat planedDispatchSwitch;
    Calendar setDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_change_dispatch_event);

//        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Created");

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isNewDispatch = getIntent().getBooleanExtra("isNewDispatch", true);
        Long eventId = getIntent().getLongExtra("eventId", 0);  //забыл для чего

        currentSupplyItem = getSupplyItemById(id);

        if(isNewDispatch) {
            //create
            setTitle(R.string.create_dispatch_event_message);
            currDispatchEvent = new DispatchEvent(0, "", "", eventId, false);
        }else{
            //change
            setTitle(R.string.change_dispatch_event_message);
            currDispatchEvent = getDispatchEventById(eventId);
        }

        editTextContractor = findViewById(R.id.edit_text_create_disp_event_contractor);
        editTextDate = findViewById(R.id.edit_text_create_disp_event_date);
        editTextAmount = findViewById(R.id.edit_text_create_disp_event_amount);
        TextView textUnitsFourth = findViewById(R.id.text_view_units_fourth);
        planedDispatchSwitch = findViewById(R.id.planed_dispatch_event_switch);

        if(isNewDispatch) {
            setInitialDateTime(new Date());
        }else{
            editTextContractor.setText(currDispatchEvent.getContractor());
            editTextDate.setText(currDispatchEvent.getDispatchDate());
            editTextAmount.setText(String.valueOf(currDispatchEvent.getAmount()));
            planedDispatchSwitch.setChecked(currDispatchEvent.isPlaned());
        }
        textUnitsFourth.setText(currentSupplyItem.isConsumableMaterial() ? R.string.kg_units_field : R.string.pieces_units_field);
    }

    @Override
    protected void onResume() {
//        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Resumed");
        super.onResume();
    }
    @Override
    protected void onDestroy() {
//        System.out.println("\t\t\t\t\tCreateChangeDispatchEventActivity Destroyed");
        super.onDestroy();
    }

    protected SupplyItem getSupplyItemById(Long id){
        List<SupplyItem> currList = MainActivity.warehouseState.getSupplyItemsList();
        currSupplyItemInd = 0;
        for(SupplyItem currItem : currList){
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

    private void setInitialDateTime(Date date) {
        String setDateString = String.format("%tY.%<tm.%<td", date);
        editTextDate.setText(setDateString);
    }

    public void onSetDateTextClick(View v) {
        new DatePickerDialog(CreateChangeDispatchEventActivity.this, d,
                setDate.get(Calendar.YEAR),
                setDate.get(Calendar.MONTH),
                setDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDate.set(Calendar.YEAR, year);
            setDate.set(Calendar.MONTH, monthOfYear);
            setDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime(new Date(setDate.getTimeInMillis()));
        }
    };

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onApplyButtonClick(View view) {
        try {
            if (isNewDispatch) {
                if(String.valueOf(editTextContractor.getText()).length() >= 4) {
                    currDispatchEvent.setContractor(String.valueOf(editTextContractor.getText()));
                }else{ throw new ValidationException(5, "incorrect contractor field data"); }

                if(String.valueOf(editTextDate.getText()).length() >= 8) {
                    currDispatchEvent.setDispatchDate(String.valueOf(editTextDate.getText()));
                }else{ throw new ValidationException(6, "incorrect dispatchDate field data"); }

                try {
                    currDispatchEvent.setAmount(Integer.parseInt(String.valueOf(editTextAmount.getText())));
                } catch (NumberFormatException exception) {
                    throw new ValidationException(7, "incorrect amount field data");
                }

                currDispatchEvent.setPlaned(planedDispatchSwitch.isChecked());
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().add(currDispatchEvent);
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
                MainActivity.addChangeLog(currentSupplyItem.getTitle(), 6);
            } else {
                if(String.valueOf(editTextContractor.getText()).length() >= 4) {
                    MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setContractor(String.valueOf(editTextContractor.getText()));
                }else{ throw new ValidationException(5, "incorrect title field data"); }

                if(String.valueOf(editTextDate.getText()).length() >= 8) {
                    MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setDispatchDate(String.valueOf(editTextDate.getText()));
                }else{ throw new ValidationException(6, "incorrect dispatchDate field data"); }

                try {
                    MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setAmount(Integer.parseInt(String.valueOf(editTextAmount.getText())));
                } catch (NumberFormatException exception) {
                    throw new ValidationException(7, "incorrect amount field data");
                }

                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().get(currDispatchEventInd).setPlaned(planedDispatchSwitch.isChecked());
                MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
                MainActivity.addChangeLog(currentSupplyItem.getTitle(), 7);
            }

            Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            CreateChangeSupplyItemActivity.isChanged = true;
            startActivity(intent);

        }catch(ValidationException exception){
            switch(exception.getErrorCode()){//позже буду объединять активности с соответствующими валидациями
                case 0:
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_title_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_item_date_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_start_amount_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_comment_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_contractor_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_dispatch_date_format_message, Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    Toast.makeText(getApplicationContext(), R.string.incorrect_amount_format_message, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    public void onDeleteButtonClick(View view) {
        AcceptDeletionDispatchEventDialogFragment myDialogFragment = new AcceptDeletionDispatchEventDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        myDialogFragment.show(fragmentManager, "deleteConfirm");
    }

    @Override
    public void onDialogAcceptDeletionClick(DialogFragment dialog) {
        MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).getDispatchEventsList().remove(currDispatchEventInd);
        MainActivity.warehouseState.getSupplyItemsList().get(currSupplyItemInd).setCorrectRestAmounts();
        MainActivity.addChangeLog(currentSupplyItem.getTitle(),8);
        Intent intent = new Intent(this, CreateChangeSupplyItemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        CreateChangeSupplyItemActivity.isChanged = true;
        startActivity(intent);
    }
}