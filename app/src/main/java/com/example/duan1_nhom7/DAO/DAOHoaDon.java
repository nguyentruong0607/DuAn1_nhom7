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
import java.util.Calendar;
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
    @SuppressLint("Range")
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

    @SuppressLint("Range")
    public int getTotalRevenueByDateRangeAndStatus(String startDate, String endDate, String status) {
        int totalRevenue = 0;
        db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            // Chuyển đổi ngày bắt đầu và ngày kết thúc sang định dạng ngày tháng
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            // Chuyển đổi ngày kết thúc sang ngày tiếp theo để bao gồm cả ngày đó
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DATE, 1);
            end = calendar.getTime();

            String query = "SELECT SUM(tongTien) AS total FROM HoaDon WHERE ngayMua BETWEEN ? AND ? AND status = ?";
            Cursor cursor = db.rawQuery(query, new String[]{dateFormat.format(start), dateFormat.format(end), status});

            if (cursor != null && cursor.moveToFirst()) {
                totalRevenue = cursor.getInt(cursor.getColumnIndex("total"));
                cursor.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.close();
        return totalRevenue;
    }

    @SuppressLint("Range")
    public int getTotalOrdersByDateRangeAndStatus(String startDate, String endDate, String status) {
        int totalOrders = 0;
        db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            // Chuyển đổi ngày bắt đầu và ngày kết thúc sang định dạng ngày tháng
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            // Chuyển đổi ngày kết thúc sang ngày tiếp theo để bao gồm cả ngày đó
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DATE, 1);
            end = calendar.getTime();

            String query = "SELECT SUM(soLuong) AS total FROM DonHangChiTiet " +
                    "JOIN HoaDon ON DonHangChiTiet.id_HoaDon = HoaDon.id_HoaDon " +
                    "WHERE HoaDon.ngayMua BETWEEN ? AND ? AND HoaDon.status = ?";
            Cursor cursor = db.rawQuery(query, new String[]{dateFormat.format(start), dateFormat.format(end), status});

            if (cursor != null && cursor.moveToFirst()) {
                totalOrders = cursor.getInt(cursor.getColumnIndex("total"));
                cursor.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        db.close();
        return totalOrders;
    }

}




