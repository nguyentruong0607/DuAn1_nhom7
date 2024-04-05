package com.example.duan1_nhom7.Adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserviewHolder>{
    ArrayList<User> list;
    Context context;
    UserDAO dao;
    BottomNavigationView bottomNavigationView;

    public AdapterUser(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        bottomNavigationView = view.findViewById(R.id.navigation);
        return new UserviewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull UserviewHolder holder,  int position) {
          User user=list.get(position);
          holder.Ten.setText(user.getFullname());
          holder.sdt.setText(user.getSodienthoai());
          dao=new UserDAO(context);
          holder.xoa_user.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                  builder.setTitle("Bạn có muốn xóa sản phẩm "+user.getFullname()+"?");
                  builder.setNegativeButton("Không",null);
                  builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          dao=new UserDAO(v.getContext());
                          if(dao.deleteData(user)>0){
                              list.remove(user);
                              dao.getAllUser();
                              notifyItemRemoved(position);
                          }
                      }
                  });
                  builder.show();
              }
          });
          holder.sua_user.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final Dialog dialog = new Dialog(context);
                  dialog.setContentView(R.layout.dialog_sua_user);
                  TextInputEditText ed_sua_fullname,ed_sua_sdtuser,ed_sua_tenuser,ed_sua_mkuser,ed_sua_diaChiUser;
                  ed_sua_fullname=dialog.findViewById(R.id.ed_sua_fullname);
                  ed_sua_sdtuser=dialog.findViewById(R.id.ed_sua_sdtuser);
                  ed_sua_tenuser=dialog.findViewById(R.id.ed_sua_tenuser);
                  ed_sua_mkuser=dialog.findViewById(R.id.ed_sua_mkuser);
                  ed_sua_diaChiUser=dialog.findViewById(R.id.ed_sua_diaChi_user);
                 ed_sua_fullname.setText(user.getFullname());
                 ed_sua_sdtuser.setText(user.getSodienthoai());
                 ed_sua_tenuser.setText(user.getTen_user());
                 ed_sua_mkuser.setText(user.getPassword());
                 ed_sua_diaChiUser.setText(user.getDiaChi());

                  Button btn_sua_user = dialog.findViewById(R.id.btn_sua_user);
                  Button btn_huy_suaUser=dialog.findViewById(R.id.btn_huy_suaUser);
                  btn_huy_suaUser.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          dialog.dismiss();
                      }
                  });
                  btn_sua_user.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          String fullname = ed_sua_fullname.getText().toString();
                          String sdt = ed_sua_sdtuser.getText().toString();
                          String tenuser = ed_sua_tenuser.getText().toString();
                          String mk = ed_sua_mkuser.getText().toString();
                          String diaChi = ed_sua_diaChiUser.getText().toString();
                          dao = new UserDAO(context);

                          user.setFullname(fullname);
                          user.setSodienthoai(sdt);
                          user.setTen_user(tenuser);
                          user.setPassword(mk);
                          user.setDiaChi(diaChi);
                          long kq = dao.updateUser(user);
                          if(kq>0){
                              list.set(position, user );
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


    public class UserviewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView xoa_user, add_user,sua_user;
        private TextView Ten;
        private TextView sdt;

        public UserviewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_user);
            xoa_user = itemView.findViewById(R.id.xoa_user);
            add_user=itemView.findViewById(R.id.add_sanpham);
            sua_user=itemView.findViewById(R.id.sua_user);
            Ten = itemView.findViewById(R.id.ten_user);
            sdt = itemView.findViewById(R.id.sdt_user);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
