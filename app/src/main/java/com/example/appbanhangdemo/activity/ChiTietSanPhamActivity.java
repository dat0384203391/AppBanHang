package com.example.appbanhangdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.model.GioHang;
import com.example.appbanhangdemo.model.SanPham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView txtTen, txtGia, txtMoTa;
    Spinner spinner;
    Button btnDatMua;
    int id = 0;
    String tenChiTiet = "";
    int giaChiTiet = 0;
    String hinhAnhChiTiet = "";
    String moTaChiTiet = "";
    int idSanPham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGioHang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                        if (MainActivity.mangGioHang.get(i).getIdsp() == id) {
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if (MainActivity.mangGioHang.get(i).getSoluongsp() >= 10) {
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(giaChiTiet * MainActivity.mangGioHang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = soluong * giaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soluong));
                    }

                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = soluong * giaChiTiet;
                    MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soLuong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getID();
        tenChiTiet = sanPham.getTenSanPham();
        giaChiTiet = sanPham.getGiaSanPham();
        hinhAnhChiTiet = sanPham.getHinhSanPham();
        moTaChiTiet = sanPham.getMoTaSanPham();
        idSanPham = sanPham.getIDSanPham();
        txtTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá : " + decimalFormat.format(giaChiTiet) + " Đ");
        txtMoTa.setText(moTaChiTiet);
        Glide.with(getApplicationContext()).load(hinhAnhChiTiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.erroimage)
                .into(imgChiTiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChiTiet = findViewById(R.id.toolbarChiTietSanPham);
        txtTen = findViewById(R.id.textviewTenChiTietSanPham);
        txtGia = findViewById(R.id.textviewGiaChiTietSP);
        txtMoTa = findViewById(R.id.textviewMoTaChiTietSP);
        spinner = findViewById(R.id.spinner);
        btnDatMua = findViewById(R.id.buttonDatMua);
        imgChiTiet = findViewById(R.id.imageviewChiTietSanPham);

    }
}