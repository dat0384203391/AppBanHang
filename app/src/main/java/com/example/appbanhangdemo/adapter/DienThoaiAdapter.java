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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtTenDienThoai, txtGiaDienThoai, txtMoTaDienThoai;
        ImageView imgDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.txtTenDienThoai = view.findViewById(R.id.textviewTenDienThoai);
            viewHolder.txtGiaDienThoai = view.findViewById(R.id.textviewGiaDienThoai);
            viewHolder.txtMoTaDienThoai = view.findViewById(R.id.textviewMoTaDienThoai);
            viewHolder.imgDienThoai = view.findViewById(R.id.imageviewDienThoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenDienThoai.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienThoai.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        viewHolder.txtMoTaDienThoai.setMaxLines(2);
        viewHolder.txtMoTaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDienThoai.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.erroimage)
                .into(viewHolder.imgDienThoai);

        return view;
    }
}
