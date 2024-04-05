package com.example.duan1_nhom7.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom7.Database.DbHelper;
import com.example.duan1_nhom7.DTO.HoaDon;

public class DAOHoaDon {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DAOHoaDon(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Hàm insert hóa đơn
    public long insertHoaDon(HoaDon hoaDon) {
        // Mở kết nối đến cơ sở dữ liệu để thực hiện thao tác insert
        db = dbHelper.getWritableDatabase();

        // Tạo đối tượng ContentValues để lưu trữ dữ liệu
        ContentValues values = new ContentValues();
        values.put("ngayMua", hoaDon.getNgayMua());
        values.put("gia", hoaDon.getGia());

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
}
