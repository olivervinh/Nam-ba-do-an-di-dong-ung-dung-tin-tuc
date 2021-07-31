package com.example.myapplication34.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication34.DetailAllArticleBaiTapActivity;
import com.example.myapplication34.R;
import com.example.myapplication34.models.ArticleCategory;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllArtclesAdapter  extends RecyclerView.Adapter<AllArtclesAdapter.ViewHolder> {
    private List<ArticleCategory> articleList;
    private Context context;

    public AllArtclesAdapter(ArrayList<ArticleCategory> articleArrayList, Context context) {
        this.articleList = articleArrayList;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_article, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        ArticleCategory article = articleList.get(position);
        Picasso.with(context).load(article.getImagepath()).into(holder.image);
        holder.title.setText(article.getTitle());
        holder.brief.setText(article.getBrief());
        holder.cate.setText(article.getNameCategory());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailAllArticleBaiTapActivity.class);
                intent.putExtra("ma",articleList.get(position).getId());
                context.startActivity(intent);
                Toast.makeText(context, "chọn :"+articleList.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, brief, cate;
        ImageView image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cate = itemView.findViewById(R.id.textView28);
            title = itemView.findViewById(R.id.textViewItemAllArticleTitle);
            image = itemView.findViewById(R.id.imageViewItemAllAtricle);
            brief = itemView.findViewById(R.id.idBrief);
        }
    }
}
