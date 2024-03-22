package com.example.duan1_nhom7.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.Database.DbHelper;

import java.util.ArrayList;

public class SanPhamDAO {
    private SQLiteDatabase database;
    DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

    public void insertData(String anhSP, String tenSP, int giaTienSP, int id_Loai, String moTaSP,  int SoLuongSP) {
        String sql = "INSERT INTO SanPham VALUES (NULL, ?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, anhSP);
        statement.bindString(2, tenSP);
        statement.bindString(3, giaTienSP+"");
        statement.bindLong(4, id_Loai);
        statement.bindString(5, moTaSP);
        statement.bindString(6,SoLuongSP+"");
        statement.executeInsert();
    }

    public void updateSanPham(String anhSP, String tenSP, int giaTienSP, int id_Loai, String moTaSP, int id_sanPham, int SoLuongSP) {
        String sql = "UPDATE SanPham SET anhSP = ?, tenSP = ?, giaTienSP = ?, id_Loai = ?, moTaSP = ?,SoLuongSP=? WHERE MaSanPham =?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, anhSP);
        statement.bindString(2, tenSP);
        statement.bindString(3, giaTienSP+"");
        statement.bindLong(4, id_Loai);
        statement.bindString(5, moTaSP);
        statement.bindString(6, SoLuongSP+"");
        statement.bindString(7, id_sanPham+"");
        statement.execute();
        database.close();
    }

    public void deleteData(int id_sanPham) {

        String sql = "DELETE FROM SanPham WHERE id_sanPham = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id_sanPham+"");
        statement.execute();
        database.close();
    }

    public ArrayList<SanPham> getAllProduct(int rdoCheck) {
        String sql = null;
        if (rdoCheck == 0) {
            sql = "SELECT * FROM SanPham";
        }
        if (rdoCheck == 1) {
            sql = "SELECT * FROM SanPham ORDER BY giaTienSP ASC";
        }
        if (rdoCheck == 2) {
            sql = "SELECT * FROM SanPham ORDER BY id_Loai ASC";
        }
        return getData(sql);
    }

    public SanPham getSPofMaSP(int maSp) {
        String sql = "Select * FROM SanPham WHERE SanPham.id_sanPham = ?";
        ArrayList<SanPham> list = getData(sql, String.valueOf(maSp));
        return list.get(0);
    }

    public ArrayList<SanPham> getSPofTL(int id_Loai) {
        String sql = "Select * FROM SanPham WHERE SanPham.id_Loai = ? ";
        ArrayList<SanPham> list = getData(sql, String.valueOf(id_Loai));
        return list;
    }

    public ArrayList<SanPham> getData(String sql, String... selectionAGrs) {
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionAGrs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String anhSP = cursor.getString(1);
            String tenSP = cursor.getString(2);
            int giaTienSP  = cursor.getInt(3);
            int id_Loai = cursor.getInt(4);
            String moTaSP = cursor.getString(5);
            int soLuongSP=cursor.getInt(6);
            list.add(new SanPham(id, anhSP, tenSP, giaTienSP, id_Loai, moTaSP, soLuongSP));
        }
        return list;
    }

}
