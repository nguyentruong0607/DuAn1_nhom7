package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Database.DbHelper;


import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;

    DbHelper dbHelper;
    public UserDAO(Context context) {
      dbHelper = new DbHelper(context);
        db= dbHelper.getWritableDatabase();
    }
    public long insert( User tt) {
        ContentValues value=new ContentValues();
        value.put("ten_user",tt.getTen_user());
        value.put("password",tt.getPassword());
        value.put("sodienthoai",tt.getSodienthoai());
        value.put("diaChi",tt.getDiaChi());
        value.put("fullname",tt.getFullname());
        value.put("id_chucvu",tt.getId_chucvu());

        return db.insert("User",null,value);
    }
//    public int updatePass(ThuThu tt) {
//        ContentValues value=new ContentValues();
//        value.put("hoTen",tt.getHoTen());
//        value.put("matKhau",tt.getMatKhau());
//        return db.update("ThuThu",value,"maTT=?",new String[]{String.valueOf(tt.getMaTT())});
//    }
//    public int delete(String id) {
//        return db.delete("ThuThu","maTT=?",new String[]{id});
//    }
//    @SuppressLint("Range")
//    public List<ThuThu> getData() {
//        String sql = "SELECT * FROM ThuThu";
//        List<ThuThu> list = new ArrayList<ThuThu>();
//        Cursor cs=db.rawQuery(sql,null);//rawquery để truy vấn dữ liệu
//        cs.moveToFirst();//đưa con trỏ về dòng đầu tiên
//        while (cs.moveToNext()){
//          ThuThu tt = new ThuThu();
//          tt.setMaTT(cs.getString(cs.getColumnIndex("maTT")));
//          tt.setHoTen(cs.getString(cs.getColumnIndex("hoTen")));
//          tt.setMatKhau(cs.getString(cs.getColumnIndex("matKhau")));
//          list.add(tt);
//        }
//        return list;
//    }
////    public List<ThuThu> getAll(){
////        String sql = "SELECT * FROM ThuThu";
////        return getData(sql);
////    }
////    // lay tv theo ma
////    public ThuThu getID(String id){
////        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
////        List<ThuThu>list = getData(id,sql);
////        return list.get(0);
////    }
    //check dang nhap;
    public boolean checkLogin(String ten_user , String passwords) {

        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE ten_user=? AND password=?", new String[]{ten_user, passwords});
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
//    }
//
//    public boolean capnhatmk(String username,String oldpass,String newpass){
//        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?",new String[]{username,oldpass});
//                if(cursor.getCount()>0){
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put("matKhau",newpass);
//            long check =  db.update("ThuThu",contentValues,"maTT=?",new String[]{username});
//            if (check==-1)
//                return false;
//            return true;
//                }
//                return false;
//    }
    }}
