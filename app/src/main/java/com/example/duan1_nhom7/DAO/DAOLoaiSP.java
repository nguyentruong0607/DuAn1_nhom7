package com.example.duan1_nhom7.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom7.Database.DbHelper;
import com.example.duan1_nhom7.DTO.LoaiSP;

import java.util.ArrayList;
import java.util.List;

public class DAOLoaiSP {
    private DbHelper dbHelper;

    public DAOLoaiSP(Context context) {
        dbHelper = new DbHelper(context);
    }

    public List<LoaiSP> getAllLoaiSP() {
        List<LoaiSP> loaiSPList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THELOAI", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id_Loai"));
                String tenLoaiSP = cursor.getString(cursor.getColumnIndex("tenLoai"));
                String imgLoaiSP = cursor.getString(cursor.getColumnIndex("imgLoaiSP"));
                loaiSPList.add(new LoaiSP(id, tenLoaiSP, imgLoaiSP));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return loaiSPList;
    }

    public void addLoaiSP(String tenLoaiSP, String imgLoaiSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoaiSP);
        values.put("imgLoaiSP", imgLoaiSP);
        db.insert("THELOAI", null, values);
        db.close();
    }

    public void deleteLoaiSP(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("THELOAI", "id_Loai = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateLoaiSP(int id, String newName, String newLink) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", newName);
        values.put("imgLoaiSP", newLink);
        db.update("THELOAI", values, "id_Loai = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}