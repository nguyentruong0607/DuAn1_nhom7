package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_nhom7.Database.DbHelper;
import com.example.duan1_nhom7.DTO.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOHoaDon {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DAOHoaDon(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Hàm insert hóa đơn
    public int insertHoaDonAndGetId(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();

        values.put("id_user", hoaDon.getId_user());
        values.put("ngayMua", hoaDon.getNgayMua());
        values.put("tongTien", hoaDon.getTongTien());
        values.put("pttt", hoaDon.getPttt());
        values.put("status", hoaDon.getStatus());
        values.put("nameUser", hoaDon.getNameUser());
        values.put("location", hoaDon.getLocation());
        values.put("phone", hoaDon.getPhone());

        // Thêm hóa đơn vào cơ sở dữ liệu
        long idHoaDon = db.insert("HoaDon", null, values);

        db.close();

        // Trả về id_HoaDon
        return (int) idHoaDon;
    }

    public HoaDon getHoaDonById(int id_HoaDon) {
        HoaDon hoaDon = null;
        db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM HoaDon WHERE id_HoaDon = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_HoaDon)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id_HoaDon"));
            int id_user = cursor.getInt(cursor.getColumnIndex("id_user"));
            String ngayMua = cursor.getString(cursor.getColumnIndex("ngayMua"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));
            String pttt = cursor.getString(cursor.getColumnIndex("pttt"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            String nameUser = cursor.getString(cursor.getColumnIndex("nameUser"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));

                hoaDon = new HoaDon(id, id_user, ngayMua, tongTien, pttt, status, nameUser, location, phone);

            cursor.close();
        }

        db.close();
        return hoaDon;
    }



    public int updateHoaDonStatus(int id_HoaDon, String newStatus) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        // Tiến hành cập nhật trạng thái của hóa đơn
        int rowsAffected = db.update("HoaDon", values, "id_HoaDon = ?", new String[]{String.valueOf(id_HoaDon)});

        db.close();

        return rowsAffected;
    }

    public int updateHoaDonStatusAndCancelDate(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("status", hoaDon.getStatus());
        values.put("ngayMua", hoaDon.getNgayMua());

        db = dbHelper.getWritableDatabase();
        int rowsAffected = db.update("HoaDon", values, "id_HoaDon = ?", new String[]{String.valueOf(hoaDon.getId_HoaDon())});
        db.close();

        return rowsAffected;
    }



}
