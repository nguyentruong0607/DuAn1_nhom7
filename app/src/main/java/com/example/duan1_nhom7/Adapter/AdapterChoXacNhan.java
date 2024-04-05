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
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterChoXacNhan extends RecyclerView.Adapter<AdapterChoXacNhan.ViewHolder> {
    private Context context;
    private List<DonHang> hoaDonList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(DonHang gioHang);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapterChoXacNhan(Context context, List<DonHang> hoaDonList) {
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
        DonHang donHang = hoaDonList.get(position);

        Picasso.get().load(donHang.getImage()).into(holder.imgGHAnhSP);
        holder.txtGHTenSP.setText(donHang.getTenSP());
        holder.txtGHSize.setText(donHang.getMau());
        int soLuong = donHang.getSoLuong();
        holder.edtGHSoLuong.setText(String.valueOf(soLuong));
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public void setData(List<DonHang> data) {
        this.hoaDonList = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP;
        TextView txtGHTenSP, txtGHSize, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGHAnhSP = itemView.findViewById(R.id.imgHoaDonXacNhan);
            txtGHTenSP = itemView.findViewById(R.id.txtTenSP_HoadonXacNhan);
            txtGHSize = itemView.findViewById(R.id.txtMau_HoadonXacNhan);
            edtGHSoLuong = itemView.findViewById(R.id.txtSoLuong_HpadonXacNhan);

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
}

