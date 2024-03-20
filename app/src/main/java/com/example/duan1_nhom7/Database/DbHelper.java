package com.example.duan1_nhom7.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="duan1";

    public static final int DB_VERSION=2;
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng TheLoai
        String createTableTheLoai = "CREATE TABLE THELOAI(id_Loai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT, imgLoaiSP TEXT);";
        db.execSQL(createTableTheLoai);
        db.execSQL("INSERT INTO THELOAI VALUES(1, 'Apple', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/logo-apple-1.webp?alt=media&token=366883f4-a81b-49c0-a27e-bb82a99d6c89'), " +
                "(2, 'Xiaomi', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/xiaomi.jpg?alt=media&token=25c8c84f-ef12-4998-86f2-b768c593e92f'), " +
                "(3, 'SamSung', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/samsung.jpg?alt=media&token=bdab3eef-6c2e-4fe0-bed3-f949d2512f26'), " +
                "(4, 'Lenovo', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/Lenovo_Global_Corporate_Logo.png?alt=media&token=759c0222-6f8b-4862-a60e-5bee6694d9c4'), " +
                "(5, 'Huawei', 'https://firebasestorage.googleapis.com/v0/b/testfirebase-c02f8.appspot.com/o/hoawei.png?alt=media&token=15d0a466-26d9-4553-87f6-1d9ebb574fd7');");

        // SanPham
        String createTableSanPham = ("CREATE TABLE SanPham(\n" +
                "id_sanPham INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "anhSP TEXT,\n" +
                "tenSP TEXT,\n" +
                "giaTienSP Integer,\n" +
                "id_Loai INTEGER REFERENCES THELOAI(id_Loai),\n" +
                "moTaSP TEXT,\n" +"soLuongSP INTEGER\n"+

                ");");
        db.execSQL(createTableSanPham);
        db.execSQL("INSERT INTO SanPham VALUES(1, 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-black-1.jpg','iphone 15 128gb',20000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50),(2, 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-black-1.jpg','iphone 15 promax 128gb',30000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropLoaiSP = "drop table if exists THELOAI";
        db.execSQL(dropLoaiSP);
        String dropSanPham = "drop table if exists SanPham";
        db.execSQL(dropSanPham);

        onCreate(db);
    }
}