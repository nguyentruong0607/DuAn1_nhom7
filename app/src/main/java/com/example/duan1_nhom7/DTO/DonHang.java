package com.example.duan1_nhom7.DTO;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id_donHangChiTiet;
    private int id_HoaDon;
    private int id_sanPham;
    private int soLuong;
    private int giaBan;
    private String mau;
    private ProductInfo productInfo;

    // Constructor không tham số
    public DonHang() {
    }

    // Constructor đầy đủ tham số
    public DonHang(int id_donHangChiTiet, int id_HoaDon, int id_sanPham, int soLuong, int giaBan, String mau) {
        this.id_donHangChiTiet = id_donHangChiTiet;
        this.id_HoaDon = id_HoaDon;
        this.id_sanPham = id_sanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.mau = mau;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    // Getter và Setter cho tất cả thuộc tính
    public int getId_donHangChiTiet() {
        return id_donHangChiTiet;
    }

    public void setId_donHangChiTiet(int id_donHangChiTiet) {
        this.id_donHangChiTiet = id_donHangChiTiet;
    }

    public int getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(int id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }
}


