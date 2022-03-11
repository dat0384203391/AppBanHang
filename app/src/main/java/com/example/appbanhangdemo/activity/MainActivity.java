package com.example.appbanhangdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.adapter.LoaispAdapter;
import com.example.appbanhangdemo.adapter.SanPhamAdapter;
import com.example.appbanhangdemo.model.GioHang;
import com.example.appbanhangdemo.model.Loaisp;
import com.example.appbanhangdemo.model.SanPham;
import com.example.appbanhangdemo.ultil.CheckConnection;
import com.example.appbanhangdemo.ultil.Server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;

    ArrayList<Loaisp> mangLoaisp;
    LoaispAdapter loaispAdapter;

    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    ArrayList<SanPham> mangSanPham;
    SanPhamAdapter sanPhamAdapter;

    public static ArrayList<GioHang> mangGioHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFilpper();
            getDuLieuLoaisp();
            getDuLieuSPMoiNhat();
            CatchOnItemLisview();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

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

    private void CatchOnItemLisview() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LapTopActivity.class);
                            intent.putExtra("idloaisanpham", mangLoaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void getDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.dungDanSanPhamMoiNhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int ID = 0;
                            String tenSanPham = "";
                            Integer giaSanPham = 0;
                            String hinhAnhSanPham = "";
                            String moTaSanPham = "";
                            int IDSanPham = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ID = jsonObject.getInt("id");
                                    tenSanPham = jsonObject.getString("tensp");
                                    giaSanPham = jsonObject.getInt("giasp");
                                    hinhAnhSanPham = jsonObject.getString("hinhanhsp");
                                    moTaSanPham = jsonObject.getString("motasp");
                                    IDSanPham = jsonObject.getInt("idsanpham");
                                    mangSanPham.add(new SanPham(ID, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, IDSanPham));
                                    sanPhamAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaisp,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenloaisp = jsonObject.getString("tenloaisp");
                                    hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                                    mangLoaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                                    loaispAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mangLoaisp.add(3, new Loaisp(0, "Liên Hệ", "data:image/jpeg;base64,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"));
                            mangLoaisp.add(4, new Loaisp(0, "Thông Tin", "data:image/jpeg;base64,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"));

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast_Short(getApplicationContext(), error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void actionViewFilpper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://photo-cms-kienthuc.zadn.vn/zoom/800/Uploaded/nguyenvan/2016_12_06/phim/phim-0_JEMD.png");
        mangquangcao.add("data:image/jpeg;base64,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");
        mangquangcao.add("https://laptops.vn/uploads/1920_x_659_1614062618.jpg");
        mangquangcao.add("https://cellphones.com.vn/sforum/wp-content/uploads/2020/08/OPPO-F17-1.jpg");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangLoaisp = new ArrayList<>();
        mangLoaisp.add(0, new Loaisp(0, "Trang Chính", "https://vietwebgroup.vn/admin/uploads/trang-chu-la-gi-tim-hieu-ve-trang-chu-la-gi.png"));

        loaispAdapter = new LoaispAdapter(mangLoaisp, getApplicationContext());
        listViewManHinhChinh.setAdapter(loaispAdapter);

        mangSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangSanPham);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewManHinhChinh.setAdapter(sanPhamAdapter);

        if (mangGioHang != null){

        }else {
            mangGioHang = new ArrayList<>();
        }
    }
}