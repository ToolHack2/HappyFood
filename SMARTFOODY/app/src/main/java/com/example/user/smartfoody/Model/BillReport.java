package com.example.user.smartfoody.Model;

public class BillReport {

    private String NgayMua;
    private String TongTien;

    public BillReport(){}

    public BillReport(String ngayMua, String tongTien) {
        NgayMua = ngayMua;
        TongTien = tongTien;
    }

    public String getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(String ngayMua) {
        NgayMua = ngayMua;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }
}
