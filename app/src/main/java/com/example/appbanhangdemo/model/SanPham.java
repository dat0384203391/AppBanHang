package com.example.appbanhangdemo.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int ID;
    private String TenSanPham;
    private Integer GiaSanPham;
    private String HinhSanPham;
    private String MoTaSanPham;
    private int IDSanPham;

    public SanPham(int ID, String tenSanPham, Integer giaSanPham, String hinhSanPham, String moTaSanPham, int IDSanPham) {
        this.ID = ID;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        HinhSanPham = hinhSanPham;
        MoTaSanPham = moTaSanPham;
        this.IDSanPham = IDSanPham;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getHinhSanPham() {
        return HinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        HinhSanPham = hinhSanPham;
    }

    public String getMoTaSanPham() {
        return MoTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        MoTaSanPham = moTaSanPham;
    }

    public int getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(int IDSanPham) {
        this.IDSanPham = IDSanPham;
    }
}
