package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication34.adapters.DanhSachThichAdater;
import com.example.myapplication34.models.ArticleLikeSave;
import com.example.myapplication34.url.URL_API;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DanhSachThichActivity extends AppCompatActivity {
    DanhSachThichAdater adater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_thich);

        get();
        Button button = findViewById(R.id.btnLikeQL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachThichActivity.this, UserManagerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void get() {
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int idUserint = sharedPref.getInt("id", 0);
        String idUserString = String.valueOf(idUserint);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sssadas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("id", idUserString)
                .build();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, ArticleLikeSave.class);
        final JsonAdapter<List<ArticleLikeSave>> jsonAdapter = moshi.adapter(articlesType);


        Request request = new Request.Builder().
                url(URL_API.url+"UserLikeArticles/laydsthich")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                final List<ArticleLikeSave> Articles = jsonAdapter.fromJson(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DanhSachThichActivity.this, "dsadasfdfffffff", Toast.LENGTH_SHORT).show();
                        adater = new DanhSachThichAdater(DanhSachThichActivity.this,Articles);
                        recyclerView.setAdapter(adater);
                    }
                });
            }
        });

    }


}