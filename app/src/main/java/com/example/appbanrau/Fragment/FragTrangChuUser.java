package com.example.appbanrau.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import com.example.appbanrau.Adapter.AdapterSliderTrangChuUser;
import com.example.appbanrau.Adapter.AdapterTrangChuUser;
import com.example.appbanrau.DAO.SanPhamTrangChuDAO;
import com.example.appbanrau.DTO.SanPhamTrangChuUserDTO;
import com.example.appbanrau.DTO.SliderDTO;
import com.example.appbanrau.R;
import me.relex.circleindicator.CircleIndicator3;


public class FragTrangChuUser extends Fragment {

    private TextView tvXemThem,tvName;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private RecyclerView recyclerView;
    private AdapterTrangChuUser adapterTrangChuUser;
    private LinearLayout layoutRau,layoutCu,layoutQua,layoutHat;
    private SanPhamTrangChuDAO sanPhamTrangChuDAO;
    private  List<SanPhamTrangChuUserDTO> list;
    private EditText edTimKiemSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_trang_chu_user,container,false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvName = view.findViewById(R.id.tvNameTrangChuUser);
        viewPager2 = view.findViewById(R.id.viewPager2TrangChuUser);
        circleIndicator3 = view.findViewById(R.id.ci3);
        recyclerView = view.findViewById(R.id.rcvTrangChuUser);
        edTimKiemSanPham = view.findViewById(R.id.edTimKiemTrangChu);

        sanPhamTrangChuDAO = new SanPhamTrangChuDAO(getContext());

        //Hiển thi tên tài khoản
        String tenDangNhap = requireActivity().getIntent().getStringExtra("tenDangNhap");
        tvName.setText("Hi, "+tenDangNhap);



        //Khởi tạo adapter Cho slideShow
        AdapterSliderTrangChuUser userAdapter = new AdapterSliderTrangChuUser(getData());
        viewPager2.setAdapter(userAdapter);

        circleIndicator3.setViewPager(viewPager2);

        viewPager2.setOffscreenPageLimit(2);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(2));
        viewPager2.setPageTransformer(compositePageTransformer);


        //Set Data mẫu
        list = sanPhamTrangChuDAO.getAll();
        adapterTrangChuUser = new AdapterTrangChuUser(getContext(),list);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(),1
                        ,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterTrangChuUser);

        //Tìm kiếm sản phẩm
        edTimKiemSanPham.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String tenSp = s.toString();
                timKiemSanPham(tenSp);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        super.onViewCreated(view, savedInstanceState);
    }

    private void timKiemSanPham(String tenSp) {

       List<SanPhamTrangChuUserDTO> listSeach = sanPhamTrangChuDAO.timKiemSanPhamTrangChu(tenSp);

        if (listSeach.size() > 0) {

            adapterTrangChuUser = new AdapterTrangChuUser(getContext(),listSeach);

        }else {

            adapterTrangChuUser = new AdapterTrangChuUser(getContext(),list);

        }
        recyclerView.setAdapter(adapterTrangChuUser);
        adapterTrangChuUser.notifyDataSetChanged();


    }

    private List<SanPhamTrangChuUserDTO> getDataSanPham() {

        List<SanPhamTrangChuUserDTO> list = new ArrayList<>();

        return list;


    }

    private List<SliderDTO> getData() {

        List<SliderDTO> list = new ArrayList<>();

        list.add(new SliderDTO(R.drawable.img_slider9));
        list.add(new SliderDTO(R.drawable.img_slider8));
        list.add(new SliderDTO(R.drawable.img_slider1));
        list.add(new SliderDTO(R.drawable.img_slider5));
        list.add(new SliderDTO(R.drawable.img_slider6));
        list.add(new SliderDTO(R.drawable.img_slider7));


        return list;

    }
}
