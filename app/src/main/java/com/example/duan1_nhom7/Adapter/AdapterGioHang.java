package com.example.duan1_nhom7.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DAO.GioHangDAO;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.Fragment.StoreFragment;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.ViewHolder> {

    private ArrayList<GioHang> list;
    private Context context;
    GioHangDAO daoGioHang;
    SanPham sanPham;

    public AdapterGioHang(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_giohang, parent, false);
        daoGioHang = new GioHangDAO(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);

//        Set ảnh cho Items

        Picasso.get().load(gioHang.getImgSP()).into(holder.imgGHAnhSP);
        holder.txtGHTenSP.setText(gioHang.getTenSP());
        holder.txtGHSize.setText( gioHang.getMau());
        double donGia = gioHang.getDonGia();
        String outDonGia = String.format("%,.0f", donGia);
        holder.txtGHGia.setText(outDonGia + " VNĐ");
        int soLuong = gioHang.getSoLuong();
        holder.edtGHSoLuong.setText(soLuong + "");

//        Sự kiện giảm số lượng sản phẩm
        holder.btnHGTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if (soLuong > 1){
//                    Số lượng SP hiện tại > 0 => Giảm
                    soLuong--;
                    gioHang.setSoLuong(soLuong);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra){
                        list.clear();
                        list = daoGioHang.getGioHang();
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                    holder.edtGHSoLuong.setText(soLuong + "");
                }
                else {
//                    Số lượng SP = 0 => Xóa SP khỏi giỏ hàng
                    boolean kiemtra = daoGioHang.deleteGiohang(gioHang);
                    if (kiemtra){

                        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa không ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                daoGioHang.deleteGiohang(gioHang);
                                list.clear();
                                list = daoGioHang.getGioHang();
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    else {
                        Toast.makeText(context, "Delete Fail!", Toast.LENGTH_SHORT).show();
                    }

                }
                notifyDataSetChanged();
                double tongTien = daoGioHang.tongTienGiohang();
                String outTongTien = String.format("%,.0f", tongTien);
                StoreFragment.txtGHTongTien.setText(outTongTien + "VNĐ");
            }
        });

//        Sự kiện tăng số lượng sản phẩm
        holder.btnGHCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();

                if (soLuong < 10) {
                    soLuong++;
                    gioHang.setSoLuong(soLuong);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra) {
                        list.clear();
                        list = daoGioHang.getGioHang();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                    holder.edtGHSoLuong.setText(String.valueOf(soLuong));
                    notifyDataSetChanged();
                    double tongTien = daoGioHang.tongTienGiohang();
                    String outTongTien = String.format("%,.0f", tongTien);
                    StoreFragment.txtGHTongTien.setText(outTongTien + " VNĐ");
                } else {
                    Toast.makeText(context, "vượt quá số lượng được mua!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.edtGHSoLuong.getText().toString();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP, btnHGTru, btnGHCong;
        TextView txtGHTenSP, txtGHGia,txtGHSize, edtGHSoLuong;
        CheckBox chk_gioHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGHAnhSP = itemView.findViewById(R.id.imgGHAnhSP);
            txtGHTenSP = itemView.findViewById(R.id.txtGHTenSP);
            txtGHSize=itemView.findViewById(R.id.txtGHSizeSP);
            txtGHGia = itemView.findViewById(R.id.txtGHGia);
            edtGHSoLuong = itemView.findViewById(R.id.edtGHSoLuong);
            btnHGTru = itemView.findViewById(R.id.btnHGTru);
            btnGHCong = itemView.findViewById(R.id.btnGHCong);
            chk_gioHang=itemView.findViewById(R.id.chk_gioHang);
        }
    }
}
