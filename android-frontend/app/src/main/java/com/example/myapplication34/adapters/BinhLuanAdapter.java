package com.example.myapplication34.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication34.models.BinhLuanServerToClientModel;
import com.example.myapplication34.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHoler> {
    Context context;
    List<BinhLuanServerToClientModel> binhLuanList;

    public BinhLuanAdapter(Context context, List<BinhLuanServerToClientModel> binhLuanList) {
        this.context = context;
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_binhluan, parent, false);
      return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHoler holder, int position) {
        BinhLuanServerToClientModel binhLuan;
        binhLuan = binhLuanList.get(position);
        holder.nameUser.setText(binhLuan.getUserName()+"");
        holder.content.setText(binhLuan.getContent());
    }

    @Override
    public int getItemCount() {
        return binhLuanList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView nameUser, content;
        public ViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.idNameBinhLuan);
            content = itemView.findViewById(R.id.idNoiDungBinhLuan);
        }
    }
}
