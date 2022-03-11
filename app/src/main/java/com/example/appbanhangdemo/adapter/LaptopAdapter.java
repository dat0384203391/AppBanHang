package com.example.appbanhangdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> arrayLaptop) {
        this.context = context;
        this.arrayLaptop = arrayLaptop;
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTenLaptop, txtGiaLaptop, txtMoTaLaptop;
        ImageView imgLaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.txtTenLaptop = view.findViewById(R.id.textviewTenLaptop);
            viewHolder.txtGiaLaptop = view.findViewById(R.id.textviewGiaLaptop);
            viewHolder.txtMoTaLaptop = view.findViewById(R.id.textviewMoTaLaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.imageviewLaptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenLaptop.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLaptop.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        viewHolder.txtMoTaLaptop.setMaxLines(2);
        viewHolder.txtMoTaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaLaptop.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.erroimage)
                .into(viewHolder.imgLaptop);

        return view;
    }
}
