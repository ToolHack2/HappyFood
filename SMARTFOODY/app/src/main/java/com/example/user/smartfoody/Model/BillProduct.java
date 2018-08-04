package com.example.user.smartfoody.Model;

public class BillProduct {

    private String Hinh;
    private String TenSP;
    private String SoLuong;


    public BillProduct(){}

    public BillProduct(String hinh, String tenSP, String soLuong) {
        Hinh = hinh;
        TenSP = tenSP;
        SoLuong = soLuong;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }
}
