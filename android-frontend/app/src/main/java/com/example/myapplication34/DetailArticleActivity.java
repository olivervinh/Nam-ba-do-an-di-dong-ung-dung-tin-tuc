package com.example.myapplication34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DetailArticleActivity extends AppCompatActivity {

    ImageView image;
    TextView title, description;
    Button btnQuayLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);
        btnQuayLai = findViewById(R.id.btnDetailQuayLai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailArticleActivity.this, AllArticlesActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        int id = intent.getIntExtra("ma",0);
        detail(id);
    }
    private void detail(int id) {

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_API.url+"articles/"+id )
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new GsonBuilder().create();
                Article article = gson.fromJson(json, Article.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image = findViewById(R.id.imageDetail);
                        Picasso.with(DetailArticleActivity.this).load(article.getImagepath()).resize(100, 100).centerCrop().into(image);
                        title = findViewById(R.id.titileDetail);
                        title.setText(article.getTitle());
                        description = findViewById(R.id.descriptionDetail);
                        description.setText(article.getDescription());
                    }
                });

            }
        });
    }
}

