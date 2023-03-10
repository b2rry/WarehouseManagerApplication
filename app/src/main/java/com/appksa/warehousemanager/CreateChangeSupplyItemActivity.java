package com.appksa.warehousemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appksa.warehousemanager.adapter.EditDispatchEventsAdapter;
import com.appksa.warehousemanager.model.BufferSupplyItem;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;

import java.util.ArrayList;
import java.util.List;

public class CreateChangeSupplyItemActivity extends AppCompatActivity {

    private SupplyItem currentSupplyItem; // поле текущей позиции склада
    private int currentSupplyItemInd; // поле индекса в коллекции текущей позиции склада
    BufferSupplyItem bufferItem;
    private boolean isChanged; // поле для хранения флага состояния позиции
    private boolean isCreation; // поле для хранения состояния действия пользователя, создания или изменения позиции
    EditText editTextTitle;
    EditText editTextDate;
    EditText editTextStartAmount;
    EditText editTextComment;
    RadioGroup changeColorRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_change_supply_item);

        System.out.println("\t\t\t\t\tCreateChangeSupplyItemActivity Created");

        Long id = getIntent().getLongExtra("supplyItemId", 0);
        isChanged  = getIntent().getBooleanExtra("isChanged", false);
        isCreation = getIntent().getBooleanExtra("isCreation", false);
        System.out.println("Флаг isChanged - " + isChanged);
        System.out.println("Флаг isCreation - " + isCreation);

        if(isCreation) {
            setTitle(R.string.create_item_message + " создание " + id + " id");
        }else{
            setTitle(R.string.edit_item_message + " изменение " + id + " id");
            currentSupplyItem = getSupplyItemById(id);
        }

        editTextTitle = findViewById(R.id.edit_text_create_disp_event_contractor);
        editTextDate = findViewById(R.id.edit_text_create_disp_event_date);
        editTextStartAmount = findViewById(R.id.edit_text_create_disp_event_amount);
        editTextComment = findViewById(R.id.edit_text_comment);
        changeColorRadioGroup = findViewById(R.id.supply_item_color_radio_group);

        if(isCreation){
            currentSupplyItem = new SupplyItem(id, "NAME", "DATE", 0, 0, new ArrayList<DispatchEvent>(), "");//добавить конструктор полупустой
            RadioButton lightGreyButton = findViewById(R.id.color_change_radio_button_light_grey);
            lightGreyButton.setChecked(true);
        }else{
            bufferItem = MainActivity.bufferItem;
            System.out.println("isChanged -- " + isChanged + "bufferItem -- " + bufferItem);
            if(isChanged && bufferItem != null){
                editTextTitle.setText(bufferItem.getTitle());
                editTextDate.setText(bufferItem.getDate());
                editTextStartAmount.setText(bufferItem.getStartAmount());
                editTextComment.setText(bufferItem.getComment());
                setCorrectRadioButton(bufferItem.getBgColor());
            }else {
                editTextTitle.setText(currentSupplyItem.getTitle());
                editTextDate.setText(currentSupplyItem.getDate());
                editTextStartAmount.setText(String.valueOf(currentSupplyItem.getStartAmount()));
                editTextComment.setText(currentSupplyItem.getComment());
                setCorrectRadioButton(currentSupplyItem.getBgColor());
            }
        }
        RecyclerView editDispatchEventsRecycler = findViewById(R.id.recycler_edit_dispatch_events);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        editDispatchEventsRecycler.setLayoutManager(layoutManager);

        EditDispatchEventsAdapter editDispatchEventsAdapter = new EditDispatchEventsAdapter(this, currentSupplyItem.getDispatchEventsList(), currentSupplyItem.getId()); // при создании пользователя не будет позиций и не получится нажать вызвать createChangeDispIvent на пустом пользователе
        editDispatchEventsRecycler.setAdapter(editDispatchEventsAdapter);
    }

    @Override
    protected void onResume() {
        System.out.println("\t\t\t\t\tCreateChangeSupplyItemActivity Resumed");
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tCreateChangeSupplyItemActivity Destroyed");
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
    protected void setCorrectRadioButton(int correctColor){
        switch (correctColor) {
            case (R.color.app_custom_background_grey):
                RadioButton greyButton = findViewById(R.id.color_change_radio_button_grey);
                greyButton.setChecked(true);
                break;
            case (R.color.app_custom_background_red):
                RadioButton redButton = findViewById(R.id.color_change_radio_button_red);
                redButton.setChecked(true);
                break;
            case (R.color.app_custom_background_orange):
                RadioButton orangeButton = findViewById(R.id.color_change_radio_button_orange);
                orangeButton.setChecked(true);
                break;
            case (R.color.app_custom_background_yellow):
                RadioButton yellowButton = findViewById(R.id.color_change_radio_button_yellow);
                yellowButton.setChecked(true);
                break;
            case (R.color.app_custom_background_green):
                RadioButton greenButton = findViewById(R.id.color_change_radio_button_green);
                greenButton.setChecked(true);
                break;
            case (R.color.app_custom_background_blue):
                RadioButton blueButton = findViewById(R.id.color_change_radio_button_blue);
                blueButton.setChecked(true);
                break;
            case (R.color.app_custom_background_purple):
                RadioButton purpleButton = findViewById(R.id.color_change_radio_button_purple);
                purpleButton.setChecked(true);
                break;
            default:
                RadioButton lightGreyButton = findViewById(R.id.color_change_radio_button_light_grey);
                lightGreyButton.setChecked(true);
                break;
        }
    }
    protected int getSelectedRadioButtonColorId(){
        int selectedRadioButtonId = changeColorRadioGroup.getCheckedRadioButtonId();
        switch(selectedRadioButtonId){
            case (R.id.color_change_radio_button_grey) :
                return R.color.app_custom_background_grey;
            case (R.id.color_change_radio_button_red) :
                return R.color.app_custom_background_red;
            case (R.id.color_change_radio_button_orange) :
                return R.color.app_custom_background_orange;
            case (R.id.color_change_radio_button_yellow) :
                return R.color.app_custom_background_yellow;
            case (R.id.color_change_radio_button_green) :
                return R.color.app_custom_background_green;
            case (R.id.color_change_radio_button_blue) :
                return R.color.app_custom_background_blue;
            case (R.id.color_change_radio_button_purple) :
                return R.color.app_custom_background_purple;
            default:
                return R.color.app_custom_background_light_grey;
        }
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
        Intent intent;
        if(isCreation) {
            intent = new Intent(this, SupplyListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // НЕ пересоздаем item, удаляем эту активность
        }else{
            if (isChanged) {
                intent = new Intent(this, SupplyItemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Пересоздаем item, удаляем эту активность
                intent.putExtra("supplyItemId", currentSupplyItem.getId());
                intent.putExtra("isChanged", isChanged);
            } else {
                intent = new Intent(this, SupplyItemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); // НЕ пересоздаем item, удаляем эту активность
            }
        }
        startActivity(intent);
    }

    public void onApplyButtonClick(View view) {

        isChanged = true;
        boolean isException = false;
        int bufStartAmount = 777;//невозможное значение
        try {
            bufStartAmount = Integer.parseInt(String.valueOf(editTextStartAmount.getText()));
        }catch(NumberFormatException exception) {
            isException = true;
            Toast.makeText(getApplicationContext(), R.string.incorrect_prihod_format_message, Toast.LENGTH_LONG).show();
        }
        if(!isException) {

            if (isCreation) {
                currentSupplyItem.setStartAmount(bufStartAmount);
                currentSupplyItem.setTitle(String.valueOf(editTextTitle.getText()));
                currentSupplyItem.setDate(String.valueOf(editTextDate.getText()));
                currentSupplyItem.setComment(String.valueOf(editTextComment.getText()));
                currentSupplyItem.setBgColor(getSelectedRadioButtonColorId());
                MainActivity.warehouseState.getSupplyItemsList().add(currentSupplyItem);
            } else {
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setStartAmount(bufStartAmount);
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setTitle(String.valueOf(editTextTitle.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setDate(String.valueOf(editTextDate.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setComment(String.valueOf(editTextComment.getText()));
                MainActivity.warehouseState.getSupplyItemsList().get(currentSupplyItemInd).setBgColor(getSelectedRadioButtonColorId());
            }

            Intent intent = new Intent(this, SupplyItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Пересоздаем item, удаляем эту активность
            intent.putExtra("supplyItemId", currentSupplyItem.getId());
            intent.putExtra("isChanged", isChanged);
            startActivity(intent);
        }
    }

    public void onAddDispatchEventClick(View view) {
        if(isCreation) {
            Toast.makeText(getApplicationContext(), R.string.decline_dispatch_event_message, Toast.LENGTH_LONG).show();
        }else{
            MainActivity.bufferItem = new BufferSupplyItem(
                    null,
                    String.valueOf(editTextTitle.getText()),
                    String.valueOf(editTextDate.getText()),
                    String.valueOf(editTextStartAmount.getText()),
                    getSelectedRadioButtonColorId(),
                    null,
                    String.valueOf(editTextComment.getText()));

            Intent intent = new Intent(this, CreateChangeDispatchEventActivity.class);
            intent.putExtra("supplyItemId", currentSupplyItem.getId());
            intent.putExtra("isNewDispatch", true);
            intent.putExtra("eventId", MainActivity.warehouseState.getIdGen());
            startActivity(intent);
        }
    }
}