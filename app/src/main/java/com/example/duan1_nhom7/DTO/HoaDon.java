package com.example.duan1_nhom7.DTO;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int id_HoaDon;
    private int id_user;
    private String ngayMua;
    private int tongTien;
    private String pttt;
    private String status;
    private String nameUser;
    private String location;
    private String phone;


    public HoaDon() {
    }

    // Constructor đầy đủ tham số
    public HoaDon(int id_HoaDon, int id_user, String ngayMua, int tongTien, String pttt, String status, String nameUser, String location, String phone) {
        this.id_HoaDon = id_HoaDon;
        this.id_user = id_user;
        this.ngayMua = ngayMua;
        this.tongTien = tongTien;
        this.pttt = pttt;
        this.status = status;
        this.nameUser = nameUser;
        this.location = location;
        this.phone = phone;
    }

    // Getter và Setter cho tất cả thuộc tính
    public int getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(int id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getPttt() {
        return pttt;
    }

    public void setPttt(String pttt) {
        this.pttt = pttt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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
}