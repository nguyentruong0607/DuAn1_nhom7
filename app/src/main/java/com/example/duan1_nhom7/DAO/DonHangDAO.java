package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.ProductInfo;
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
        values.put("id_HoaDon", donHang.getId_HoaDon());
        values.put("id_sanPham", donHang.getId_sanPham());
        values.put("soLuong", donHang.getSoLuong());
        values.put("giaBan", donHang.getGiaBan());
        values.put("mau", donHang.getMau());

        db.insert("DonHangChiTiet", null, values);

        db.close();
    }

    @SuppressLint("Range")
    public List<DonHang> getDonHangsByUserAndStatus(int idUser, String status) {
        List<DonHang> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT HoaDon.*, DonHangChiTiet.id_sanPham, DonHangChiTiet.soLuong, DonHangChiTiet.giaBan, DonHangChiTiet.mau, " +
                "SanPham.tenSP, SanPham.anhSP " +
                "FROM HoaDon " +
                "JOIN DonHangChiTiet ON HoaDon.id_HoaDon = DonHangChiTiet.id_HoaDon " +
                "JOIN SanPham ON DonHangChiTiet.id_sanPham = SanPham.id_sanPham " +
                "WHERE HoaDon.id_user = ? AND HoaDon.status = ? " +
                "GROUP BY HoaDon.id_HoaDon " +
                "ORDER BY DonHangChiTiet.id_donHangChiTiet ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser), status});

        while (cursor.moveToNext()) {
            DonHang donHang = new DonHang();
            donHang.setId_HoaDon(cursor.getInt(cursor.getColumnIndexOrThrow("id_HoaDon")));
            donHang.setId_sanPham(cursor.getInt(cursor.getColumnIndexOrThrow("id_sanPham")));
            donHang.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("soLuong")));
            donHang.setGiaBan(cursor.getInt(cursor.getColumnIndexOrThrow("giaBan")));
            donHang.setMau(cursor.getString(cursor.getColumnIndexOrThrow("mau")));

            // Thiết lập thông tin sản phẩm
            ProductInfo productInfo = new ProductInfo(
                    cursor.getString(cursor.getColumnIndexOrThrow("tenSP")),
                    cursor.getString(cursor.getColumnIndexOrThrow("anhSP"))
            );
            donHang.setProductInfo(productInfo);

            list.add(donHang);
        }

        cursor.close();
        db.close();
        return list;
    }


    public List<DonHang> getDonHangsByIdHoaDon(int idHoaDon) {
        List<DonHang> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM DonHangChiTiet WHERE id_HoaDon = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idHoaDon)});

        while (cursor.moveToNext()) {
            DonHang donHang = new DonHang();
            donHang.setId_HoaDon(cursor.getInt(cursor.getColumnIndexOrThrow("id_HoaDon")));
            donHang.setId_sanPham(cursor.getInt(cursor.getColumnIndexOrThrow("id_sanPham")));
            donHang.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("soLuong")));
            donHang.setGiaBan(cursor.getInt(cursor.getColumnIndexOrThrow("giaBan")));
            donHang.setMau(cursor.getString(cursor.getColumnIndexOrThrow("mau")));

            list.add(donHang);
        }

        cursor.close();
        db.close();
        return list;
    }


    public List<DonHang> getDonHangsByStatus(String status) {
        List<DonHang> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String query = "SELECT DISTINCT HoaDon.*, DonHangChiTiet.id_sanPham, DonHangChiTiet.soLuong, DonHangChiTiet.giaBan, DonHangChiTiet.mau, " +
                "SanPham.tenSP, SanPham.anhSP " +
                "FROM HoaDon " +
                "JOIN DonHangChiTiet ON HoaDon.id_HoaDon = DonHangChiTiet.id_HoaDon " +
                "JOIN SanPham ON DonHangChiTiet.id_sanPham = SanPham.id_sanPham " +
                "WHERE HoaDon.status = ? " +
                "GROUP BY HoaDon.id_HoaDon " +
                "ORDER BY DonHangChiTiet.id_donHangChiTiet ASC";

        Cursor cursor = db.rawQuery(query, new String[]{status});

        while (cursor.moveToNext()) {
            DonHang donHang = new DonHang();
            donHang.setId_HoaDon(cursor.getInt(cursor.getColumnIndexOrThrow("id_HoaDon")));
            donHang.setId_sanPham(cursor.getInt(cursor.getColumnIndexOrThrow("id_sanPham")));
            donHang.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow("soLuong")));
            donHang.setGiaBan(cursor.getInt(cursor.getColumnIndexOrThrow("giaBan")));
            donHang.setMau(cursor.getString(cursor.getColumnIndexOrThrow("mau")));

            // Thiết lập thông tin sản phẩm
            ProductInfo productInfo = new ProductInfo(
                    cursor.getString(cursor.getColumnIndexOrThrow("tenSP")),
                    cursor.getString(cursor.getColumnIndexOrThrow("anhSP"))
            );
            donHang.setProductInfo(productInfo);

            list.add(donHang);
        }

        cursor.close();
        db.close();
        return list;
    }




}
