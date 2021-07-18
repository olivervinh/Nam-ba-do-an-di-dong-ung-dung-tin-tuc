package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailAllArticleBaiTap extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    ViewFlipper viewFlipper;
    Button btnLui;
    Button btnTiep;
    NavigationView navigationView;
    BinhLuanAdapter binhluanAdapter;
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
       ViewFlipperHam();
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
        Detail(id);
       BinhLuanHam(id);
        ReportHam(id);
        ThichVaBoThich(id);
        LuuVaBoLuu(id);
        TatCaBinhLuan(id);

        NavicationDrawerLayMenuCategory();
        TinTucLienQuan(id);



    }

    private void ViewFlipperHam() {
        btnTiep = findViewById(R.id.btnTiep);
        btnLui = findViewById(R.id.btnLuis);
        viewFlipper = findViewById(R.id.viewFlipper);
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
    }

    private void NavicationDrawerLayMenuCategory() {
        navigationView = findViewById(R.id.nvView);
        Menu menu = navigationView.getMenu();
        AddMenuCategory(menu);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = new Intent(DetailAllArticleBaiTap.this, ArticlesCategoryActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void ReportHam(int id) {
        Button report = findViewById(R.id.btnReportDetail);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DetailAllArticleBaiTap.this, ReportActivity.class);
                intent2.putExtra("id",id);
                startActivity(intent2);
            }
        });
    }

    private void BinhLuanHam(int id) {
        Button binhluan = findViewById(R.id.btnCommentArticle);
        binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailAllArticleBaiTap.this, CommentActivity.class);
                intent1.putExtra("id",id);
                startActivity(intent1);
            }
        });
    }

    private void LuuVaBoLuu(int id) {
        Button btnSave = findViewById(R.id.btnSaveArticle);
        String idBaiBaoString = String.valueOf(id);
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int idUserint = sharedPref.getInt("id", 0);
        String idUserString = String.valueOf(idUserint);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSave.getText()=="Lưu"){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("idArticle", idBaiBaoString)
                            .add("idUser", idUserString)
                            .add("status", "1")
                            .build();
                    Request request = new Request.Builder()
                            .url(URL_API.url+"UserSaveArticles")
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response !=null){
                                        btnSave.setText("Đã lưu");
                                        Toast.makeText(DetailAllArticleBaiTap.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
                else{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("idArticle", idBaiBaoString)
                            .add("idUser", idUserString)
                            .build();
                    Request request = new Request.Builder()
                            .url(URL_API.url+"UserLikeArticles")
                            .delete(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response !=null){
                                        btnSave.setText("Lưu");
                                        Toast.makeText(DetailAllArticleBaiTap.this, "Bỏ lưu thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }


            }
        });
    }

    private void ThichVaBoThich(int id) {
        Button btnThich = findViewById(R.id.btnLikeArticle);
        String idBaiBaoString = String.valueOf(id);
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int idUserint = sharedPref.getInt("id", 0);
        String idUserString = String.valueOf(idUserint);
        btnThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnThich.getText()=="Thích"){
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("idArticle", idBaiBaoString)
                            .add("idUser", idUserString)
                            .add("status", "1")
                            .build();
                    Request request = new Request.Builder()
                            .url(URL_API.url+"UserLikeArticles")
                            .post(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response !=null){
                                        btnThich.setText("Đã thích");
                                        Toast.makeText(DetailAllArticleBaiTap.this, "Thích thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
                else{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("idArticle", idBaiBaoString)
                            .add("idUser", idUserString)
                            .build();
                    Request request = new Request.Builder()
                            .url(URL_API.url+"UserLikeArticles")
                            .delete(requestBody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response !=null){
                                        btnThich.setText("Thích");
                                        Toast.makeText(DetailAllArticleBaiTap.this, "Bỏ thích thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }

            }
        });

    }

    private void TatCaBinhLuan(int id) {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerViewBL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client1 = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, BinhLuanServerToClientModel.class);
        final JsonAdapter<List<BinhLuanServerToClientModel>> jsonAdapter = moshi.adapter(articlesType);

        // Tạo request lên server.
        Request request1 = new Request.Builder()
                .url(URL_API.url+"Comments/usercomment/"+id)
                .build();

        // Thực thi request.
        client1.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("Error", "Network Error");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                String json = response.body().string();
                final List<BinhLuanServerToClientModel> binhluans = jsonAdapter.fromJson(json);

                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binhluanAdapter = new BinhLuanAdapter( DetailAllArticleBaiTap.this ,binhluans);
                        recyclerView.setAdapter(binhluanAdapter);
                        binhluanAdapter.notifyDataSetChanged();

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
    private void Detail(int id) {

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
    private void TinTucLienQuan(int id){
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
                        textbaitap1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DetailAllArticleBaiTap.this, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",Articles.get(0).getId());
                                startActivity(intent);
                            }
                        });

                        textbaitap2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DetailAllArticleBaiTap.this, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",Articles.get(1).getId());
                                startActivity(intent);
                            }
                        });

                        textbaitap3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DetailAllArticleBaiTap.this, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",Articles.get(3).getId());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
    private void AddMenuCategory(Menu menu){
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, Article.class);
        final JsonAdapter<List<Article>> jsonAdapter = moshi.adapter(articlesType);

        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(URL_API.url+"Categories")
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
                Moshi moshi = new Moshi.Builder().build();

                Type categoriesType = Types.newParameterizedType(List.class, Category.class);
                JsonAdapter<List<Category>> jsonAdapter = moshi.adapter(categoriesType);

                List<Category> Categories = jsonAdapter.fromJson(json);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < Categories.toArray().length; i++) {
                            menu.add(Menu.NONE,Categories.get(i).getId(),Menu.NONE,Categories.get(i).getName());
                        }
                    }
                });
            }
        });
    }
}