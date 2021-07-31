package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication34.adapters.AllArtclesAdapter;
import com.example.myapplication34.models.Article;
import com.example.myapplication34.models.ArticleCategory;
import com.example.myapplication34.models.Category;
import com.example.myapplication34.url.URL_API;
import com.google.android.material.navigation.NavigationView;
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

public class AllArticlesActivity extends AppCompatActivity {
    Button quaylai, tim;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    NavigationView navigationView;

    EditText stringSearch;
    AllArtclesAdapter artclesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_articles);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Khởi tạo RecyclerView.
        quaylai = findViewById(R.id.btnQuaylaiAllArticle);
        tim = findViewById(R.id.btnAllArtiTim);
        stringSearch = findViewById(R.id.edtSearchAll);
        String chuoiTimKiem = stringSearch.getText().toString();
        Toast.makeText(this, chuoiTimKiem, Toast.LENGTH_SHORT).show();

        tim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AllArticlesActivity.this, "chuoi tim" +stringSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AllArticlesActivity.this,SearchAllArticlesActivity.class);
                intent.putExtra("search",stringSearch.getText().toString());
                startActivity(intent);
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllArticlesActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        getAllArticles();

        navigationView = findViewById(R.id.nvView);
        Menu menu = navigationView.getMenu();
        AddMenuCategory(menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = new Intent(AllArticlesActivity.this, ArticlesCategoryActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void getAllArticles() {
        final RecyclerView recyvArticles = (RecyclerView) findViewById(R.id.recyArticleAll);
        recyvArticles.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type articlesType = Types.newParameterizedType(List.class, ArticleCategory.class);
        final JsonAdapter<List<ArticleCategory>> jsonAdapter = moshi.adapter(articlesType);

        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(URL_API.url+"Articles/laytheodieukien")
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
                TextView soketqua = findViewById(R.id.idSoKetQua);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        artclesAdapter = new AllArtclesAdapter((ArrayList<ArticleCategory>) Articles,AllArticlesActivity.this);
                        recyvArticles.setAdapter(artclesAdapter);
                        soketqua.setText(artclesAdapter.getItemCount()+"");
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
    private void AddMenuCategory(Menu menu){
        OkHttpClient client = new OkHttpClient();
        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
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