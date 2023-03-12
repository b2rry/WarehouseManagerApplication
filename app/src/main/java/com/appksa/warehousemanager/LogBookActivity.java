package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_book);

        System.out.println("\t\t\t\t\tLogBookActivity Created");

    }

    @Override
    protected void onResume(){
        super.onResume();
        System.out.println("\t\t\t\t\tLogBookActivity Resumed");
    }
    @Override
    protected void onDestroy() {
        System.out.println("\t\t\t\t\tLogBookActivity Destroyed");
        super.onDestroy();
    }
    public void onMainActivityClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}