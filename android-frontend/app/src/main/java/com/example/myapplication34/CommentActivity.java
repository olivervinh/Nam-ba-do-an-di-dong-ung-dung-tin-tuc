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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CommentActivity extends AppCompatActivity {
    Button btnCmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        int IdArticle = intent.getIntExtra("id",0);
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int idUser = sharedPref.getInt("id", 0);
        String idUserString = String.valueOf(idUser);
        String idArticleString = String.valueOf(IdArticle);
        EditText edtContent = findViewById(R.id.edtComment);
        Button btnQuayLai = findViewById(R.id.btnQuayLaiqqqqqqq);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CommentActivity.this, DetailAllArticleBaiTap.class);
                intent1.putExtra("ma",IdArticle);
                startActivity(intent1);
            }
        });
        btnCmt = findViewById(R.id.btnGuiComment);
        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody requestBody = new FormBody.Builder()
                        .add("idUser",idUserString)
                        .add("content", edtContent.getText().toString())
                        .add("idArticle",idArticleString)
                        .build();
                // Khởi tạo OkHttpClient để lấy dữ liệu.
                OkHttpClient client = new OkHttpClient();

                // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                Request request = new Request.Builder()
                        .url(URL_API.url+"Comments")
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

                                Toast.makeText(CommentActivity.this, "Gửi binh luận thành công", Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(CommentActivity.this, DetailAllArticleBaiTap.class);
                                intent2.putExtra("ma",IdArticle);
                                startActivity(intent2);
                            }
                        });

                    }
                });
            }
        });
    }
}