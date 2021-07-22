package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArticlesCategoryActivity extends AppCompatActivity {
    Button btnQuaylai;
    AllArtclesAdapter artclesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_category);
        Intent intent = getIntent();
        int id=intent.getIntExtra("id",0);
        btnQuaylai = findViewById(R.id.btnQuayLaidsds);
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ArticlesCategoryActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        final RecyclerView recyvArticles = (RecyclerView) findViewById(R.id.recyclerViewCategory);
        recyvArticles.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, ArticleCategory.class);
        final JsonAdapter<List<ArticleCategory>> jsonAdapter = moshi.adapter(articlesType);

        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(URL_API.url+"Articles/laytheoloai/"+id)
                .build();

        // Thực thi request.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("Error", "Network Error");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                String json = response.body().string();
                final List<ArticleCategory> Articles = jsonAdapter.fromJson(json);

                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        artclesAdapter = new AllArtclesAdapter((ArrayList<ArticleCategory>) Articles,ArticlesCategoryActivity.this);
                        recyvArticles.setAdapter(artclesAdapter);
                    }
                });
            }
        });
    }
}