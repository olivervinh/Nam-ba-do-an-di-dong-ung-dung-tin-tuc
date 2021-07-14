package com.example.myapplication34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    ImageView image1,image2,image3;
    TextView text1 ,text2, text3, textXinChaos;

    ViewFlipper viewFlipper;
    Button btnLui;
    Button btnTiep;
    Button xemtatca;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;


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
        Intent intent = this.getIntent();
        Intent intent1 = this.getIntent();
        textXinChaos.setText("Quản lí tài khoản");

        textXinChaos.setTextColor(-65536);
        textXinChaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,UserManagerActivity.class);
                intent1.putExtra("xinchao",intent.getStringExtra("name"));
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
        getFearture1(MainActivity.this);
        getFearture2(MainActivity.this);
        getFearture3(MainActivity.this);
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
                Article article=gson.fromJson(json,Article.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image1 = findViewById(R.id.imageItem1);
                        Picasso.with(MainActivity.this).load(article.getImagepath()).resize(100,100).centerCrop().into(image1);
                        text1 = findViewById(R.id.titile1);
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
                Article article=gson.fromJson(json,Article.class);
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
                Article article=gson.fromJson(json,Article.class);
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
}