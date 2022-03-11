package com.example.appbanhangdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.model.Loaisp;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListLoaisp;
    Context context;

    @Override
    public int getCount() {
        return arrayListLoaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtTenLoaisp;
        ImageView imgLoaisp;
    }

    public LoaispAdapter(ArrayList<Loaisp> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            //get layout ra
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp, null);
            viewHolder.txtTenLoaisp = view.findViewById(R.id.textViewLoaisp);
            viewHolder.imgLoaisp = view.findViewById(R.id.imageViewLoaisp);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txtTenLoaisp.setText(loaisp.getTenLoaiSP());
        Glide.with(context).load(loaisp.getHinhAnhLoaiSP()).placeholder(R.drawable.noimage).
                error(R.drawable.erroimage).into(viewHolder.imgLoaisp);
        return view;
    }
}
