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

import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.DTO.Top10BanChay;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_gioHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{
    private List<Top10BanChay> list;
    private Context context;
    UserDAO userDAO;
    public Top10Adapter(List<Top10BanChay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position).getSanPham();
        Picasso.get().load(sanPham.getAnhSP()).into(holder.itemSpHomeImg);
        holder.itemSpHomeTen.setText(sanPham.getTenSP());
        holder.itemSoLuong.setText(list.get(position).getSoluong()+"");
        userDAO=new UserDAO(context);

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

        TextView itemSpHomeTen,itemSoLuong;
        ImageView itemSpHomeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemSpHomeTen = itemView.findViewById(R.id.tv_TenTop);
            itemSoLuong = itemView.findViewById(R.id.tv_soLuongTop);
            itemSpHomeImg = itemView.findViewById(R.id.img_top);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
