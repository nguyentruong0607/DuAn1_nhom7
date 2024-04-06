package com.example.duan1_nhom7.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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


        return db.insert("User",null,value);
    }
    public int updateUser(User tt){
        ContentValues value = new ContentValues();
        value.put("sodienthoai",tt.getSodienthoai());
        value.put("diaChi",tt.getDiaChi());
        value.put("fullname",tt.getFullname());


        String [] tham_so=new String[]{tt.getId_user()+""};
        return db.update("User",value,"id_user=?",tham_so);

    }


    public boolean checkLogin(String username, String password) {
        boolean success = false;
        Cursor cursor = null;
        if (db != null) {
            String sql = "SELECT * FROM User WHERE ten_user=? AND password=?";
            cursor = db.rawQuery(sql, new String[]{username, password});
            if (cursor.moveToFirst()) {
                success = true;
            }
        }
        return success;
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

                list.add(new User(_id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname));
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

                user = new User( _id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname);
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


                User user=new User(_id_user,_ten_user,_password,_sodienthoai,_diaChi,_fullname);
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

    public List<String> getDiaChi() {
        List<String> diaChiList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User", null);
        if (cursor.moveToFirst()) {
            do {
                String diaChi = cursor.getString(cursor.getColumnIndex("diaChi"));
                diaChiList.add(diaChi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return diaChiList;
    }




    public String getIdUser(String username) {
        String idUser = null;
        Cursor cursor = db.rawQuery("SELECT id_user FROM User WHERE ten_user=?", new String[]{username});
        if (cursor.moveToFirst()) {
            idUser = cursor.getString(0);
        }
        cursor.close();
        return idUser;
    }

    public String getFullName(String username) {
        String fullname = null;
        Cursor cursor = db.rawQuery("SELECT fullname FROM User WHERE ten_user=?", new String[]{username});
        if (cursor.moveToFirst()) {
            fullname = cursor.getString(0);
        }
        cursor.close();
        return fullname;
    }

    public String getPhone(String username) {
        String phone = null;
        Cursor cursor = db.rawQuery("SELECT sodienthoai FROM User WHERE ten_user=?", new String[]{username});
        if (cursor.moveToFirst()) {
            phone = cursor.getString(0);
        }
        cursor.close();
        return phone;
    }

    public String getLocation(String username) {
        String location = null;
        Cursor cursor = db.rawQuery("SELECT diaChi FROM User WHERE ten_user=?", new String[]{username});
        if (cursor.moveToFirst()) {
            location = cursor.getString(0);
        }
        cursor.close();
        return location;
    }

    public User getUserByName(String inputName) {
        User user = null;
        Cursor cursor = db.rawQuery("SELECT User.id_user, User.ten_user, User.password, User.sodienthoai,User.diaChi, User.fullname FROM User WHERE User.ten_user=?", new String[]{inputName});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int _id_user = cursor.getInt(0);
                String _ten_user = cursor.getString(1);
                String _password = cursor.getString(2);
                String _sodienthoai = cursor.getString(3);
                String _diaChi = cursor.getString(4);
                String _fullname = cursor.getString(5);

                user = new User(_id_user, _ten_user, _password, _sodienthoai, _diaChi, _fullname);
            } while (cursor.moveToNext());
        }
        return user;
    }

}