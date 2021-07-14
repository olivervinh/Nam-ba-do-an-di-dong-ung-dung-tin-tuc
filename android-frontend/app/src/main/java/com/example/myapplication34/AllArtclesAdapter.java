package com.example.myapplication34;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AllArtclesAdapter  extends RecyclerView.Adapter<AllArtclesAdapter.ViewHolder> {
    private List<Article> articleList;
    private Context context;

    public AllArtclesAdapter(ArrayList<Article> articleArrayList, Context context) {
        this.articleList = articleArrayList;
        this.context = context;
    }
    public void filterList(List<Article> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        articleList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
           Article article = articleList.get(position);
        Picasso.with(context).load(article.getImagepath()).into(holder.image);
        holder.title.setText(article.getTitle());
        holder.btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailAllArticleBaiTap.class);
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
        TextView title,category;
        ImageView image;
        Button btnXemChiTiet;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewItemAllArticleTitle);
            category = itemView.findViewById(R.id.textViewItemArticleCategory);
            image = itemView.findViewById(R.id.imageViewItemAllAtricle);
            btnXemChiTiet = itemView.findViewById(R.id.btnViewMoreItemArticle);
        }
    }
}
