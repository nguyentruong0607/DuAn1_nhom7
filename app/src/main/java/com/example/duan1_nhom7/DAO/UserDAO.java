package com.example.duan1_nhom7.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1_nhom7.DTO.SanPham;
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
       value.put("Chucvu",tt.getChucvu());

        return db.insert("User",null,value);
    }
    public int updateUser(User tt){
        ContentValues value = new ContentValues();
        value.put("ten_user",tt.getTen_user());
        value.put("password",tt.getPassword());
        value.put("sodienthoai",tt.getSodienthoai());
        value.put("diaChi",tt.getDiaChi());
        value.put("fullname",tt.getFullname());
        value.put("Chucvu",tt.getChucvu());

        String [] tham_so=new String[]{tt.getId_user()+""};
        return db.update("User",value,"id_user=?",tham_so);

    }
    public ArrayList<User> checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE ten_user=? AND password=?";
        ArrayList<User> list = getData(sql, username, password);
        return list;
    }
    public ArrayList<User> getData(String sql, String... selectionAGrs) {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionAGrs);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int _id_user = cursor.getInt(0);
                String _ten_user = cursor.getString(1);
                String _password = cursor.getString(2);
                String _sodienthoai = cursor.getString(3);
                String _diaChi = cursor.getString(4);
                String _fullname = cursor.getString(5);
                int _id_chucvu = cursor.getInt(6);

                list.add(new User(_id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname,_id_chucvu));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public User getUser(int inputId) {
        User user=null;
        Cursor cursor = db.rawQuery("SELECT User.id_user, User.ten_user, User.password, User.sodienthoai,User.diaChi, User.fullname,User.Chucvu FROM User WHERE User.id_user=?", new String[]{String.valueOf(inputId)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int _id_user = cursor.getInt(0);
                String _ten_user = cursor.getString(1);
                String _password = cursor.getString(2);
                String _sodienthoai = cursor.getString(3);
                String _diaChi = cursor.getString(4);
                String _fullname = cursor.getString(5);
                int _Chucvu = cursor.getInt(6);
                user = new User( _id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname,_Chucvu);
            } while (cursor.moveToNext());
        }
        return user;
    }
    public List<User> getAllUser(){
        List<User> list = new ArrayList<User>();

        Cursor cursor=db.rawQuery("SELECT * FROM User",null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int _id_user = cursor.getInt(0);
                String _ten_user = cursor.getString(1);
                String _password = cursor.getString(2);
                String _sodienthoai = cursor.getString(3);
                String _diaChi = cursor.getString(4);
                String _fullname = cursor.getString(5);
                int _id_chucvu = cursor.getInt(6);

                User user=new User(_id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname,_id_chucvu);
                list.add( user);
                cursor.moveToNext();
            }

        }else{
            Log.d("zzz", "selectAll: Không có dữ liệu");
        }

        return  list;
    }
    public int deleteData(User user) {

        return db.delete("User","id_user=?",new String[]{user.getId_user()+""});
    }

}