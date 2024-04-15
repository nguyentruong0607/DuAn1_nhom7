package com.example.duan1_nhom7.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DonHangActivity;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_gioHang;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterChoXacNhanAdmin extends RecyclerView.Adapter<AdapterChoXacNhanAdmin.ViewHolder> {
    private Context context;
    private List<DonHang> hoaDonList;
    private OnItemClickListener mListener; // Thêm biến mListener

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(DonHang gioHang);
    }

    // Phương thức setter để thiết lập mListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapterChoXacNhanAdmin(Context context, List<DonHang> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_xacnhandonhang_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang gioHang = hoaDonList.get(position);

//        Picasso.get().load(gioHang.getImage()).into(holder.imgGHAnhSP);
//        holder.txtGHTenSP.setText(gioHang.getTenSP());
//        holder.txtGHSize.setText(gioHang.getMau());
//        int soLuong = gioHang.getSoLuong();
//        holder.edtGHSoLuong.setText(String.valueOf(soLuong));
//        int idDonHang = gioHang.getId_donHang();

        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn xác nhận đơn hàng này không?");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DonHangDAO donHangDAO = new DonHangDAO(context);
                        DonHang donHang = hoaDonList.get(holder.getAdapterPosition());
                        if (donHang != null) {
//                            donHang.setStatus("2");
//                            int rowsAffected = donHangDAO.updateDonHangStatus(donHang);
//                            if (rowsAffected > 0) {
//                                Toast.makeText(context, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
//
//                                // Cập nhật lại danh sách đơn hàng sau khi xác nhận thành công
//                                hoaDonList = donHangDAO.getDonHangByStatus("1");
//                                notifyDataSetChanged();
//
//                            } else {
//                                Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
//                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnXacNhan;
        ImageView imgGHAnhSP;
        TextView txtGHTenSP, txtGHSize, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGHAnhSP = itemView.findViewById(R.id.imgHoaDonXacNhanAdmin);
            txtGHTenSP = itemView.findViewById(R.id.txtTenSP_HoadonXacNhanAdmin);
            txtGHSize = itemView.findViewById(R.id.txtMau_HoadonXacNhanAdmin);
            edtGHSoLuong = itemView.findViewById(R.id.txtSoLuong_HpadonXacNhanAdmin);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);




            // Thêm sự kiện click vào itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClick(hoaDonList.get(position));
                    }
                }
            });
        }
    }

    public void updateData(List<DonHang> newData) {
        hoaDonList.clear(); // Xóa dữ liệu cũ
        hoaDonList.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Cập nhật giao diện
    }
}
