package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SauKhiReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sau_khi_report);
        Button btnQuayLaiTrangChu = findViewById(R.id.btnQuayLaiTrangChu);
        btnQuayLaiTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SauKhiReportActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}