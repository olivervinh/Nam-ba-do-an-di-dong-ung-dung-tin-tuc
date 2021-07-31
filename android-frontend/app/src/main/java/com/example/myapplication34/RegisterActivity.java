package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister, btnQuayLai;
    EditText edtFullName, edtUserName, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtFullName = findViewById(R.id.edtFullnameRegister);
        edtUserName = findViewById(R.id.edtUsernameRegister);
        edtPassword = findViewById(R.id.edtPasswordRegister);
        btnRegister = findViewById(R.id.btnRegisterRegister);
        btnQuayLai = findViewById(R.id.btnRegisterQuaylai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFullName.getText();
                String username = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                edtPassword.getText();
                if(username.contains("gmail.com")&&password.length()>=6){
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", edtUserName.getText().toString())
                            .add("password", edtPassword.getText().toString())
                            .add("fullname",edtFullName.getText().toString())
                            .build();
                    // Khởi tạo OkHttpClient để lấy dữ liệu.
                    OkHttpClient client = new OkHttpClient();

                    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                    Request request = new Request.Builder()
                            .url(URL_API.url+"users")
                            .post(requestBody)
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
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Vui lòng  nhập đúng với yêu cầu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}