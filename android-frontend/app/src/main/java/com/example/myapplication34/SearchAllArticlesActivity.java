package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchAllArticlesActivity extends AppCompatActivity {
    AllArtclesAdapter artclesAdapter;
    TextView txtKetquatim;
    Button quaylai,btnTim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all_articles);
        Intent intent = getIntent();
        String s = intent.getStringExtra("search");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        txtKetquatim = findViewById(R.id.txtTuKhoa);
        txtKetquatim.setText(s);
        quaylai = findViewById(R.id.btnSearchAlllQuayLai);
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (SearchAllArticlesActivity.this, AllArticlesActivity.class);
                startActivity(intent1);
            }
        });
        final RecyclerView recyvArticles = (RecyclerView) findViewById(R.id.recyclerViewSearch);
        recyvArticles.setLayoutManager(new LinearLayoutManager(SearchAllArticlesActivity.this));
        RequestBody requestBody = new FormBody.Builder()
                .add("search", s)
                .build();
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, ArticleCategory.class);
        final JsonAdapter<List<ArticleCategory>> jsonAdapter = moshi.adapter(articlesType);

        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(URL_API.url+"Articles/search")
                .post(requestBody)
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
                        artclesAdapter = new AllArtclesAdapter((ArrayList<ArticleCategory>) Articles,SearchAllArticlesActivity.this);
                        recyvArticles.setAdapter(artclesAdapter);
                    }
                });
            }
        });
    }
}