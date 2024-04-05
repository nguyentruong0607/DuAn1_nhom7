package com.example.duan1_nhom7.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ThongKeFragment extends Fragment {
    Button btnStart, btnEnd, btnDoanhThu;
    EditText edStart, edEnd;
    TextView txtDoanhThu, txtSoLuong;
    DAOHoaDon daoHoaDon;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    int mYear, mMonth, mDay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        daoHoaDon = new DAOHoaDon(getActivity());
        btnStart = view.findViewById(R.id.btnStart);
        btnEnd = view.findViewById(R.id.btnEnd);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        edStart = view.findViewById(R.id.edStart);
        edEnd = view.findViewById(R.id.edEnd);
        txtDoanhThu = view.findViewById(R.id.txtDoanhThu);
        txtSoLuong = view.findViewById(R.id.txtSoLuongThongKe);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgayString = edStart.getText().toString();
                String denNgayString = edEnd.getText().toString();

                double tong = daoHoaDon.calculateRevenueByTimePeriod(tuNgayString, denNgayString);
                String outTongTien = String.format("%,.0f VNĐ", tong);
                txtDoanhThu.setText("Doanh thu từ ngày " + tuNgayString+ " đến ngày " + denNgayString + " là: " + outTongTien);
                txtSoLuong.setText("Số lượng sản phẩm bán ra là: " + String.valueOf(daoHoaDon.calculateQuantityByTimePeriod(tuNgayString, denNgayString)));
            }
        });


        return view;
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;

            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edStart.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;

            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edEnd.setText(sdf.format(c.getTime()));
        }
    };
}
