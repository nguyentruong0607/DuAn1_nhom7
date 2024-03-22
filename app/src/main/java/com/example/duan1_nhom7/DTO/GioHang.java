package com.example.duan1_nhom7.DTO;

public class GioHang {
    int id_gioHang;
    int id_sanPham;
    String imgSP;
    String tenSP;
    int soLuong;
    String mau;
    int donGia;

    public GioHang() {
    }

    public GioHang(int id_gioHang, int id_sanPham, String imgSP, String tenSP, int soLuong, String mau, int donGia) {
        this.id_gioHang = id_gioHang;
        this.id_sanPham = id_sanPham;
        this.imgSP = imgSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.mau = mau;
        this.donGia = donGia;
    }

    public int getId_gioHang() {
        return id_gioHang;
    }

    public void setId_gioHang(int id_gioHang) {
        this.id_gioHang = id_gioHang;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public String getImgSP() {
        return imgSP;
    }

    public void setImgSP(String imgSP) {
        this.imgSP = imgSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
