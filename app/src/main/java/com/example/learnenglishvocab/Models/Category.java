package com.example.learnenglishvocab.Models;

public class Category {
    String TenNhomTu, hinhanh, IDNhomTu;

    public Category() {
    }

    public Category(String IDNhomTu ,String tenNhomTu, String hinhanh) {
        this.IDNhomTu = IDNhomTu;
        this.TenNhomTu = tenNhomTu;
        this.hinhanh = hinhanh;
    }

    public String getIDNhomTu() {
        return IDNhomTu;
    }

    public void setIDNhomTu(String IDNhomTu) {
        this.IDNhomTu = IDNhomTu;
    }

    public String getTenNhomTu() {
        return TenNhomTu;
    }

    public void setTenNhomTu(String tenNhomTu) {
        TenNhomTu = tenNhomTu;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
