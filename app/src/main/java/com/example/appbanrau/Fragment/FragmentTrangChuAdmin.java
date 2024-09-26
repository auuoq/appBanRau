package com.example.appbanrau.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanrau.Adapter.AdapterSanPhamAdmin;
import com.example.appbanrau.DAO.TrangChuAdminDAO;
import com.example.appbanrau.DTO.SanPhamAdminDTO;
import com.example.appbanrau.R;
import com.example.appbanrau.SuaSanPhamAdmin;
import com.example.appbanrau.ThemSanPhamAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentTrangChuAdmin extends Fragment implements AdapterSanPhamAdmin.SanPhamAdminInterface {

    private RecyclerView recyclerSanPhamAdmin;
    private EditText edSeachAdmin;
    private TextView tvTenTaiKhoanAdmin;
    private FloatingActionButton fltAddSanPhamAdmin;
    private TrangChuAdminDAO trangChuAdminDAO;
    private AdapterSanPhamAdmin adapterSanPhamAdmin;
    private ArrayList<SanPhamAdminDTO> listSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_trang_chu_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trangChuAdminDAO = new TrangChuAdminDAO(getContext());

        // Khởi tạo các View
        edSeachAdmin = view.findViewById(R.id.edSeachAdmin);
        tvTenTaiKhoanAdmin = view.findViewById(R.id.tvTenTaiKhoanAdmin);
        recyclerSanPhamAdmin = view.findViewById(R.id.recyclerSanPhamAdmin);
        fltAddSanPhamAdmin = view.findViewById(R.id.fltAddSanPhamAdmin);

        // Hiển thị tên đăng nhập
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String tenDangNhap = sharedPreferences.getString("tenDangNhap", "");
        tvTenTaiKhoanAdmin.setText("Hi, " + tenDangNhap);

        // Khởi tạo RecyclerView
        recyclerSanPhamAdmin.setLayoutManager(new GridLayoutManager(getContext(), 1));
        listSanPham = trangChuAdminDAO.getDSSanPhamAdmin(); // Lấy tất cả sản phẩm
        adapterSanPhamAdmin = new AdapterSanPhamAdmin(listSanPham, getContext(), this);
        recyclerSanPhamAdmin.setAdapter(adapterSanPhamAdmin);

        // Thêm sản phẩm
        fltAddSanPhamAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThemSanPhamAdmin.class);
                startActivity(intent);
            }
        });

        // Chức năng tìm kiếm
        edSeachAdmin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSanPham(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });
    }

    /**
     * Lọc danh sách sản phẩm dựa trên từ khóa tìm kiếm.
     *
     * @param query Từ khóa tìm kiếm.
     */
    private void filterSanPham(String query) {
        ArrayList<SanPhamAdminDTO> filteredList = trangChuAdminDAO.timKiemSanPham(query);
        adapterSanPhamAdmin.updateList(filteredList);
    }

    @Override
    public void updateSanPham(SanPhamAdminDTO dto) {
        Intent intent = new Intent(getActivity(), SuaSanPhamAdmin.class);
        intent.putExtra("dto", dto);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật danh sách sản phẩm khi trở về fragment
        listSanPham = trangChuAdminDAO.getDSSanPhamAdmin();
        adapterSanPhamAdmin.updateList(listSanPham);
    }
}
