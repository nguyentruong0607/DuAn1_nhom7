package com.example.duan1_nhom7.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_Fragment;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_gioHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private ArrayList<SanPham> list;
    private Context context;
   UserDAO userDAO;
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
//        holder.itemSpHomeGia.setText(sanPham.getGiaTienSP()+""+"VNĐ");
        double giaSP = sanPham.getGiaTienSP();
        String mGiaSP = String.format("%,.0f", giaSP);
        holder.itemSpHomeGia.setText(mGiaSP + "VNĐ");
        userDAO=new UserDAO(context);

        //set quyền
        SharedPreferences pref = context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
        int id_user = pref.getInt("MA", 0);
        User user = userDAO.getUser(id_user);
        int quyenUser = user.getChucvu();
       if (quyenUser != 1) {

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   loadFragment(new ChiTiet_SP_gioHang(sanPham));
               }
           });
       }else{
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   loadFragment(new ChiTiet_SP_Fragment(sanPham));
               }
           });
       }

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
