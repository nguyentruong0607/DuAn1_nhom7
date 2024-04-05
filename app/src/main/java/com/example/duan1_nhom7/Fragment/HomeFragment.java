package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom7.Adapter.AdapterLoaiSP;
import com.example.duan1_nhom7.Adapter.HomeAdapter;
import com.example.duan1_nhom7.DAO.DAOLoaiSP;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcv_allSP, rcv_allNewSP, rcv_LoaiSPHome, rcv_timKiem, rcv_hangSX;
    private ArrayList<SanPham> listSp = new ArrayList<>();
    private ArrayList<SanPham> listSpTimKiem = new ArrayList<>();
    TextView tv_timKiem,tvQuayLai;
    ScrollView viewScroll;
    LinearLayout layoutParent, viewHome,tv_nullLoaiSP;
    ViewFlipper viewFlipper;
    SanPhamDAO sanPhamDAO;
    HomeAdapter homeAdapter;
    DAOLoaiSP daoLoaiSP;
    AdapterLoaiSP adapterLoaiSP;
    ArrayList<LoaiSP> listLoaiSP = new ArrayList<>();
    AutoCompleteTextView edt_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgNotifi = view.findViewById(R.id.imgNotifi);
        layoutParent = view.findViewById(R.id.layoutParent);
        rcv_allSP = view.findViewById(R.id.rcv_AllSanPham);
        rcv_allNewSP = view.findViewById(R.id.rcv_SPnew);
        rcv_LoaiSPHome = view.findViewById(R.id.rcv_LoaiSPHome);
        rcv_timKiem = view.findViewById(R.id.rcv_timKiem);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        edt_search = view.findViewById(R.id.edt_search);
        viewHome = view.findViewById(R.id.viewHome);
        tv_timKiem = view.findViewById(R.id.tv_timKiem);
        tvQuayLai = view.findViewById(R.id.tvQuayLai);
        tv_nullLoaiSP = view.findViewById(R.id.tv_nullLoaiSP);
        rcv_hangSX = view.findViewById(R.id.rcv_hangSX);
        viewScroll = view.findViewById(R.id.viewScroll);
        viewFlipper();

        daoLoaiSP = new DAOLoaiSP(getContext());
        sanPhamDAO = new SanPhamDAO(getContext());

        listLoaiSP = (ArrayList<LoaiSP>) daoLoaiSP.getAllLoaiSP();

        adapterLoaiSP = new AdapterLoaiSP(getContext(), listLoaiSP, new AdapterLoaiSP.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiSP loaiSP) {
                //                Ẩn view tổng của viewScroll
                viewScroll.setVisibility(View.GONE);
                List<SanPham> listSpham = sanPhamDAO.getProductsByCategoryId(loaiSP.getId());
//                    Nếu không tìm thấy thì hiện textview báo không tìm thấy
                    if (listSpham.size() == 0) {
                        rcv_hangSX.setVisibility(View.GONE);
                        tv_nullLoaiSP.setVisibility(View.VISIBLE);
//                    Nếu tìm thấy thì hiện danh sách
                    } else {
                        HomeAdapter homeAdapter1 = new HomeAdapter((ArrayList<SanPham>) listSpham, getContext());
                        rcv_hangSX.setAdapter(homeAdapter1);
                        tv_nullLoaiSP.setVisibility(View.GONE);
                        rcv_hangSX.setVisibility(View.VISIBLE);
                    }
            }
        });
        rcv_LoaiSPHome.setAdapter(adapterLoaiSP);

        listSp = sanPhamDAO.getAllProduct(0);
        homeAdapter = new HomeAdapter(listSp, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_allSP.setLayoutManager(linearLayoutManager);
        rcv_allSP.setAdapter(homeAdapter);

        ArrayList<SanPham> listSPnew = new ArrayList<>();
        listSPnew = sanPhamDAO.getAllProduct(1);
        HomeAdapter homeAdapter1 = new HomeAdapter(listSPnew, getActivity());

        rcv_allNewSP.setAdapter(homeAdapter1);
        tvQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScroll.setVisibility(View.VISIBLE);
                viewHome.setVisibility(View.VISIBLE);
                rcv_hangSX.setVisibility(View.GONE);
                tv_nullLoaiSP.setVisibility(View.GONE);
            }
        });
//        Xử lý sự kiện khi edittext có sự thay đổi
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Ẩn view tổng của home
                viewHome.setVisibility(View.GONE);
//                Hiện danh sách sản phẩm tìm kiếm
                rcv_timKiem.setVisibility(View.VISIBLE);
//              Nếu nhập vào rỗng thì hiện viewHome, ẩn danh sách tìm kiếm,và ngược lại
                if (s.toString().trim().equals("")) {
                    viewHome.setVisibility(View.VISIBLE);
                    rcv_timKiem.setVisibility(View.GONE);
                    tv_timKiem.setVisibility(View.GONE);
                } else {
                    viewHome.setVisibility(View.GONE);
                    listSpTimKiem = sanPhamDAO.searchProductByName(s.toString().trim());
//                    Nếu không tìm thấy thì hiện textview báo không tìm thấy
                    if (listSpTimKiem.size() == 0) {
                        rcv_timKiem.setVisibility(View.GONE);
                        tv_timKiem.setVisibility(View.VISIBLE);
//                    Nếu tìm thấy thì hiện danh sách
                    } else {
                        HomeAdapter homeAdapter2 = new HomeAdapter(listSpTimKiem, getContext());
                        rcv_timKiem.setAdapter(homeAdapter2);
                        tv_timKiem.setVisibility(View.GONE);
                        rcv_timKiem.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void viewFlipper() {
        List<String> banner = new ArrayList<>();
        banner.add("https://cdn.tgdd.vn/2024/03/banner/iPhone-13-800-200-800x200.png");
        banner.add("https://cdn.tgdd.vn/Products/Images/42/305658/Slider/iphone-15-pro-max-thumb-youtube-1020x570.jpg");
        banner.add("https://thietkehaithanh.com/wp-content/uploads/2019/06/thietkehaithanh-banner-1-1.jpg");
        for (int i = 0; i < banner.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(banner.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
}
