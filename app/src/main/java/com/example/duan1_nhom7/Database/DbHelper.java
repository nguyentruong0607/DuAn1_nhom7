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
                "moTaSP TEXT,\n" +"soLuongSP INTEGER,\n" +" ngaySP TEXT\n"+

                ");");
        db.execSQL(createTableSanPham);
        db.execSQL("INSERT INTO SanPham VALUES(1, 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-black-1.jpg','iphone 15 128gb',20000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50,'03/03/30224'),"+
                "(2, 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-black-1.jpg','iphone 15 promax 128gb',30000000,1,'Chế tác bộ bộ khung viền từ chất liệu Titanium cứng cáp',50,'21/03/2024')," +
                " (3, 'https://cdn.tgdd.vn/Products/Images/42/322526/xiaomi-14-den-5.jpg','Xiaomi 14 256gb',18000000,2,'Xiaomi 14 256GB chiếc flagship hứa hẹn mang đến những trải nghiệm đột phá cho người dùng. Với thiết kế sang trọng, màn hình hiển thị đỉnh cao, hiệu năng mạnh mẽ, camera chụp ảnh ấn tượng và thời lượng pin dài. Máy là một lựa chọn hoàn hảo cho những ai đang tìm kiếm một chiếc điện thoại cao cấp trong phân khúc giá tầm trung.',50,'27/03/2024')");
        //Gio hang
        String tableGioHang = "CREATE Table GioHang (\n" +
                "id_gioHang INTEGER,\n" +
                "id_sanPham INTEGER REFERENCES SanPham(id_sanPham),\n" +
                "SoLuong INTEGER,\n" +
                "Mau TEXT,\n"+
                "DonGia DOUBLE\n" +
                ");";
        db.execSQL(tableGioHang);
        //admin
        String createTableAdmin = "create table Admin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "pass TEXT NOT NULL," +
                "quyen INTEGER )";
        db.execSQL(createTableAdmin);
        db.execSQL("INSERT INTO Admin VALUES (1,'admin','admin',1)");

        // User
        String createTableUser = "create table User (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten_user TEXT NOT NULL, " +
                "password TEXT NOT NULL," +
                "sodienthoai TEXT NOT NULL," +
                "diaChi TEXT NOT NULL," +
                "fullname TEXT NOT NULL)";
        db.execSQL(createTableUser);

        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "id_HoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER REFERENCES User(id_user), " +
                "ngayMua TEXT, " +
                "tongTien INTEGER, " +
                "pttt TEXT, " +
                "status TEXT, " +
                "nameUser TEXT, " +
                "location TEXT, " +
                "phone TEXT" +
                ");";
        db.execSQL(createTableHoaDon);

        db.execSQL("INSERT INTO HoaDon (id_HoaDon, id_user, ngayMua, tongTien, pttt, status, nameUser, location, phone) " +
                "VALUES (1001, 1, '2024/04/11', 500000, 'COD', '1', 'John Doe', '123 Street, City', '123456789'), " +
                "(1002, 1, '2024/04/12', 750000, 'Credit card', '1', 'Alice Smith', '456 Avenue, Town', '987654321'), " +
                "(1003, 1, '2024/04/13', 600000, 'COD', '1', 'Bob Johnson', '789 Road, Village', '111222333'), " +
                "(1005, 2, '2024/04/13', 600000, 'COD', '1', 'Bob Johnson', '789 Road, Village', '111222333'), " +
                "(1006, 3, '2024/04/13', 600000, 'COD', '1', 'Bob Johnson', '789 Road, Village', '111222333'), " +
                "(1007, 4, '2024/04/13', 600000, 'COD', '1', 'Bob Johnson', '789 Road, Village', '111222333'), " +
                "(1004, 1, '2024/04/14', 900000, 'Credit card', '1', 'Eva Brown', '987 Lane, Hamlet', '444555666')");



        String createTableDonHangChiTiet = "CREATE TABLE DonHangChiTiet(" +
                "id_donHangChiTiet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_HoaDon INTEGER REFERENCES HoaDon(id_HoaDon), " +
                "id_sanPham INTEGER REFERENCES SanPham(id_sanPham), " +
                "soLuong INTEGER, " +
                "giaBan INTEGER, " +
                "mau TEXT);";
        db.execSQL(createTableDonHangChiTiet);
        db.execSQL("INSERT INTO DonHangChiTiet (id_donHangChiTiet, id_HoaDon, id_sanPham, soLuong, giaBan, mau) " +
                "VALUES (1, 1001, 1, 2, 100000, 'red'), " +
                "(2, 1002, 2, 1, 200000, 'blue'), " +
                "(3,  1003, 3, 3,  150000, 'green'), " +
                "(5,  1005, 3, 3,  150000, 'green'), " +
                "(6,  1005, 2, 3,  150000, 'purple'), " +
                "(7,  1006, 3, 3,  150000, 'green'), " +
                "(8,  1006, 2, 4,  150000, 'green'), " +
                "(9,  1006, 1, 7,  150000, 'green'), " +
                "(10,  1007, 3, 3,  150000, 'green'), " +
                "(4, 1004, 4, 2, 180000, 'yellow')");


    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropLoaiSP = "drop table if exists THELOAI";
        db.execSQL(dropLoaiSP);
        String dropSanPham = "drop table if exists SanPham";
        db.execSQL(dropSanPham);
        String dropGioHang = "drop table if exists GioHang";
        db.execSQL(dropGioHang);
        onCreate(db);
    }
}