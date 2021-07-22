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

import java.util.List;
import java.util.zip.Inflater;

public class DanhSachLuuAdapter extends RecyclerView.Adapter<DanhSachLuuAdapter.ViewHolder> {
    List<ArticleLikeSave> articleLikeSaves;
    Context context;

    public DanhSachLuuAdapter(List<ArticleLikeSave> articleLikeSaves, Context context) {
        this.articleLikeSaves = articleLikeSaves;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachluu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ArticleLikeSave article = articleLikeSaves.get(position);
        Picasso.with(context).load(article.getImagepath()).resize(300,300).centerCrop().into(holder.image);
        holder.title.setText(article.getTitle());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailAllArticleBaiTap.class);
                intent.putExtra("ma",articleLikeSaves.get(position).getId());
                context.startActivity(intent);
                Toast.makeText(context, "chọn :"+articleLikeSaves.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return articleLikeSaves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn;
        TextView title;
        ImageView image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btnXemCTSSS);
            title = itemView.findViewById(R.id.titileSaveddd);
            image = itemView.findViewById(R.id.imageViewSave);
        }
    }
}
