package com.example.duan1_nhom7.DTO;

public class User {
     int id_user;
     String ten_user;
     String password;
     String sodienthoai;
     String diaChi;
     String fullname;
     int Chucvu;




    public User(String ten_user, String password, String sodienthoai, String diaChi, String fullname) {
        this.ten_user = ten_user;
        this.password = password;
        this.sodienthoai = sodienthoai;
        this.diaChi = diaChi;
        this.fullname = fullname;

    }

    public User(int id_user, String ten_user, String password, String sodienthoai, String diaChi, String fullname, int Chucvu) {
        this.id_user = id_user;
        this.ten_user = ten_user;
        this.password = password;
        this.sodienthoai = sodienthoai;
        this.diaChi = diaChi;
        this.fullname = fullname;
        this.Chucvu = Chucvu;
    }

    public User() {

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

    public int getChucvu() {
        return Chucvu;
    }

    public void setChucvu(int chucvu) {
        Chucvu = chucvu;
    }
}