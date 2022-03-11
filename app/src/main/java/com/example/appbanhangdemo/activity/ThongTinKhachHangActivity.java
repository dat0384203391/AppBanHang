package com.example.appbanhangdemo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangdemo.R;
import com.example.appbanhangdemo.ultil.CheckConnection;
import com.example.appbanhangdemo.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    EditText edtTenKhachHang, edtSoDienThoai, edtEmail;
    Button btnXacNhan, btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        AnhXa();
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            eventButton();
        }else {
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn kiểm tra lại kết nối");
        }
    }

    private void eventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTenKhachHang.getText().toString().trim();
                String sdt = edtSoDienThoai.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.dungDanDonHang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String madonhang) {
                                    Log.d("madonhang",madonhang);
                                    if (Integer.parseInt(madonhang) > 0){
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest request = new StringRequest(Request.Method.POST, Server.dungDanChiTietDonHang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("1")){
                                                            MainActivity.mangGioHang.clear();
                                                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn đã thêm giỏ hàng thành công");
                                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                            CheckConnection.showToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");

                                                        }else {
                                                            CheckConnection.showToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng bạn bị lỗi");

                                                        }
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                }
                                        ){
                                            @Nullable
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                JSONArray jsonArray = new JSONArray();
                                                for (int i = 0; i < MainActivity.mangGioHang.size() ; i++) {
                                                    JSONObject jsonObject = new JSONObject();
                                                    try {
                                                        jsonObject.put("madonhang",madonhang);
                                                        jsonObject.put("masanpham",MainActivity.mangGioHang.get(i).getIdsp());
                                                        jsonObject.put("tensanpham",MainActivity.mangGioHang.get(i).getTensp());
                                                        jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGiasp());
                                                        jsonObject.put("soluongsanpham",MainActivity.mangGioHang.get(i).getSoluongsp());

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonArray.put(jsonObject);
                                                }
                                                HashMap<String,String> hashMap = new HashMap<>();
                                                hashMap.put("json",jsonArray.toString());
                                                return hashMap;
                                            }
                                        };
                                        queue.add(request);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void AnhXa() {
        edtTenKhachHang = findViewById(R.id.edittextTenKhacHang);
        edtEmail = findViewById(R.id.edittextEmail);
        edtSoDienThoai = findViewById(R.id.edittextSoDienThoai);
        btnXacNhan = findViewById(R.id.buttonXacNhan);
        btnTroVe = findViewById(R.id.buttonTroVe);
    }
}