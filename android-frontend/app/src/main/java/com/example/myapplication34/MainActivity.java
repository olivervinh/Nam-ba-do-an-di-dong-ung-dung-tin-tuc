package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    ImageView image1,image2,image3, image4, image5, image6;
    TextView text1 ,text2, text3, textXinChaos, text4, text5, text6;

    ViewFlipper viewFlipper;
    Button btnLui;
    Button btnTiep,btnDung;
    Button xemtatca;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnLui = findViewById(R.id.btnLuis);
        btnTiep = findViewById(R.id.btnTiep);
        viewFlipper = findViewById(R.id.viewFlipper);

        textXinChaos = (TextView) findViewById(R.id.txtXinChao);

        getImageSileAPI();
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String name = sharedPref.getString("fullname", "");
        textXinChaos.setText(name);

        textXinChaos.setTextColor(-65536);
        textXinChaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,UserManagerActivity.class);
                startActivity(intent1);
            }
        });
        xemtatca=findViewById(R.id.viewAllbtn);
        xemtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AllArticlesActivity.class);
                startActivity(intent);
            }
        });
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
        VideoView videoView =(VideoView)findViewById(R.id.videoView2);
        Button buttonPlay = findViewById(R.id.btnPlayVideo);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "android.resource://com.example.myapplication34/"+R.raw.video;
                Uri uri = Uri.parse(path);
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
        btnDung = findViewById(R.id.btnPauseVideo2);
        btnDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });
        getFearture1(MainActivity.this);
        getFearture2(MainActivity.this);
        getFearture3(MainActivity.this);
        getFearture4(MainActivity.this);
        getFearture5(MainActivity.this);
        getFearture6(MainActivity.this);
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        navigationView = findViewById(R.id.nvView);
        Menu menu = navigationView.getMenu();
        AddMenuCategory(menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                Intent intent = new Intent(MainActivity.this, ArticlesCategoryActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    private void getImageSileAPI() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL_API.url+"ImageSlide").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();


                String url1 = stringToArray(json, ImageSlide[].class).get(0).getUrl();
                String url2 = stringToArray(json, ImageSlide[].class).get(1).getUrl();
                String url3 = stringToArray(json, ImageSlide[].class).get(2).getUrl();

                ImageView imageView1 = findViewById(R.id.imageView1);
                ImageView imageView2 = findViewById(R.id.imageViewd2);
                ImageView imageView3 = findViewById(R.id.imageViewd3);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       Picasso.with(MainActivity.this).load(url1).into(imageView1);
                        Picasso.with(MainActivity.this).load(url2).into(imageView2);
                        Picasso.with(MainActivity.this).load(url3).into(imageView3);

                    }
                });

            }
        });
    }

    private void ViewArticlesCategory(int id) {

    }


    private void getFearture1(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/1")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Log.d("dsadasd",json);
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image1 = findViewById(R.id.imageItem1);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image1);
                        text1 = findViewById(R.id.title1);
                        text1.setText(article.getTitle());
                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",1);
                                context.startActivity(intent);
                            }
                        });
                        text1.setSingleLine(false);
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text1.setText(content);
                    }
                });

            }
        });
    }
    private void getFearture2(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/2")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image2 = findViewById(R.id.imageItem2);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image2);
                        text2 = findViewById(R.id.titile2);
                        text2.setText(article.getTitle());
                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",2);
                                context.startActivity(intent);
                            }
                        });
                        text2.setSingleLine(false);
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text2.setText(content);
                    }
                });

            }
        });
    }
    private void getFearture3(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/3")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image3 = findViewById(R.id.imageItem3);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image3);
                        text3 = findViewById(R.id.titile3);
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",3);
                                context.startActivity(intent);
                            }
                        });
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text3.setText(content);
                    }
                });

            }
        });
    }
    private void getFearture4(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/4")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Log.d("dsadasd",json);
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image4= findViewById(R.id.imageItem4);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image4);
                        text4 = findViewById(R.id.title4);
                        text4.setText(article.getTitle());
                        text4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",4);
                                context.startActivity(intent);
                            }
                        });
                        text4.setSingleLine(false);
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text4.setText(content);
                    }
                });

            }
        });
    }
    private void getFearture5(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/5")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Log.d("dsadasd",json);
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image5 = findViewById(R.id.imageItem5);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image5);
                        text5 = findViewById(R.id.title5);
                        text5.setText(article.getTitle());
                        text5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",5);
                                context.startActivity(intent);
                            }
                        });
                        text5.setSingleLine(false);
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text5.setText(content);
                    }
                });

            }
        });
    }
    private void getFearture6(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/6")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Log.d("dsadasd",json);
                Gson gson=new GsonBuilder().create();
                ArticleCategory article=gson.fromJson(json,ArticleCategory.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image6 = findViewById(R.id.imageItem6);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image6);
                        text6 = findViewById(R.id.title6);
                        text6.setText(article.getTitle());
                        text6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTap.class);
                                intent.putExtra("ma",6);
                                context.startActivity(intent);
                            }
                        });
                        text6.setSingleLine(false);
                        SpannableString content = new SpannableString(article.getTitle());
                        content.setSpan(new UnderlineSpan(), 0, article.getTitle().length(), 0);
                        text6.setText(content);
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
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

}