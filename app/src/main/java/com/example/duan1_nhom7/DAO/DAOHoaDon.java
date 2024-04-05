package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom7.Database.DbHelper;
import com.example.duan1_nhom7.DTO.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DAOHoaDon {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DAOHoaDon(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Hàm insert hóa đơn
    public long insertHoaDon(HoaDon hoaDon) {

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ngayMua", hoaDon.getNgayMua());
        values.put("gia", hoaDon.getGia());
        values.put("soLuong", hoaDon.getSoLuong());

        // Thực hiện thao tác insert vào bảng HoaDon
        long result = -1;
        try {
            result = db.insert("HoaDon", null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Đóng kết nối và trả về kết quả
        db.close();
        return result;
    }




    public int calculateRevenueByTimePeriod(String startDate, String endDate) {
        int totalRevenue = 0;

        db = dbHelper.getReadableDatabase();

        // Định dạng ngày tháng từ chuỗi sang đối tượng Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = sdf.parse(startDate);
            endDateTime = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Sử dụng câu truy vấn SQL để tính tổng doanh thu theo khoảng thời gian
        String query = "SELECT SUM(gia) AS total FROM HoaDon WHERE ngayMua BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{sdf.format(startDateTime), sdf.format(endDateTime)});

        // Kiểm tra cursor và tính tổng doanh thu nếu có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            totalRevenue = cursor.getInt(cursor.getColumnIndex("total"));
            cursor.close();
        }

        // Đóng kết nối và trả về tổng doanh thu
        db.close();
        return totalRevenue;
    }

    public int calculateQuantityByTimePeriod(String startDate, String endDate) {
        int totalQuantity = 0;
        db = dbHelper.getReadableDatabase();

        // Định dạng ngày tháng từ chuỗi sang đối tượng Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = sdf.parse(startDate);
            endDateTime = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Sử dụng câu truy vấn SQL để tính tổng số lượng theo khoảng thời gian
        String query = "SELECT SUM(soLuong) AS totalQuantity FROM HoaDon WHERE ngayMua BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{sdf.format(startDateTime), sdf.format(endDateTime)});

        // Kiểm tra cursor và tính tổng số lượng nếu có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            totalQuantity = cursor.getInt(cursor.getColumnIndex("totalQuantity"));
            cursor.close();
        }

        // Đóng kết nối và trả về tổng số lượng
        db.close();
        return totalQuantity;
    }



}
