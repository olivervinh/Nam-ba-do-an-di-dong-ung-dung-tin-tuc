package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

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

public class DetailAllArticleBaiTap extends AppCompatActivity {
    ArrayList<Article> articleArrayList;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    ViewFlipper viewFlipper;
    Button btnLui;
    Button btnTiep;
    ImageView image,imagebaitap1,imagebaitap2,imagebaitap3;
    TextView title, description,textbaitap1,textbaitap2,textbaitap3, summary;
    Button btnQuayLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_all_article_baitap);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view
        btnTiep = findViewById(R.id.btnTiep);
        btnLui = findViewById(R.id.btnLuis);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();
            }
        });

        btnLui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });
        btnQuayLai = findViewById(R.id.btnDetailBTQuaylai);
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAllArticleBaiTap.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        int id = intent.getIntExtra("ma",0);
        detail(id);
        btnLui = findViewById(R.id.btnLuis);
        btnTiep = findViewById(R.id.btnTiep);
        viewFlipper = findViewById(R.id.viewFlipper);
        // This will display an Up icon (<-), we will replace it with hamburger later
        OkHttpClient client = new OkHttpClient();
        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(URL_API.url+"Articles/tintuclienquan1/"+id)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Moshi moshi = new Moshi.Builder().build();

                Type articlesType = Types.newParameterizedType(List.class, Article.class);
                JsonAdapter<List<Article>> jsonAdapter = moshi.adapter(articlesType);

                List<Article> Articles = jsonAdapter.fromJson(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imagebaitap1 = findViewById(R.id.imageItembaitap1);
                        imagebaitap2 = findViewById(R.id.imageItembaitap2);
                        imagebaitap3 = findViewById(R.id.imageItembaitap3);
                        textbaitap1 = findViewById(R.id.titlebaitap1);
                        textbaitap2 = findViewById(R.id.titlebaitap2);
                        textbaitap3 = findViewById(R.id.titlebaitap3);
                        textbaitap1.setText(Articles.get(0).getTitle());
                        textbaitap2.setText(Articles.get(1).getTitle());
                        textbaitap3.setText(Articles.get(2).getTitle());
                        Picasso.with(DetailAllArticleBaiTap.this).load(Articles.get(0).getImagepath()).into(imagebaitap1);
                        Picasso.with(DetailAllArticleBaiTap.this).load(Articles.get(1).getImagepath()).into(imagebaitap2);
                        Picasso.with(DetailAllArticleBaiTap.this).load(Articles.get(2).getImagepath()).into(imagebaitap3);
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                        image = findViewById(R.id.imageViewBaiTap);
                        Picasso.with(DetailAllArticleBaiTap.this).load(article.getImagepath()).resize(100, 100).centerCrop().into(image);
                        title = findViewById(R.id.titileBaiTap);
                        title.setText(article.getTitle());
                        summary = findViewById(R.id.TomtatBaiTap);
                        summary.setText(article.getSummary());
                        description = findViewById(R.id.descriptionBaiTap);
                        description.setText(article.getDescription());

                    }
                });

            }
        });
    }

}