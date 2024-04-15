package com.example.duan1_nhom7.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {

    private Context context;
    private List<DonHang> donHangList;
    private SanPhamDAO sanPhamDAO;

    public AdapterItem(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
        sanPhamDAO = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        Picasso.get().load(sanPhamDAO.getAnhSanPhamById(donHang.getId_sanPham())).into(holder.imgGHAnhSP);
        holder.txtGHTenSP.setText(sanPhamDAO.getTenSanPhamById(donHang.getId_sanPham()));
        holder.txtGHSize.setText(donHang.getMau());
        double donGia = donHang.getGiaBan();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedDonGia = decimalFormat.format(donGia);
        holder.txtGHGia.setText(formattedDonGia + " VNĐ");
        int soLuong = donHang.getSoLuong();
        holder.edtGHSoLuong.setText(String.valueOf(soLuong));
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP;
        TextView txtGHTenSP, txtGHGia, txtGHSize, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGHAnhSP = itemView.findViewById(R.id.imgHoaDon);
            txtGHTenSP = itemView.findViewById(R.id.txtTenSP_Hoadon);
            txtGHSize = itemView.findViewById(R.id.txtMau_Hoadon);
            txtGHGia = itemView.findViewById(R.id.txtGia_Hoadon);
            edtGHSoLuong = itemView.findViewById(R.id.txtSoLuong_Hpadon);
        }
    }
}
