package com.example.duan1_nhom7.DTO;

public class LoaiSP {
    private int id;
    private String tenLoaiSP;
    private String imgLoaiSP;

    public LoaiSP(int id, String tenLoaiSP, String imgLoaiSP) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.imgLoaiSP = imgLoaiSP;
    }

    public int getId() {
        return id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public String getImgLoaiSP() {
        return imgLoaiSP;
    }
}