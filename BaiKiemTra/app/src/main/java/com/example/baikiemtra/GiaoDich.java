package com.example.baikiemtra;

public class GiaoDich {
    private int ma;
    private String noiDung;
    private String ngay;
    private boolean loaiGiaoDich;
    private String hoTen;
    private int soTien;

    public GiaoDich(String noiDung, String ngay, boolean loaiGiaoDich, String hoTen, int soTien) {
        this.noiDung = noiDung;
        this.ngay = ngay;
        this.loaiGiaoDich = loaiGiaoDich;
        this.hoTen = hoTen;
        this.soTien = soTien;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public boolean isLoaiGiaoDich() {
        return loaiGiaoDich;
    }

    public void setLoaiGiaoDich(boolean loaiGiaoDich) {
        this.loaiGiaoDich = loaiGiaoDich;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}

