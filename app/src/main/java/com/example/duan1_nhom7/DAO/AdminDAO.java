package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_nhom7.Database.DbHelper;

public class AdminDAO {
    private SQLiteDatabase database;
    DbHelper dbHelper;

    public AdminDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }
    @SuppressLint("Range")
    public int getQuyen(String name) {
        int quyen = 0;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            // Thực thi truy vấn
            if (database != null) {
                String query = "SELECT quyen FROM Admin WHERE name = ?";
                cursor = database.rawQuery(query, new String[]{name});
                if (cursor.moveToFirst()) {
                    quyen = cursor.getInt(cursor.getColumnIndex("quyen"));
                }
            }
        } catch (Exception ex) {
            Log.e("READ_ERROR", ex.getMessage());
        }
        return quyen;
    }
    public boolean checkDangNhap(String name, String pass) {
        boolean success = false;
        Cursor cursor = null;
            if (database != null) {
                String query = "SELECT name,pass FROM Admin WHERE name = ? AND pass = ?";
                cursor = database.rawQuery(query, new String[]{name, pass});
                if (cursor.moveToFirst()) {
                    success = true;
                }
            }
        return success;
    }


}
