package com.example.duan1_nhom7.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_gioHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private ArrayList<SanPham> list;
    private Context context;

    public HomeAdapter(ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sp_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        Picasso.get().load(sanPham.getAnhSP()).into(holder.itemSpHomeImg);
        holder.itemSpHomeTen.setText(sanPham.getTenSP());
        holder.itemSpHomeGia.setText(sanPham.getGiaTienSP()+""+"VNĐ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChiTiet_SP_gioHang(sanPham));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemSpHomeTen,itemSpHomeGia;
        ImageView itemSpHomeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemSpHomeTen = itemView.findViewById(R.id.itemSpHomeTen);
            itemSpHomeGia = itemView.findViewById(R.id.itemSpHomeGia);
            itemSpHomeImg = itemView.findViewById(R.id.itemSpHomeImg);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
