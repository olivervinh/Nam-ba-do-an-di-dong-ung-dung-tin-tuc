package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.example.myapplication34.models.Article;
import com.example.myapplication34.models.Category;
import com.example.myapplication34.models.ImageSlide;
import com.example.myapplication34.url.URL_API;
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
    ImageView image1,image2,image3, image4, image5, image6,image7,image8,image9,image10,image11,image12,image13,image14,image15;
    TextView text1 ,text2, text3, textXinChaos, text4, text5, text6,text7,text8,text9,text10,text11,text12,text13,text14,text15;

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
//jkhkjhjkhhjhj
        getImageSileAPI();

//bnmbkjhjkhjkhjkhkj
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
        ArticlesCategoryNoiBat(MainActivity.this);
        ArticleCategoryThoiSu(MainActivity.this);
        ArticlesCategoryTheGiois(MainActivity.this);
        ArticlesCategoryGiaoDucs(MainActivity.this);
        ArticleCategoryTheThaos(MainActivity.this);


        // Setup toggle to display hamburger icon with nice animation
        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        navigationView = findViewById(R.id.nvView);
        Menu menu = navigationView.getMenu();
        AddMenuCategory(menu);////gsfsdfsdfsdddddddddddddddddddddddddddddddd
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
            }///zxczxvxvxc
        });
    }

    private void ArticleCategoryTheThaos(Context context) {
        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)

        Request request = new Request.Builder()
                .url(URL_API.url+"articles/thethaos")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                Article[] articles=gson.fromJson(json,Article[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image13 = findViewById(R.id.imageItem13);
                        Picasso.with(MainActivity.this).load(articles[0].getImagepath()).resize(100,100).centerCrop().into(image13);
                        text13 = findViewById(R.id.title13);
                        text13.setText(articles[0].getTitle());
                        text13.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[0].getId());
                                context.startActivity(intent);
                            }
                        });
                        image14 = findViewById(R.id.imageItem14);
                        Picasso.with(MainActivity.this).load(articles[1].getImagepath()).resize(100,100).centerCrop().into(image14);
                        text14 = findViewById(R.id.title14);
                        text14.setText(articles[1].getTitle());
                        text14.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[1].getId());
                                context.startActivity(intent);
                            }
                        });

                        image15 = findViewById(R.id.imageItem15);
                        Picasso.with(MainActivity.this).load(articles[2].getImagepath()).resize(100,100).centerCrop().into(image15);
                        text15 = findViewById(R.id.title15);
                        text15.setText(articles[2].getTitle());
                        text15.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[2].getId());
                                context.startActivity(intent);
                            }
                        });
                    }
                });

            }
        });

    }

    private void ArticlesCategoryGiaoDucs(Context context) {
         // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)

        Request request = new Request.Builder()
                .url(URL_API.url+"articles/giaoducs")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                Article[] articles=gson.fromJson(json,Article[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image10 = findViewById(R.id.imageItem10);
                        Picasso.with(MainActivity.this).load(articles[0].getImagepath()).resize(100,100).centerCrop().into(image10);
                        text10 = findViewById(R.id.title10);
                        text10.setText(articles[0].getTitle());
                        text10.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[0].getId());
                                context.startActivity(intent);
                            }
                        });
                        image11 = findViewById(R.id.imageItem11);
                        Picasso.with(MainActivity.this).load(articles[1].getImagepath()).resize(100,100).centerCrop().into(image11);
                        text11 = findViewById(R.id.title11);
                        text11.setText(articles[1].getTitle());
                        text11.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[1].getId());
                                context.startActivity(intent);
                            }
                        });

                        image12 = findViewById(R.id.imageItem12);
                        Picasso.with(MainActivity.this).load(articles[2].getImagepath()).resize(100,100).centerCrop().into(image12);
                        text12 = findViewById(R.id.title12);
                        text12.setText(articles[2].getTitle());
                        text12.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[2].getId());
                                context.startActivity(intent);
                            }
                        });
                    }
                });

            }
        });
    }

    private void ArticlesCategoryTheGiois(Context context) {
          // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/thegiois")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                Article[] articles=gson.fromJson(json,Article[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image7 = findViewById(R.id.imageItem7);
                        Picasso.with(MainActivity.this).load(articles[0].getImagepath()).resize(100,100).centerCrop().into(image7);
                        text7 = findViewById(R.id.title7);
                        text7.setText(articles[0].getTitle());
                        text7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[0].getId());
                                context.startActivity(intent);
                            }
                        });
                        image8 = findViewById(R.id.imageItem8);
                        Picasso.with(MainActivity.this).load(articles[1].getImagepath()).resize(100,100).centerCrop().into(image8);
                        text8 = findViewById(R.id.title8);
                        text8.setText(articles[1].getTitle());
                        text8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[1].getId());
                                context.startActivity(intent);
                            }
                        });

                        image9 = findViewById(R.id.imageItem9);
                        Picasso.with(MainActivity.this).load(articles[2].getImagepath()).resize(100,100).centerCrop().into(image9);
                        text9 = findViewById(R.id.title9);
                        text9.setText(articles[2].getTitle());
                        text9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",articles[2].getId());
                                context.startActivity(intent);
                            }
                        });
                    }
                });

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




    private void ArticlesCategoryNoiBat(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/noibats")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                Article[] articles=gson.fromJson(json,Article[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image1 = findViewById(R.id.imageItem1);
                        Picasso.with(MainActivity.this).load(articles[0].getImagepath()).resize(100,100).centerCrop().into(image1);
                        text1 = findViewById(R.id.title1);
                        text1.setText(articles[0].getTitle());
                        text1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",1);
                                context.startActivity(intent);
                            }
                        });
                        image2 = findViewById(R.id.imageItem2);
                        Picasso.with(MainActivity.this).load(articles[1].getImagepath()).resize(100,100).centerCrop().into(image2);
                        text2 = findViewById(R.id.titile2);
                        text2.setText(articles[1].getTitle());
                        text2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",2);
                                context.startActivity(intent);
                            }
                        });

                        image3 = findViewById(R.id.imageItem3);
                        Picasso.with(MainActivity.this).load(articles[2].getImagepath()).resize(100,100).centerCrop().into(image3);
                        text3 = findViewById(R.id.titile3);
                        text3.setText(articles[2].getTitle());
                        text3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",3);
                                context.startActivity(intent);
                            }
                        });
                    }
                });

            }
        });
    }


    private void ArticleCategoryThoiSu(Context context){

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Request request = new Request.Builder()
                .url(URL_API.url+"articles/thoisus")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new GsonBuilder().create();
                Article[] articles=gson.fromJson(json,Article[].class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image4= findViewById(R.id.imageItem4);
                        Picasso.with(MainActivity.this).load(articles[0].getImagepath()).resize(100,100).centerCrop().into(image4);
                        text4 = findViewById(R.id.title4);
                        text4.setText(articles[0].getTitle());
                        text4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",4);
                                context.startActivity(intent);
                            }
                        });


                        image5 = findViewById(R.id.imageItem5);
                        Picasso.with(MainActivity.this).load(articles[1].getImagepath()).resize(100,100).centerCrop().into(image5);
                        text5 = findViewById(R.id.title5);
                        text5.setText(articles[1].getTitle());
                        text5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",5);
                                context.startActivity(intent);
                            }
                        });

                        image6 = findViewById(R.id.imageItem6);
                        Picasso.with(MainActivity.this).load(articles[2].getImagepath()).resize(100,100).centerCrop().into(image6);
                        text6 = findViewById(R.id.title6);
                        text6.setText(articles[2].getTitle());
                        text6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                                intent.putExtra("ma",6);
                                context.startActivity(intent);
                            }
                        });
                    }
                });

            }
        });
    }

    private void AddMenuCategory(Menu menu){//gsdfsdfdsfdsfsdfsdfsdfsdfsdfsdfsd
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
    }//sdfsd
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