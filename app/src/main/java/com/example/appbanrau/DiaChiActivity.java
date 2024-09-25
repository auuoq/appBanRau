package com.example.appbanrau;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.appbanrau.Adapter.DiaChiUserAdapter;
import com.example.appbanrau.DAO.DiaChiKhachHangDAO;
import com.example.appbanrau.DTO.ThongTinKhachHangDTO;

public class DiaChiActivity extends AppCompatActivity {

    RecyclerView recyclerDiaChi;
    private ImageView imgBackDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        recyclerDiaChi = findViewById(R.id.recyclerDiaChi);
        imgBackDiaChi = findViewById(R.id.imgBackDiaChi);
        imgBackDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DiaChiKhachHangDAO diaChiKhachHangDAO = new DiaChiKhachHangDAO(this);
        ArrayList<ThongTinKhachHangDTO> list = diaChiKhachHangDAO.getDiaChiAll();
        LinearLayoutManager manager = new LinearLayoutManager(this);

        DiaChiUserAdapter adapter = new DiaChiUserAdapter(list, this);
        recyclerDiaChi.setLayoutManager(manager);
        recyclerDiaChi.setAdapter(adapter);

    }
}