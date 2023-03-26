package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appksa.warehousemanager.dialog.AcceptLoadingFromExternalFileDialogFragment;
import com.appksa.warehousemanager.excel.ExcelHelper;
import com.appksa.warehousemanager.json.JsonHelper;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.LogBookItem;
import com.appksa.warehousemanager.model.SummaryInformation;
import com.appksa.warehousemanager.model.SupplyItem;
import com.appksa.warehousemanager.model.WarehouseState;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements AcceptLoadingFromExternalFileDialogFragment.LoadingFromExternalFileDialogListener{

    public static WarehouseState warehouseState;
    public static Queue<LogBookItem> logBookSaveItemsQueue;
    public static Queue<LogBookItem> logBookChangeItemsQueue;
    SummaryInformation summaryInformation = new SummaryInformation();
    JsonHelper jHelper;
    ExcelHelper exHelper;
    SharedPreferences prefs;
    public static boolean isStateSaved;
    TextView saveWarningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.appksa.warehousemanager", MODE_PRIVATE);
        jHelper = new JsonHelper();
        exHelper = new ExcelHelper();

        if (prefs.getBoolean("firstRun", true)) {
            generateTestPositions();
            prefs.edit().putBoolean("firstRun", false).apply();
            warehouseState.updateLogLists();
            jHelper.exportToJsonAndInternalSave(this, warehouseState);
        }else{
            warehouseState = jHelper.importFromJsonFromInternalFile(this);
            warehouseState.updateLogQueues();
        }

        isStateSaved = true;
        saveWarningText = findViewById(R.id.save_warning_text_view);
//        System.out.println("\t\t\t\t\tMainActivity Created");
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(isStateSaved){
            saveWarningText.setText(R.string.save_not_required_message);
            saveWarningText.setTextColor(ContextCompat.getColor(this, R.color.app_custom_swamp_green));
        }else{
            saveWarningText.setText(R.string.save_warning_message);
            saveWarningText.setTextColor(ContextCompat.getColor(this, R.color.app_custom_soft_red));
        }
        updateSummaryInformation();
//        System.out.println("\t\t\t\t\tMainActivity Resumed");
    }
    @Override
    protected void onDestroy() {
//        System.out.println("\t\t\t\t\tMainActivity Destroyed");
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void generateTestPositions(){
        warehouseState = new WarehouseState(0L, null);
        List<SupplyItem> supplyItemsList = new ArrayList<>();

        SupplyItem supplyItem = new SupplyItem(warehouseState.getIdGen(), "S-106/AC 08x20 HX+ Керамический наполнитель", "2022.07.14", 500, R.color.app_custom_background_light_grey, getList(), "Комментарий к этой позиции на складе.", true);
        supplyItemsList.add(supplyItem);


        Queue<LogBookItem> logBookSaveItemsQueue = new LinkedList<>();
        Queue<LogBookItem> logBookChangeItemsQueue = new LinkedList<>();

        try {
            for (int i = 0; i < 50; i++) {
                logBookSaveItemsQueue.add(new LogBookItem("", "",0));
                logBookChangeItemsQueue.add(new LogBookItem("", "",0));
            }
        }catch(IllegalStateException ex){
            System.out.println("IllegalStateException catch");
        }

        warehouseState.setSupplyItemsList(supplyItemsList);
        MainActivity.logBookSaveItemsQueue = logBookSaveItemsQueue;
        MainActivity.logBookChangeItemsQueue = logBookChangeItemsQueue;
    }

    private List<DispatchEvent> getList(){
        List<DispatchEvent> dispatchEventsList = new ArrayList<>();

        dispatchEventsList.add(new DispatchEvent(125, "Контрагент 1", "2022.07.19", warehouseState.getIdGen(), false));
        dispatchEventsList.add(new DispatchEvent(200, "Контрагент 2", "2022.08.03", warehouseState.getIdGen(), true));

        return dispatchEventsList;
    }

    protected void updateSummaryInformation(){
        summaryInformation.updateAmounts();

        TextView valueFinalPositions = findViewById(R.id.value_final_positions);
        TextView valueFinalFactual = findViewById(R.id.value_final_factual);
        TextView valueFinalAvailable = findViewById(R.id.value_final_available);
        TextView valueConsumablePositions = findViewById(R.id.value_consumable_positions);
        TextView valueConsumableFactual = findViewById(R.id.value_consumable_factual);
        TextView valueConsumableAvailable = findViewById(R.id.value_consumable_available);

        valueFinalPositions.setText(String.valueOf(summaryInformation.getFinalProductPositionsAmount()));
        valueFinalFactual.setText(String.valueOf(summaryInformation.getFinalProductFactualRestAmount()));
        valueFinalAvailable.setText(String.valueOf(summaryInformation.getFinalProductAvailableRestAmount()));
        valueConsumablePositions.setText(String.valueOf(summaryInformation.getConsumableMaterialPositionsAmount()));
        valueConsumableFactual.setText(String.valueOf(summaryInformation.getConsumableMaterialFactualRestAmount()));
        valueConsumableAvailable.setText(String.valueOf(summaryInformation.getConsumableMaterialAvailableRestAmount()));
    }

    public static void addSaveLog(int operationCode){
        Date date = new Date();
        String currentMoment = String.format("%td.%<tm.%<tY / %<tR", date);
        if(operationCode == 1){
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Сохранено состояние склада", operationCode));
        } else if (operationCode == 2) {
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Выгружен файл состояния", operationCode));
        } else if (operationCode == 9) {
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Выгружен Exel-файл позиций", operationCode));
        } else if (operationCode == 10) {
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "Загружен Exel-файл позиций", operationCode));
        }else{
            logBookSaveItemsQueue.poll();
            logBookSaveItemsQueue.add(new LogBookItem(currentMoment, "received incorrect code", 400));
        }
    }
    public static void addChangeLog(String itemTitle, int operationCode){
        String action = "";
        switch(operationCode){
            case 3:
                action = "Добавлена позиция";
                break;
            case 4:
                action = "Изменена позиция";
                break;
            case 5:
                action = "Удалена позиция";
                break;
            case 6:
                action = "Добавлена отгрузка";
                break;
            case 7:
                action = "Изменена отгрузка";
                break;
            case 8:
                action = "Удалена отгрузка";
                break;
            default:
                operationCode = 400;
                action = "received incorrect code";
                break;
        }
        isStateSaved = false;
        logBookChangeItemsQueue.poll();
        logBookChangeItemsQueue.add(new LogBookItem(action, itemTitle, operationCode));
    }

    public void onSupplyListActivityClick(View view) {
        Intent intent = new Intent(this, SupplyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onLogBookActivityClick(View view) {
        Intent intent = new Intent(this, LogBookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onAboutProgramActivityClick(View view) {
        Intent intent = new Intent(this, AboutProgramActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void onSaveClick(View view) {
        addSaveLog(1);
        warehouseState.updateLogLists();
        if(jHelper.exportToJsonAndInternalSave(this, warehouseState)){
            Toast.makeText(getApplicationContext(), "Состояние сохранено успешно", Toast.LENGTH_LONG).show();
            isStateSaved = true;
            saveWarningText.setText(R.string.save_not_required_message);
            saveWarningText.setTextColor(ContextCompat.getColor(this, R.color.app_custom_swamp_green));
        }else {
            Toast.makeText(getApplicationContext(), "СОХРАНЕНИЕ НЕ УДАЛОСЬ!", Toast.LENGTH_LONG).show();
        }
    }
    public void onExportFileClick(View view) {
        addSaveLog(2);
        warehouseState.updateLogLists();
        if(jHelper.exportToJsonAndExternalSave(this, warehouseState)){
            jHelper.exportToJsonAndInternalSave(this, warehouseState); //сохранение состояния для сохранения лога (не интуитивно понятно что лог не сохранится после выхода)
            Toast.makeText(getApplicationContext(), "Файл выгружен успешно", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "ВЫГРУЗКА НЕ УДАЛОСЬ!", Toast.LENGTH_LONG).show();
        }
    }

    public void onLoadExternalFileClick(MenuItem item) {
        AcceptLoadingFromExternalFileDialogFragment myDialogFragment = new AcceptLoadingFromExternalFileDialogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        myDialogFragment.show(fragmentManager, "acceptExternalFileLoading");
    }

    @Override
    public void onDialogAcceptLoadingClick(DialogFragment dialog) {
        warehouseState = jHelper.importFromJsonFromExternalFile(this);
        if(warehouseState == null){
            Toast.makeText(getApplicationContext(), "Файл не найден, необходимо четко следовать инструкции", Toast.LENGTH_LONG).show();
            warehouseState = jHelper.importFromJsonFromInternalFile(this);
        }else {
            warehouseState.updateLogQueues();
            Toast.makeText(getApplicationContext(), "Состояние приложения успешно обновлено", Toast.LENGTH_LONG).show();
            isStateSaved = false;
            saveWarningText.setText(R.string.save_warning_message);
            saveWarningText.setTextColor(ContextCompat.getColor(this, R.color.app_custom_soft_red));
            updateSummaryInformation();
            SupplyListActivity.isChanged = true;
        }
    }
    public void onExelExportClick(MenuItem item) {
        warehouseState.updateLogLists();
        if(exHelper.externalExportXSSFPositionDataFile(this, warehouseState.getSupplyItemsList())){
            addSaveLog(9);
            jHelper.exportToJsonAndInternalSave(this, warehouseState); //сохранение состояния для сохранения лога (не интуитивно понятно что лог не сохранится после выхода)
            Toast.makeText(getApplicationContext(), "Файл выгружен успешно", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), "ВЫГРУЗКА НЕ УДАЛОСЬ!", Toast.LENGTH_LONG).show();
        }
    }
    public void onExelImportClick(MenuItem item) {

        warehouseState = exHelper.externalImportXSSFDataFile(this);

        if(warehouseState == null){
            Toast.makeText(getApplicationContext(), "Файл не найден, либо содержит критические ошибки, необходимо четко следовать инструкции", Toast.LENGTH_LONG).show();
            warehouseState = jHelper.importFromJsonFromInternalFile(this);
        }else{
            addSaveLog(10);
            warehouseState.updateLogLists();
            Toast.makeText(getApplicationContext(), "Состояние приложения успешно обновлено", Toast.LENGTH_LONG).show();
            isStateSaved = false;
            saveWarningText.setText(R.string.save_warning_message);
            saveWarningText.setTextColor(ContextCompat.getColor(this, R.color.app_custom_soft_red));
            updateSummaryInformation();
            SupplyListActivity.isChanged = true;
        }

        if(ExcelHelper.finalReport.equals("")){
            ExcelHelper.finalReport = "Не было сформировано никаких сообщений при конвертировании файла";
        }
        Intent intent = new Intent(this, AboutProgramActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("isReport", true);
        startActivity(intent);
    }
}