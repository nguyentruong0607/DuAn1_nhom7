package com.example.duan1_nhom7.DTO;

public class User {
    private int id_user;
    private String ten_user;
    private String password;
    private String sodienthoai;
    private String diaChi;
    private String fullname;
    private int id_chucvu;


    public User() {
    }

    public User(String ten_user, String password, String sodienthoai, String diaChi, String fullname, int id_chucvu) {
        this.ten_user = ten_user;
        this.password = password;
        this.sodienthoai = sodienthoai;
        this.diaChi = diaChi;
        this.fullname = fullname;
        this.id_chucvu = id_chucvu;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTen_user() {
        return ten_user;
    }

    public void setTen_user(String ten_user) {
        this.ten_user = ten_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId_chucvu() {
        return id_chucvu;
    }

    public void setId_chucvu(int id_chucvu) {
        this.id_chucvu = id_chucvu;
    }
}


