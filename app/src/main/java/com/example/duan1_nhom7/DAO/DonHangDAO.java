package com.example.duan1_nhom7.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DonHangDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void insertDonHang(DonHang donHang) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_sanPham", donHang.getId_sanPham());
        values.put("id_user", donHang.getId_user());
        values.put("tenSP", donHang.getTenSP());
        values.put("ngayMua", donHang.getNgayMua());
        values.put("soLuong", donHang.getSoLuong());
        values.put("gia", donHang.getGia());
        values.put("status", donHang.getStatus());
        values.put(("image"), donHang.getImage());
        values.put(("mau"), donHang.getMau());
        values.put((("pttt")), donHang.getPttt());
        // Thêm các trường còn lại tương ứng vào values

        // Insert vào cơ sở dữ liệu
        db.insert("DonHang", null, values);

        // Đóng kết nối tới cơ sở dữ liệu
        db.close();
    }

    public List<DonHang> getDonHangByStatus(String status) {
        List<DonHang> donHangList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String[] columns = {"id_donHang", "id_sanPham", "id_user", "tenSP", "ngayMua", "soLuong", "gia", "status", "image", "mau", "pttt"};

        String selection = "status=?";
        String[] selectionArgs = {status};

        Cursor cursor = null;
        try {
            cursor = db.query("DonHang", columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id_donHang"));
                    int id_sanPham = cursor.getInt(cursor.getColumnIndex("id_sanPham"));
                    int id_user = cursor.getInt(cursor.getColumnIndex("id_user"));
                    String tenSP = cursor.getString(cursor.getColumnIndex("tenSP"));
                    String ngayMua = cursor.getString(cursor.getColumnIndex("ngayMua"));
                    int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
                    int gia = cursor.getInt(cursor.getColumnIndex("gia"));
                    String statusDB = cursor.getString(cursor.getColumnIndex("status"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String mau = cursor.getString(cursor.getColumnIndex("mau"));
                    String pttt= cursor.getString(cursor.getColumnIndex("pttt"));

                    DonHang donHang = new DonHang(id, id_sanPham, id_user, tenSP, ngayMua, soLuong, gia, statusDB, image, mau, pttt);
                    donHangList.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DonHangDAO", "Error while trying to get don hang by status: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }
        return donHangList;
    }

    public int updateDonHangStatus(DonHang donHang) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", donHang.getStatus());

        String selection = "id_donHang=?";
        String[] selectionArgs = {String.valueOf(donHang.getId_donHang())};

        int rowsAffected = db.update("DonHang", values, selection, selectionArgs);
        db.close();
        return rowsAffected;
    }





}
