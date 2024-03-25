package com.example.duan1_nhom7.Adapter;

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

import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.Fragment.ProductFragment;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLoaiSP extends RecyclerView.Adapter<AdapterLoaiSP.ViewHolder> {

    private Context context;
    private List<LoaiSP> loaiSPList;
    private OnItemClickListener onItemClickListener;

    public AdapterLoaiSP(Context context, List<LoaiSP> loaiSPList) {
        this.context = context;
        this.loaiSPList = loaiSPList;
    }

    public interface OnItemClickListener {
        void onItemClick(LoaiSP loaiSP);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setData(List<LoaiSP> newData) {
        loaiSPList.clear();
        loaiSPList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaisp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSP loaiSP = loaiSPList.get(position);
        holder.textViewTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        Picasso.get().load(loaiSP.getImgLoaiSP()).into(holder.imageViewProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(loaiSP);
                }
            }
        });
        holder.imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadFragment(new ProductFragment());
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiSPList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenLoaiSP;
        ImageView imageViewProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenLoaiSP = itemView.findViewById(R.id.txtNameLoaiSP);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}