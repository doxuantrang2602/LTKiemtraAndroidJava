package com.example.quanlythisinh;

import java.io.Serializable;

public class ThiSinh implements Serializable {
    private String soBaoDanh;
    private String hoTen;
    private float toan;
    private float ly;
    private float hoa;

    public ThiSinh(String soBaoDanh, String hoTen, float toan, float ly, float hoa) {
        this.soBaoDanh = soBaoDanh;
        this.hoTen = hoTen;
        this.toan = toan;
        this.ly = ly;
        this.hoa = hoa;
    }

    public String getSoBaoDanh() {
        return soBaoDanh;
    }

    public void setSoBaoDanh(String soBaoDanh) {
        this.soBaoDanh = soBaoDanh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public float getToan() {
        return toan;
    }

    public void setToan(float toan) {
        this.toan = toan;
    }

    public float getLy() {
        return ly;
    }

    public void setLy(float ly) {
        this.ly = ly;
    }

    public float getHoa() {
        return hoa;
    }

    public void setHoa(float hoa) {
        this.hoa = hoa;
    }
    public float tongDiem(){
        return toan + ly + hoa;
    }
    public float diemTB(){
        return (toan + ly + hoa) /  3;
    }
}
