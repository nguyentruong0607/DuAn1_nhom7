package com.example.duan1_nhom7.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1_nhom7.Adapter.AdapterLoaiSP;
import com.example.duan1_nhom7.DAO.DAOLoaiSP;

import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;


public class LoaiSPFragment extends Fragment {

    private DAOLoaiSP daoLoaiSP;
    private RecyclerView recyclerView;
    private AdapterLoaiSP adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_s_p, container, false);

        daoLoaiSP = new DAOLoaiSP(getContext());

        List<LoaiSP> loaiSPList = daoLoaiSP.getAllLoaiSP(); // Lấy danh sách loại sản phẩm từ DAO

        recyclerView = view.findViewById(R.id.listDanhSachSP);
        adapter = new AdapterLoaiSP(getContext(), loaiSPList);

//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddLoaiSP(Gravity.CENTER);
            }
        });

        adapter.setOnItemClickListener(new AdapterLoaiSP.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiSP loaiSP) {
                showDialog(loaiSP);
            }
        });

        return view;
    }

    private void openAddLoaiSP(int gravity) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_loaisp);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);

        EditText edLinkImg = dialog.findViewById(R.id.addLinkImgLoaiSP);
        EditText edName = dialog.findViewById(R.id.addNameLoaiSP);
        Button btnCancle = dialog.findViewById(R.id.btnCancleDiagAdd);
        Button btnAdd = dialog.findViewById(R.id.btnAddLoaiSP);

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkImg = edLinkImg.getText().toString().trim();
                String name = edName.getText().toString().trim();

                if (!linkImg.isEmpty() && !name.isEmpty()) {
                    daoLoaiSP.addLoaiSP(name, linkImg); // Gọi phương thức addLoaiSP từ DAO
                    dialog.dismiss();
                    List<LoaiSP> updatedList = daoLoaiSP.getAllLoaiSP(); // Lấy danh sách loại sản phẩm sau khi thêm
                    adapter.setData(updatedList);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    private void showDialog(LoaiSP loaiSP) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_xoa_loaisp);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);

        ImageView img = dialog.findViewById(R.id.img_dialog_sua_them);
        EditText edName = dialog.findViewById(R.id.edName_dialog_sua_them);
        EditText edLink = dialog.findViewById(R.id.edLink_dialog_sua_them);
        Button btnXoa = dialog.findViewById(R.id.btnXoaLoaiSP);
        Button btnSua = dialog.findViewById(R.id.btnSuaLoaiSP);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDeleteDialog(loaiSP, dialog);
            }

            private void showConfirmDeleteDialog(LoaiSP loaiSP, Dialog dialog) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa " + loaiSP.getTenLoaiSP() + " không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        daoLoaiSP.deleteLoaiSP(loaiSP.getId()); // Gọi phương thức deleteLoaiSP từ DAO
                        List<LoaiSP> updatedList = daoLoaiSP.getAllLoaiSP(); // Lấy danh sách loại sản phẩm sau khi xóa
                        adapter.setData(updatedList);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edName.getText().toString().trim();
                String newLink = edLink.getText().toString().trim();

                if (!newName.isEmpty() && !newLink.isEmpty()) {
                    daoLoaiSP.updateLoaiSP(loaiSP.getId(), newName, newLink); // Gọi phương thức updateLoaiSP từ DAO
                    List<LoaiSP> updatedList = daoLoaiSP.getAllLoaiSP(); // Lấy danh sách loại sản phẩm sau khi cập nhật
                    adapter.setData(updatedList);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Picasso.get().load(loaiSP.getImgLoaiSP()).into(img);
        edName.setText(loaiSP.getTenLoaiSP());
        edLink.setText(loaiSP.getImgLoaiSP());

        dialog.show();
    }
}