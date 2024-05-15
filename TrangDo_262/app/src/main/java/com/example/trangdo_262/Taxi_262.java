package com.example.trangdo_262;

public class Taxi_262 {
    private int maXe;
    private String soXe;
    private float quangDuong;
    private int donGia;
    private int khuyenMai;

    public Taxi_262(int maXe, String soXe, float quangDuong, int donGia, int khuyenMai) {
        this.maXe = maXe;
        this.soXe = soXe;
        this.quangDuong = quangDuong;
        this.donGia = donGia;
        this.khuyenMai = khuyenMai;
    }

    public int getMaXe() {
        return maXe;
    }

    public void setMaXe(int maXe) {
        this.maXe = maXe;
    }

    public String getSoXe() {
        return soXe;
    }

    public void setSoXe(String soXe) {
        this.soXe = soXe;
    }

    public float getQuangDuong() {
        return quangDuong;
    }

    public void setQuangDuong(float quangDuong) {
        this.quangDuong = quangDuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
    public float tongTien(){
        return donGia*quangDuong*(100-khuyenMai)/100;
    }
}
