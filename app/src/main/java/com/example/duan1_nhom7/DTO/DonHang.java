package com.example.duan1_nhom7.DTO;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id_donHang;
    private int id_sanPham;
    private int id_user;
    private String tenSP;
    private String ngayMua;
    private int soLuong;
    private int gia;
    private String status;
    private String image; // Thêm trường image

    private String mau;

    private String pttt;
    private String location;
    private String phone;

    private String nameUser;

    public DonHang() {
    }

    public DonHang(int id_donHang, int id_sanPham, int id_user, String tenSP, String ngayMua, int soLuong, int gia, String status, String image, String mau, String pttt, String location, String phone, String nameUser) {
        this.id_donHang = id_donHang;
        this.id_sanPham = id_sanPham;
        this.id_user = id_user;
        this.tenSP = tenSP;
        this.ngayMua = ngayMua;
        this.soLuong = soLuong;
        this.gia = gia;
        this.status = status;
        this.image = image;
        this.mau = mau;
        this.pttt = pttt;
        this.location = location;
        this.phone = phone;
        this.nameUser = nameUser;
    }

    public int getId_donHang() {
        return id_donHang;
    }

    public void setId_donHang(int id_donHang) {
        this.id_donHang = id_donHang;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getPttt() {
        return pttt;
    }

    public void setPttt(String pttt) {
        this.pttt = pttt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
