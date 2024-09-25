package com.example.appbanrau;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.appbanrau.Fragment.FragDonDatHomeUser;
import com.example.appbanrau.Fragment.FragGioHangUser;
import com.example.appbanrau.Fragment.FragTaiKhoanAdmin;
import com.example.appbanrau.Fragment.FragTaiKhoanUser;
import com.example.appbanrau.Fragment.FragTrangChuUser;
import com.example.appbanrau.Fragment.FragmentDanhSachDonDatAdmin;
import com.example.appbanrau.Fragment.FragmentTrangChuAdmin;


public class MainActivity extends AppCompatActivity {


    private LinearLayout layoutTrangChuUser, layoutGioHangUser, layoutDonDatUser, layoutTaiKhoanUser;
    private LinearLayout layoutTrangChuAdmin, layoutThongKeAdmin, layoutDonDatAdmin, layoutTaiKhoanAdmin;
    private CardView layoutNavBottomAdmin, layoutNavBottomUser;
    private FragmentManager manager;
    private ImageView iconTrangChuUser, iconGioHangUser, iconDonDatUser, iconTaiKhoanUser;
    private ImageView iconTrangChuAdmin, iconThongKeAdmin, iconDonDatAdmin, iconTaiKhoanAdmin;

    private int index = 1; // Khởi tạo biến index bằng 1 để gán cho từng icon có trong navbottom


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Ánh xạ layout navbuttom admin và user
        layoutNavBottomAdmin = findViewById(R.id.layoutNavBottomAdmin);
        layoutNavBottomUser = findViewById(R.id.layoutNavBottomUser);

        //Khoải tạo fragment menager
        manager = getSupportFragmentManager();

        //Phẩm quyền admin và user
        //Nhận tên đăng nhập
        String tenDangNhap = getIntent().getStringExtra("tenDangNhap");
        String matKhau = getIntent().getStringExtra("matKhau");

        //xư ly lay ten dang nhap
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", tenDangNhap);
        editor.putString("PassOld", matKhau);
        editor.apply();




        assert tenDangNhap != null;
        if (tenDangNhap.equals("admin")) {
            setupAdminLayout();
        } else {
            setupUserLayout();
        }
    }

    private void setupAdminLayout() {
        layoutNavBottomAdmin.setVisibility(View.VISIBLE);
        layoutNavBottomUser.setVisibility(View.GONE);
        manager.beginTransaction().replace(R.id.fragContainerView, new FragmentTrangChuAdmin()).commit();

        // Ánh xạ layout admin
        layoutTrangChuAdmin = findViewById(R.id.layoutTrangChuAdmin);
        layoutDonDatAdmin = findViewById(R.id.layoutDonDatAdmin);
        layoutTaiKhoanAdmin = findViewById(R.id.layoutTaiKhoanAdmin);

        // Ánh xạ icon admin
        iconTrangChuAdmin = findViewById(R.id.iconTrangChuAdmin);
        iconDonDatAdmin = findViewById(R.id.iconDonDatAdmin);
        iconTaiKhoanAdmin = findViewById(R.id.iconTaiKhoanAdmin);

        // Xử lý sự kiện click cho admin
        layoutTrangChuAdmin.setOnClickListener(view -> handleAdminNavigation(1, new FragmentTrangChuAdmin(), iconTrangChuAdmin));
        layoutDonDatAdmin.setOnClickListener(view -> handleAdminNavigation(2, new FragmentDanhSachDonDatAdmin(), iconDonDatAdmin));
        layoutTaiKhoanAdmin.setOnClickListener(view -> handleAdminNavigation(3, new FragTaiKhoanAdmin(), iconTaiKhoanAdmin));
    }

    private void setupUserLayout() {
        layoutNavBottomAdmin.setVisibility(View.GONE);
        layoutNavBottomUser.setVisibility(View.VISIBLE);
        manager.beginTransaction().replace(R.id.fragContainerView, new FragTrangChuUser()).commit();

        // Ánh xạ layout user
        layoutTrangChuUser = findViewById(R.id.layoutTrangChuUser);
        layoutGioHangUser = findViewById(R.id.layouGioiHangUser);
        layoutDonDatUser = findViewById(R.id.layoutDonDatUser);
        layoutTaiKhoanUser = findViewById(R.id.layoutTaiKhoanUser);

        // Ánh xạ icon user
        iconTrangChuUser = findViewById(R.id.iconTrangChu);
        iconGioHangUser = findViewById(R.id.iconGioHang);
        iconDonDatUser = findViewById(R.id.iconDonDat);
        iconTaiKhoanUser = findViewById(R.id.iconTaiKhoan);

        // Xử lý sự kiện click cho user
        layoutTrangChuUser.setOnClickListener(view -> handleUserNavigation(1, new FragTrangChuUser(), iconTrangChuUser));
        layoutGioHangUser.setOnClickListener(view -> handleUserNavigation(2, new FragGioHangUser(), iconGioHangUser));
        layoutDonDatUser.setOnClickListener(view -> handleUserNavigation(3, new FragDonDatHomeUser(), iconDonDatUser));
        layoutTaiKhoanUser.setOnClickListener(view -> handleUserNavigation(4, new FragTaiKhoanUser(), iconTaiKhoanUser));
    }

    private void handleAdminNavigation(int newIndex, androidx.fragment.app.Fragment fragment, ImageView selectedIcon) {
        if (index != newIndex) {
            manager.beginTransaction().replace(R.id.fragContainerView, fragment).commit();
            resetAdminIcons();
            selectedIcon.setColorFilter(getResources().getColor(R.color.selected_icon_color));
            index = newIndex;
        }
    }

    private void resetAdminIcons() {
        iconTrangChuAdmin.setColorFilter(getResources().getColor(R.color.default_icon_color));
        iconDonDatAdmin.setColorFilter(getResources().getColor(R.color.default_icon_color));
        iconTaiKhoanAdmin.setColorFilter(getResources().getColor(R.color.default_icon_color));
    }

    private void handleUserNavigation(int newIndex, androidx.fragment.app.Fragment fragment, ImageView selectedIcon) {
        if (index != newIndex) {
            manager.beginTransaction().replace(R.id.fragContainerView, fragment).commit();
            resetUserIcons();
            selectedIcon.setColorFilter(getResources().getColor(R.color.selected_icon_color));
            index = newIndex;
        }
    }

    private void resetUserIcons() {
        iconTrangChuUser.setColorFilter(getResources().getColor(R.color.default_icon_color));
        iconGioHangUser.setColorFilter(getResources().getColor(R.color.default_icon_color));
        iconDonDatUser.setColorFilter(getResources().getColor(R.color.default_icon_color));
        iconTaiKhoanUser.setColorFilter(getResources().getColor(R.color.default_icon_color));
    }
}