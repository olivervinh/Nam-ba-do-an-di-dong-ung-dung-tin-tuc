package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication34.url.URL_API;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserManagerActivity extends AppCompatActivity {
    static  String name;
    Button btnQuayLai, btnDangXuat, btnSave;
    EditText edtFullName, edtUsername, edtPassword, edtId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        btnQuayLai = findViewById(R.id.btnQuayLaiUsermanager);
        edtFullName = findViewById(R.id.edtHoVaTenUserManager);
        edtUsername = findViewById(R.id.edtUsernameUserManager);
        edtPassword = findViewById(R.id.edtPasswordUserManager);
        btnSave = findViewById(R.id.btnUserManagerSave);
        edtId = findViewById(R.id.edtUerMangerId);
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int id = sharedPref.getInt("id", 0);
        String fullname = sharedPref.getString("fullname", "");
        String username = sharedPref.getString("username", "");
        String password = sharedPref.getString("password", "");
        edtId.setText(id+"");
        edtFullName.setText(fullname);
        edtUsername.setText(username);
        edtPassword.setText(password);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserManagerActivity.this, "dsadasd", Toast.LENGTH_SHORT).show();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",  edtUsername.getText().toString())
                        .add("password", edtPassword.getText().toString())
                        .add("fullname",edtFullName.getText().toString())
                        .build();
                // Khởi tạo OkHttpClient để lấy dữ liệu.
                OkHttpClient client = new OkHttpClient();

                // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                Request request = new Request.Builder()
                        .url(URL_API.url+"users/"+id)
                        .put(requestBody)
                        .build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserManagerActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });
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
                SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                Intent intent = new Intent(UserManagerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Button btnLikeList = findViewById(R.id.btnDSThich);
        btnLikeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagerActivity.this, DanhSachThichActivity.class);
                startActivity(intent);
            }
        });

        Button btnSave = findViewById(R.id.btnDSLuu);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManagerActivity.this, DanhSachLuuActivity.class);
                startActivity(intent);
            }
        });
    }
}