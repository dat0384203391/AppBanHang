package com.example.appbanhangdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.activity.ChiTietSanPhamActivity;
import com.example.appbanhangdemo.model.SanPham;
import com.example.appbanhangdemo.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> arraySanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = arraySanPham.get(position);
        holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanpPham.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        Glide.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.erroimage)
                .into(holder.imgHinhSanPham);
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhSanPham;
        TextView txtTenSanPham, txtGiaSanpPham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhSanPham = itemView.findViewById(R.id.imageHinhAnhMoiNhat);
            txtGiaSanpPham = itemView.findViewById(R.id.textviewGiaSanPhamMoiNhat);
            txtTenSanPham = itemView.findViewById(R.id.textviewTenSanPhamMoiNhat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham",arraySanPham.get(getPosition()));
                    CheckConnection.showToast_Short(context,arraySanPham.get(getPosition()).getTenSanPham());
                    context.startActivity(intent);
                }
            });

        }
    }
}
