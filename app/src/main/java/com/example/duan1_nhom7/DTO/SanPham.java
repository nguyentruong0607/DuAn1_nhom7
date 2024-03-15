package com.example.duan1_nhom7.DTO;

public class SanPham {
    int id_sanPham;
    String anhSP;
    String tenSP;
    int giaTienSP;
    int id_Loai;
    String moTaSP;
    int soLuongSP;

    public SanPham() {
    }

    public SanPham(int id_sanPham, String anhSP, String tenSP, int giaTienSP, int id_Loai, String moTaSP, int soLuongSP) {
        this.id_sanPham = id_sanPham;
        this.anhSP = anhSP;
        this.tenSP = tenSP;
        this.giaTienSP = giaTienSP;
        this.id_Loai = id_Loai;
        this.moTaSP = moTaSP;
        this.soLuongSP = soLuongSP;
    }

    public int getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(int id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaTienSP() {
        return giaTienSP;
    }

    public void setGiaTienSP(int giaTienSP) {
        this.giaTienSP = giaTienSP;
    }

    public int getId_Loai() {
        return id_Loai;
    }

    public void setId_Loai(int id_Loai) {
        this.id_Loai = id_Loai;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }
}
