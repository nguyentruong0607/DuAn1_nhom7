package com.example.duan1_nhom7.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="duan1";

    public static final int DB_VERSION=1;
    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTheLoai = "CREATE TABLE THELOAI(id_Loai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT);";
        db.execSQL(createTableTheLoai);
        db.execSQL("INSERT INTO THELOAI VALUES(1, 'Iphone'), (2, 'SamSung'), (3, 'Xiaomi');");
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
    }
}
