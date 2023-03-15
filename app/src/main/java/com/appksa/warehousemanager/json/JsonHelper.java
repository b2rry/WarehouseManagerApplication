package com.appksa.warehousemanager.json;

import android.content.Context;

import com.appksa.warehousemanager.model.WarehouseState;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class JsonHelper {

    private static final String FILE_NAME = "warehouse_state_data.json";

    public boolean exportToJsonAndInternalSave(Context context, WarehouseState currentState) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(currentState);

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean exportToJsonAndExternalSave(Context context, WarehouseState currentState) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(currentState);

        try {
            File externalDir = new File(context.getExternalFilesDir(null), FILE_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(externalDir);
            //fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public WarehouseState importFromJsonFromInternalFile(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            return gson.fromJson(streamReader, WarehouseState.class);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

    public WarehouseState importFromJsonFromExternalFile(Context context) {


        try{
            File externalDir = new File(context.getExternalFilesDir(null), FILE_NAME);
            FileInputStream fileInputStream = new FileInputStream(externalDir);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream);

            Gson gson = new Gson();
            return gson.fromJson(streamReader, WarehouseState.class);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }
}
