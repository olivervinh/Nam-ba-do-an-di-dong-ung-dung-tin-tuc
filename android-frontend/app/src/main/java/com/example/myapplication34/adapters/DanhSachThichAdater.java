package com.example.myapplication34.adapters;

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

import com.example.myapplication34.DetailAllArticleBaiTapActivity;
import com.example.myapplication34.R;
import com.example.myapplication34.models.ArticleLikeSave;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DanhSachThichAdater  extends RecyclerView.Adapter<DanhSachThichAdater.ViewHoler> {
    List<ArticleLikeSave> articleList;
    Context context;


    public DanhSachThichAdater(Context context, List<ArticleLikeSave> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danhsachthich, parent, false);

        return new ViewHoler(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHoler holder, int position) {
        ArticleLikeSave article = articleList.get(position);
        Picasso.with(context).load(article.getImagepath()).into(holder.imageView);
        holder.title.setText(article.getTitle());
        holder.btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHoler extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        Button btnXemChiTiet;

        public ViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewThich);
            title = itemView.findViewById(R.id.likeTitle);
            btnXemChiTiet = itemView.findViewById(R.id.btnLikeXemChiTiet);
        }
    }
}
