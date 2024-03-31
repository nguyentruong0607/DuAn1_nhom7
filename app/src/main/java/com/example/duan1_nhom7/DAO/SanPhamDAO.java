package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    private SQLiteDatabase database;
    DbHelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

    public void insertData(String anhSP, String tenSP, int giaTienSP, int id_Loai, String moTaSP,  int SoLuongSP, String ngaySP) {
        String sql = "INSERT INTO SanPham VALUES (NULL, ?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, anhSP);
        statement.bindString(2, tenSP);
        statement.bindString(3, giaTienSP+"");
        statement.bindLong(4, id_Loai);
        statement.bindString(5, moTaSP);
        statement.bindString(6,SoLuongSP+"");
        statement.bindString(7,ngaySP);
        statement.executeInsert();
    }


    public int updateSanPham(SanPham sanPham){
        ContentValues values = new ContentValues();
        values.put("id_Loai",sanPham.getId_Loai());
        values.put("tenSP", sanPham.getTenSP());
        values.put("anhSP",sanPham.getAnhSP());
        values.put("giaTienSP",sanPham.getGiaTienSP()+"");
        values.put("moTaSP",sanPham.getMoTaSP());
        values.put("SoLuongSP",sanPham.getSoLuongSP()+"");
        values.put("ngaySP",sanPham.getNgaySP());

       String [] tham_so=new String[]{sanPham.getId_sanPham()+""};
       return database.update("SanPham",values,"id_sanPham=?",tham_so);

    }
    public int deleteData(SanPham sanPham) {

        return database.delete("SanPham","id_sanPham=?",new String[]{sanPham.getId_sanPham()+""});
    }

    public ArrayList<SanPham> getAllProduct(int rdoCheck) {
        String sql = null;
        if (rdoCheck == 0) {
            sql = "SELECT * FROM SanPham";
        }
        if (rdoCheck == 1) {
            sql = "SELECT * FROM SanPham ORDER BY ngaySP ASC";
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
    @SuppressLint("Range")
    public List<SanPham> getProductsByCategoryId(int Id_Loai) {
        List<SanPham> productList = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM SanPham WHERE id_Loai = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(Id_Loai)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                     int id = cursor.getInt(cursor.getColumnIndex("id_sanPham"));
                    String anhSP = cursor.getString(cursor.getColumnIndex("anhSP"));
                    String tenSP = cursor.getString(cursor.getColumnIndex("tenSP"));
                    int giaTienSP = cursor.getInt(cursor.getColumnIndex("giaTienSP"));
                    String moTaSP = cursor.getString(cursor.getColumnIndex("moTaSP"));
                    int soLuongSP = cursor.getInt(cursor.getColumnIndex("soLuongSP"));
                    String ngaySP = cursor.getString(cursor.getColumnIndex("ngaySP"));

                    SanPham sanPham = new SanPham(id, anhSP, tenSP, giaTienSP, Id_Loai, moTaSP, soLuongSP, ngaySP);
                    productList.add(sanPham);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return productList;
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
            String ngaySP=cursor.getString(7);
            list.add(new SanPham(id, anhSP, tenSP, giaTienSP, id_Loai, moTaSP, soLuongSP,ngaySP));
        }
        return list;
    }

    public String getMoTaSPById(int idSanPham) {
        String moTaSP = null;
        Cursor cursor = null;

        try {
            String query = "SELECT moTaSP FROM SanPham WHERE id_sanPham = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(idSanPham)});

            if (cursor != null && cursor.moveToFirst()) {
                moTaSP = cursor.getString(cursor.getColumnIndex("moTaSP"));

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return moTaSP;
    }


}
