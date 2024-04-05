package com.example.duan1_nhom7.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.Database.DbHelper;

import java.util.ArrayList;

public class GioHangDAO {
    private SQLiteDatabase database;
    DbHelper dbHelper;
    Context context;
    //    Khởi tạo Constructor


    public GioHangDAO(Context context) {
        dbHelper=new DbHelper(context);
        database=dbHelper.getWritableDatabase();
        database=dbHelper.getReadableDatabase();
    }

    //    Thêm sản phẩm vào giỏ hàng
    public boolean addGiohang(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("id_gioHang", gioHang.getId_gioHang());
        values.put("id_sanPham", gioHang.getId_sanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Mau",gioHang.getMau());
        values.put("DonGia", gioHang.getDonGia());
        long check = database.insert("GioHang", null, values);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //    Lấy danh sách sản phẩm có trong giỏ hàng
    public ArrayList<GioHang> getGioHang(){
        ArrayList<GioHang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT GioHang.id_gioHang, GioHang.id_sanPham, SanPham.anhSP, SanPham.tenSP, GioHang.SoLuong, GioHang.mau, GioHang.dongia FROM GioHang, SanPham WHERE GioHang.id_sanPham = SanPham.id_sanPham", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int id_gioHang = cursor.getInt(0);
                int id_sanPham = cursor.getInt(1);
                String anhSP = cursor.getString(2);
                String tenSP = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                String mau=cursor.getString(5);
                int donGia = cursor.getInt(6);
                list.add(new GioHang(id_gioHang, id_sanPham, anhSP, tenSP, soLuong,mau, donGia));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    //    Sửa số lượng sản phẩm
    public boolean updateGioHang(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("id_gioHang", gioHang.getId_gioHang());
        values.put("id_sanPham", gioHang.getId_sanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Mau",gioHang.getMau());
        values.put("DonGia", gioHang.getDonGia());
        long check = database.update("GioHang", values, "id_sanPham = ? ", new String[]{String.valueOf(gioHang.getId_sanPham())});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }



    //    Check tồn tại SP
    public ArrayList<GioHang> checkValidGioHang(GioHang gioHang){
        ArrayList<GioHang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT GioHang.id_gioHang, GioHang.id_sanPham, SanPham.anhSP, SanPham.tenSP, GioHang.SoLuong, GioHang.mau, GioHang.dongia FROM GioHang, SanPham WHERE GioHang.id_sanPham = SanPham.id_sanPham AND SanPham.id_sanPham = ? ", new String[]{String.valueOf(gioHang.getId_sanPham())});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int id_gioHang = cursor.getInt(0);
                int id_sanPham = cursor.getInt(1);
                String anhSP = cursor.getString(2);
                String tenSP = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                String mau=cursor.getString(5);
                int donGia = cursor.getInt(6);
                list.add(new GioHang(id_gioHang, id_sanPham, anhSP, tenSP, soLuong,mau, donGia));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    //    Xóa SP khỏi giỏ hàng
    public boolean deleteGiohang(GioHang gioHang){
        long check = database.delete("GioHang", "id_sanPham = ?", new String[]{String.valueOf(gioHang.getId_sanPham())});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //    Tính tổng tiền thanh toán từ giỏ hàng
    public double tongTienGiohang(){
        double tongTien = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(GioHang.soluong * GioHang.dongia) as TongTien FROM GioHang", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            tongTien = cursor.getDouble(0);
        }
        return tongTien;
    }

    public boolean deleteAllGioHang() {
        long check = database.delete("GioHang", null, null);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }




}
