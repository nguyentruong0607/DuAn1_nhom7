package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.duan1_nhom7.Adapter.ViewPagerAdapter;
import com.example.duan1_nhom7.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerFragment extends Fragment {
    ViewPager2 viewPager;
    TabLayout tab_pager;
    ViewPagerAdapter pagerAdapter;
    public static ViewPagerFragment newInstance() {
        ViewPagerFragment frag=new ViewPagerFragment();
        return frag;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_viewpager,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=view.findViewById(R.id.viewPager);
        tab_pager=view.findViewById(R.id.tab_pager);
        pagerAdapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        TabLayoutMediator mediator=new TabLayoutMediator(tab_pager, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                if (i==0){
                    tab.setText("Sản phẩm");
                }else {
                    tab.setText("Loại sản phẩm");
                }
            }
        });
        mediator.attach();
    }
}
