package com.example.duan1_nhom7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDaGiao extends RecyclerView.Adapter<AdapterDaGiao.ViewHolder> {

    private Context context;
    private List<DonHang> hoaDonList;

    public AdapterDaGiao(Context context, List<DonHang> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_choxacnhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang gioHang = hoaDonList.get(position);

        Picasso.get().load(gioHang.getImage()).into(holder.imgGHAnhSP);
        holder.txtGHTenSP.setText(gioHang.getTenSP());
        holder.txtGHSize.setText( gioHang.getMau());
        int soLuong = gioHang.getSoLuong();
        holder.edtGHSoLuong.setText(soLuong + "");
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP;
        TextView txtGHTenSP,txtGHSize, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGHAnhSP = itemView.findViewById(R.id.imgHoaDonXacNhan);
            txtGHTenSP = itemView.findViewById(R.id.txtTenSP_HoadonXacNhan);
            txtGHSize=itemView.findViewById(R.id.txtMau_HoadonXacNhan);
            edtGHSoLuong = itemView.findViewById(R.id.txtSoLuong_HpadonXacNhan);

        }
    }
}