package com.example.user.smartfoody.Model;

public class HistoryBill {

    String Ngay;
    String Id;
    String TongTien;
    String NoiDung;

    public HistoryBill(){}

    public HistoryBill(String ngay, String id, String tongTien, String noiDung) {
        Ngay = ngay;
        Id = id;
        TongTien = tongTien;
        NoiDung = noiDung;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
