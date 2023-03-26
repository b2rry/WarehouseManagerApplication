package com.appksa.warehousemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appksa.warehousemanager.excel.ExcelHelper;

public class AboutProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_program);
        if(getIntent().getBooleanExtra("isReport", false)) {
            setTitle("Отчет конвертирования");
            TextView mainText = findViewById(R.id.main_text);
            mainText.setText(ExcelHelper.finalReport);
        }
    }

    public void onMainActivityClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}