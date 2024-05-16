package com.example.dekhachsan;

public class HoaDon_26 {
    private int ma;
    private String hoTen;
    private int soPhong;
    private int donGia;
    private int soNgay;

    public HoaDon_26(int ma, String hoTen, int soPhong, int donGia, int soNgay) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.soPhong = soPhong;
        this.donGia = donGia;
        this.soNgay = soNgay;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoNgay() {
        return soNgay;
    }

    public void setSoNgay(int soNgay) {
        this.soNgay = soNgay;
    }
    public int tongTien(){
        return soNgay*donGia;
    }
}
