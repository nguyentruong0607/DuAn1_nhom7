package com.example.duan1_nhom7.DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HoaDon {
    private int id_HoaDon;
    private String ngayMua;
    private int gia;

    private int soLuong;

    public HoaDon() {
    }

    public HoaDon(int id_HoaDon, String ngayMua, int gia, int soLuong) {
        this.id_HoaDon = id_HoaDon;
        this.ngayMua = ngayMua;
        this.gia = gia;
        this.soLuong = soLuong;
    }

    public int getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(int id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
