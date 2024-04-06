package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.R;


public class Personal_Account_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_personal__account_, container, false);
        Bundle args = getArguments();
        if (args != null) {
            User loggedInUser = (User) args.getSerializable("user");
            if (loggedInUser != null) {
                Log.i("aaaaaaaaaaaaa",loggedInUser.toString());
            }
        }else {
            Log.i("aaaaaaaaaaaaa","khong co thong tin");

        }
        Log.i("aaaaaaaaaaaaa","khong co thong tin111");

      return  view;
    }
}