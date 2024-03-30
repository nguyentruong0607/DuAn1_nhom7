package com.example.duan1_nhom7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder> {

    private Context context;
    private List<GioHang> hoaDonList;

    public AdapterHoaDon(Context context, List<GioHang> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = hoaDonList.get(position);

        Picasso.get().load(gioHang.getImgSP()).into(holder.imgGHAnhSP);
        holder.txtGHTenSP.setText(gioHang.getTenSP());
        holder.txtGHSize.setText( gioHang.getMau());
        double donGia = gioHang.getDonGia();
        String outDonGia = String.format("%,.0f", donGia);
        holder.txtGHGia.setText(outDonGia + " VNĐ");
        int soLuong = gioHang.getSoLuong();
        holder.edtGHSoLuong.setText(soLuong + "");
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP;
        TextView txtGHTenSP, txtGHGia,txtGHSize, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGHAnhSP = itemView.findViewById(R.id.imgHoaDon);
            txtGHTenSP = itemView.findViewById(R.id.txtTenSP_Hoadon);
            txtGHSize=itemView.findViewById(R.id.txtMau_Hoadon);
            txtGHGia = itemView.findViewById(R.id.txtGia_Hoadon);
            edtGHSoLuong = itemView.findViewById(R.id.txtSoLuong_Hpadon);

        }
    }
}