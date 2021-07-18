package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnRegister, btnLogin;
    EditText edtUserName, edtPassword;
    TextView DangKiDangKi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DangKiDangKi = findViewById(R.id.txtDangKiDangKi);
        btnLogin = findViewById(R.id.btnLoginLogin);
        edtUserName = findViewById(R.id.edtUserNameLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        SharedPreferences settings = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        DangKiDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RequestBody requestBody = new FormBody.Builder()
                        .add("username", edtUserName.getText().toString())
                        .add("password", edtPassword.getText().toString())
                        .build();
                // Khởi tạo OkHttpClient để lấy dữ liệu.
                OkHttpClient client = new OkHttpClient();
                // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                Moshi moshi = new Moshi.Builder().build();
                Request request = new Request.Builder()
                        .url(URL_API.url+"users/dangnhap")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Đang nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                        String json;
                        json = response.body().string();
                        if(json!=null){
                            Type usersType = Types.newParameterizedType(UserResponse.class);
                            final JsonAdapter<UserResponse> jsonAdapter = moshi.adapter(usersType);
                            UserResponse userResponse = jsonAdapter.fromJson(json);
                            Gson g = new Gson(); UserResponse p = g.fromJson(json, UserResponse.class);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("name",p.getFullname());
                            intent.putExtra("username",p.getUsername());
                            intent.putExtra("password",p.getPassword());
                            SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("id", p.getId());
                            editor.putString("fullname", p.getFullname());
                            editor.putString("username", p.getUsername());
                            editor.putString("password", p.getPassword());
                            editor.commit();
                            startActivity(intent);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Đang nhập thành công", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Đang nhập thất bại", Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }
                });
            }
        });

    }


}