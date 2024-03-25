package com.example.duan1_nhom7.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.R;

import java.util.List;


public class SpinnerAdapter extends BaseAdapter {
    List<LoaiSP> listLoai;
    Context context;

    public SpinnerAdapter(List<LoaiSP> listLoai, Context context) {
        this.listLoai = listLoai;
        this.context = context;
    }
    public static class ViewHolder{
        TextView spn_ten;
    }
    @Override
    public int getCount() {
        return listLoai.size();
    }

    @Override
    public Object getItem(int i) {
        return listLoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.spinner_loai, null);
            holder.spn_ten = view.findViewById(R.id.spn_tenLoai);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


            LoaiSP obj = listLoai.get(i);
            holder.spn_ten.setText(obj.getTenLoaiSP());


        return view;
    }
}
