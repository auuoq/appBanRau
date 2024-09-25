package com.example.appbanrau.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.appbanrau.Adapter.AdapterFoodAdmin;
import com.example.appbanrau.Adapter.AdapterSanPhamCuAdmin;
import com.example.appbanrau.Adapter.AdapterSanPhamRauAdmin;
import com.example.appbanrau.DAO.TrangChuAdminDAO;
import com.example.appbanrau.DTO.SanPhamRauAdminDTO;
import com.example.appbanrau.R;
import com.example.appbanrau.SuaSanPhamAdmin;
import com.example.appbanrau.ThemSanPhamAdmin;

public class FragmentQuaTrangChuAdmin extends Fragment implements AdapterSanPhamCuAdmin.SanPhamAdminInterface, AdapterSanPhamRauAdmin.SanPhamAdminInterface {

    public static RecyclerView recyclerViewQuaAdmin;
    private FloatingActionButton fltAddQuaAdmin;
    private AdapterSanPhamRauAdmin.SanPhamAdminInterface listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_qua_trang_chu_admin, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listener = this;
        recyclerViewQuaAdmin = view.findViewById(R.id.recyclerQuaAdmin);
        fltAddQuaAdmin = view.findViewById(R.id.fltAddQuaAdmin);
        intiData();

        fltAddQuaAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThemSanPhamAdmin.class));
            }
        });
    }

    private void intiData() {
        TrangChuAdminDAO trangChuAdminDAO = new TrangChuAdminDAO(getContext());
        ArrayList<SanPhamRauAdminDTO> list = trangChuAdminDAO.getDSSanPhamQuaAdmin();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerViewQuaAdmin.setLayoutManager(gridLayoutManager);
        AdapterFoodAdmin adapterFoodAdmin = new AdapterFoodAdmin(list, getContext(), listener);
        recyclerViewQuaAdmin.setAdapter(adapterFoodAdmin);
        adapterFoodAdmin.notifyDataSetChanged();
//        Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
        intiData();
    }

    @Override
    public void updateSanPham(SanPhamRauAdminDTO dto) {
        Intent intent = new Intent(requireActivity(), SuaSanPhamAdmin.class);
        intent.putExtra("dto", dto);
        requireActivity().startActivity(intent);
    }
}
