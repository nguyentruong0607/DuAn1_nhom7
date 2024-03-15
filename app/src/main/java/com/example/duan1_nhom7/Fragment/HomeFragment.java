package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom7.Adapter.HomeAdapter;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView rcv_allSP,rcv_allNewSP;
    private ArrayList<SanPham> listSp = new ArrayList<>();
    LinearLayout layoutParent;
    ViewFlipper viewFlipper;
    SanPhamDAO sanPhamDAO;
    HomeAdapter homeAdapter;
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
        rcv_allNewSP=view.findViewById(R.id.rcv_SPnew);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        viewFlipper();
        sanPhamDAO=new SanPhamDAO(getContext());
        listSp=sanPhamDAO.getAllProduct(0);
        homeAdapter=new HomeAdapter(listSp,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_allSP.setLayoutManager(linearLayoutManager);
        rcv_allSP.setAdapter(homeAdapter);

        ArrayList<SanPham> listSPnew=new ArrayList<>();
        listSPnew=sanPhamDAO.getAllProduct(1);
        HomeAdapter homeAdapter1=new HomeAdapter(listSPnew,getActivity());
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rcv_allNewSP.setLayoutManager(linearLayoutManager1);
        rcv_allNewSP.setAdapter(homeAdapter1);


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
