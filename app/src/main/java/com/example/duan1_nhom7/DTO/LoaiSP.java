package com.example.duan1_nhom7.DTO;

public class LoaiSP {
    private int id_Loai;
    private String tenLoaiSP;
    private String imgLoaiSP;

    public LoaiSP(int id_Loai, String tenLoaiSP, String imgLoaiSP) {
        this.id_Loai = id_Loai;
        this.tenLoaiSP = tenLoaiSP;
        this.imgLoaiSP = imgLoaiSP;
    }

    public int getId() {
        return id_Loai;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public String getImgLoaiSP() {
        return imgLoaiSP;
    }
}