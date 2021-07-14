package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserManagerActivity extends AppCompatActivity {
    static  String name;
    Button btnQuayLai, btnDangXuat;
    EditText edtFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        btnQuayLai = findViewById(R.id.btnQuayLaiUsermanager);
        edtFullName = findViewById(R.id.edtHoVaTenUserManager);
        Intent intent = getIntent();
        name = intent.getStringExtra("xinchao");
        edtFullName.setText(name);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagerActivity.this, MainActivity.class);
                intent.putExtra("usermanage",edtFullName.getText());
                Toast.makeText(UserManagerActivity.this,edtFullName.getText(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}