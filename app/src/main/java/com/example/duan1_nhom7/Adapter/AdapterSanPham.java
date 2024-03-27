package com.example.duan1_nhom7.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DAO.DAOLoaiSP;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Fragment.ChiTiet_SP_gioHang;
import com.example.duan1_nhom7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.UserViewHolder> {

    private Context context;
    ArrayList<SanPham> list;
    BottomNavigationView bottomNavigationView;
    SanPhamDAO sanPhamDAO;

    SpinnerAdapter spinnerAdapter;
    LoaiSP loaiSP;
    DAOLoaiSP daoLoaiSP;
    List<LoaiSP> listLoai;

    UserDAO userDAO;
    public AdapterSanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_sanpham, parent, false);
        bottomNavigationView = view.findViewById(R.id.navigation);
        return new UserViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        Set Data cho List Item
        SanPham sanPham = list.get(position);
        holder.TenSanPham.setText(sanPham.getTenSP());
        double giaSP = sanPham.getGiaTienSP();
        String mGiaSP = String.format("%,.0f", giaSP);
        holder.GiaTien.setText(mGiaSP + "VNĐ");
//        holder.GiaTien.setText(String.valueOf(sanPham.getGiaTienSP()+"VNĐ"));
        Picasso.get().load(sanPham.getAnhSP()).into(holder.img_SanPham);
        holder.add_sanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChiTiet_SP_gioHang(sanPham));
            }
        });
        userDAO=new UserDAO(context);

        //set quyền
        SharedPreferences pref = context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
        int id_user = pref.getInt("MA", 0);
        User user = userDAO.getUser(id_user);
            int quyenUser = user.getChucvu();
            if (quyenUser != 1) {
                holder.xoa_sanpham.setVisibility(View.GONE);
                holder.sua_sanPham.setVisibility(View.GONE);
            }else {
                holder.add_sanPham.setVisibility(View.GONE);
            }

        holder.xoa_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa sản phẩm "+sanPham.getTenSP()+"?");
                builder.setNegativeButton("Không",null);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         sanPhamDAO=new SanPhamDAO(v.getContext());
                        if(sanPhamDAO.deleteData(sanPham)>0){
                            list.remove(sanPham);
                            sanPhamDAO.getAllProduct(0);
                            notifyItemRemoved(position);
                        }
                    }
                });
                builder.show();
            }
        });
        holder.sua_sanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.fragment_sua_sp);
                // hiển thị dữ liệu cũ
                Spinner spnLT=dialog.findViewById(R.id.ed_suaLoaiSP);
                daoLoaiSP = new DAOLoaiSP(context);
                listLoai = (ArrayList<LoaiSP>) daoLoaiSP.getAllLoaiSP();
                spinnerAdapter = new SpinnerAdapter((List<LoaiSP>) listLoai,context);
                spnLT.setAdapter(spinnerAdapter);
                spnLT.setSelection(list.get(position).getId_Loai());


                EditText ed_suaName = dialog.findViewById(R.id.ed_suaNameSP);
                ed_suaName.setText(  sanPham.getTenSP() );
                EditText ed_suaPrice=dialog.findViewById(R.id.ed_suaPrice);
                ed_suaPrice.setText(sanPham.getGiaTienSP()+"");
                EditText  ed_suaMoTa=dialog.findViewById(R.id.ed_suaMoTa);
                ed_suaMoTa.setText(sanPham.getMoTaSP());
                EditText ed_suaImage=dialog.findViewById(R.id.ed_suaImageSP);
                ed_suaImage.setText(sanPham.getAnhSP());
                EditText  ed_suaSoLuongSP=dialog.findViewById(R.id.ed_suaSoLuongSP);
                ed_suaSoLuongSP.setText(sanPham.getSoLuongSP()+"");

                EditText  ed_suaNgaySP=dialog.findViewById(R.id.ed_suaNgaySP);
                ed_suaNgaySP.setText(sanPham.getNgaySP());
                ed_suaNgaySP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                int nam = i;
                                int thang = i1;
                                int ngay = i2;
                                ed_suaNgaySP.setText(ngay + "/" + (thang + 1) + "/" + nam);

                            }
                        },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DATE)
                        );
                        dialog.show();
                    }
                });
                Button btn_suaSP = dialog.findViewById(R.id.btn_suaSP);
                Button btn_huy_sua_SP=dialog.findViewById(R.id.btn_huy_suaSP);
                btn_huy_sua_SP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_suaSP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenSP_moi = ed_suaName.getText().toString();
                        String anhSP_moi= ed_suaImage.getText().toString();
                        int  giaTienSP_moi = Integer.parseInt(ed_suaPrice.getText().toString());
                        String moTa_moi = ed_suaMoTa.getText().toString();
                        int  soLuongSP_moi = Integer.parseInt(ed_suaSoLuongSP.getText().toString());
                        String ngaySP_moi=ed_suaNgaySP.getText().toString();

                        sanPham.setTenSP(tenSP_moi);
                        sanPham.setAnhSP(anhSP_moi);
                        sanPham.setGiaTienSP(giaTienSP_moi);
                        sanPham.setMoTaSP(moTa_moi);
                        sanPham.setSoLuongSP(soLuongSP_moi);
                        sanPham.setNgaySP(ngaySP_moi);
                        sanPhamDAO=new SanPhamDAO(context);
                        long kq = sanPhamDAO.updateSanPham(sanPham);
                        if(kq>0){
                            list.set(position, sanPham );
                            notifyDataSetChanged();

                            dialog.dismiss();

                        }else{
                            Toast.makeText(context, "Lỗi rồi, xem log đi", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();
            }

        });




    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView xoa_sanpham, img_SanPham,add_sanPham,sua_sanPham;
        private TextView TenSanPham;
        private TextView GiaTien;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_sanPham);
            xoa_sanpham = itemView.findViewById(R.id.xoa_sanpham);
            img_SanPham = itemView.findViewById(R.id.img_sanpham);
            add_sanPham=itemView.findViewById(R.id.add_sanpham);
            sua_sanPham=itemView.findViewById(R.id.sua_sanpham);
            TenSanPham = itemView.findViewById(R.id.ten_sanpham);
            GiaTien = itemView.findViewById(R.id.gia_sanpham);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
