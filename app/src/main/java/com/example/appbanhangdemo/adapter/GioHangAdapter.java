package com.example.appbanhangdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.activity.GioHangActivity;
import com.example.appbanhangdemo.activity.MainActivity;
import com.example.appbanhangdemo.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> mangGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> mangGioHang) {
        this.context = context;
        this.mangGioHang = mangGioHang;
    }

    @Override
    public int getCount() {
        return mangGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return mangGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imgGioHang;
        TextView txtTenGioHang, txtGiaGioHang;
        Button btnMinus, btnValues, btnPlus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txtTenGioHang = view.findViewById(R.id.textviewTenGioHang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.textviewGiaGioHang);
            viewHolder.imgGioHang = view.findViewById(R.id.imageviewGioHang);
            viewHolder.btnMinus = view.findViewById(R.id.butonMinus);
            viewHolder.btnValues = view.findViewById(R.id.butonValue);
            viewHolder.btnPlus = view.findViewById(R.id.butonPlus);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");
        Glide.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.erroimage)
                .into(viewHolder.imgGioHang);
        viewHolder.btnValues.setText(gioHang.getSoluongsp() + "");

        // bắt sự kiện 2 button
        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl >= 10){
            viewHolder.btnValues.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }else if (sl <= 1){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }else if (sl >=1){
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) + 1;
                int slht = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(slMoiNhat);
                long giaMoiNhat = giaht *slMoiNhat / slht;
                MainActivity.mangGioHang.get(i).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                GioHangActivity.EventUltil();
                if (slMoiNhat > 9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoiNhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoiNhat));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) - 1;
                int slht = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(slMoiNhat);
                long giaMoiNhat = giaht *slMoiNhat / slht;
                MainActivity.mangGioHang.get(i).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                GioHangActivity.EventUltil();
                if (slMoiNhat < 2){
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoiNhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slMoiNhat));
                }
            }
        });
        return view;
    }
}
