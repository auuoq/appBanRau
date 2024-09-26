package com.example.appbanrau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.example.appbanrau.DAO.DonDatUserDAO;
import com.example.appbanrau.DAO.SanPhamTrangChuDAO;
import com.example.appbanrau.DTO.DonDatUserDTO;
import com.example.appbanrau.DTO.SanPhamTrangChuUserDTO;

public class ChiTietDonDatUserActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvTrangThai, tvTenKhach, tvSoDienThoai, tvDiaChi, tvDanhSachSp, tvTongTien, tvThoiGian, tvMaDonHang;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    AppCompatButton btnHuy;
    List<DonDatUserDTO> list;
    List<SanPhamTrangChuUserDTO> listSp;
    SanPhamTrangChuDAO sanPhamTrangChuDAO;
    DonDatUserDAO donDatUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_dat_user);
        ivBack = findViewById(R.id.ivBackChiTietDonDat);
        tvTenKhach = findViewById(R.id.tvTenNguoiNhan);
        tvSoDienThoai = findViewById(R.id.tvSDTChiTietDonDat);
        tvDiaChi = findViewById(R.id.tvDiaChiChiTietDonDat);
        tvDanhSachSp = findViewById(R.id.tvDanhSachSpChiTietDonDat);
        tvTongTien = findViewById(R.id.tvTongTienChiTietDonDat);
        tvThoiGian = findViewById(R.id.tvThoiGianDatHangChiTietDonHang);
        tvMaDonHang = findViewById(R.id.tvMaDonHangChiTietDonHang);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        btnHuy = findViewById(R.id.btnHuyDonChiTietDonDat);

        //Khởi tạo dao
        donDatUserDAO = new DonDatUserDAO(this);
        list = donDatUserDAO.donDat();


        //Nhận dữ liệu
        int id = getIntent().getIntExtra("idHoaDon", 0);
        String tenKhachHang = getIntent().getStringExtra("tenKhach");
        String soDienThoai = getIntent().getStringExtra("soDienThoai");
        String diaChi = getIntent().getStringExtra("diaChi");
        String tenSanPham = getIntent().getStringExtra("tenSanPham");
        int tongTien = getIntent().getIntExtra("tongTien", 0);
        String ngayDat = getIntent().getStringExtra("ngayDat");
        String trangThai = getIntent().getStringExtra("trangThai");

        Random random = new Random();
        String stringRamdon = "QWERTYUIOPJASDFGHJKZXCVBNMqwertyuiopkjhgfdsazxcvbnm";
        StringBuilder maRamDom = new StringBuilder();
        int soLuongRamdon = 8;
        for (int i = 0; i < soLuongRamdon; i++) {

            maRamDom.append(stringRamdon.charAt(random.nextInt(stringRamdon.length())));

        }

        //set cho các viewTrong Đơn đặt
        tvTenKhach.setText("Họ tên: " + tenKhachHang);
        tvSoDienThoai.setText("SĐT: " + soDienThoai);
        tvDiaChi.setText("Địa chỉ: " + diaChi);
        tvDanhSachSp.setText(tenSanPham);
        tvTongTien.setText("Tổng tiền: " + decimalFormat.format(tongTien) + " VND");
        tvThoiGian.setText(ngayDat);
        tvMaDonHang.setText(id + "" + maRamDom);
        tvTrangThai.setText(trangThai);

        //Kiểm tra trạng thái nếu là đang giao và hoàn thành
        // và đã hủy thì ẩn đi nút hủy ở chi tiết đơn đặt
        if (trangThai.equals("Hủy") || trangThai.equals("Đang giao hàng") || trangThai.equals("Đã thanh toán")) {

            btnHuy.setVisibility(View.GONE);

        }


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietDonDatUserActivity.this);
                View view1 = LayoutInflater
                        .from(ChiTietDonDatUserActivity.this)
                        .inflate(R.layout.dialog_xac_nhan, null, false);

                builder.setView(view1);

                AlertDialog dialog = builder.create();
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                AppCompatButton btnXacNhan, btnHuy;
                TextView tvNoiDung;
                btnHuy = view1.findViewById(R.id.btnHuyDialog);
                btnXacNhan = view1.findViewById(R.id.btnXacNhanDialog);
                tvNoiDung = view1.findViewById(R.id.tvNoiDungDialog);
                tvNoiDung.setText("Bạn có chắc muốn hủy đơn hàng không ?");

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DonDatUserDTO id = new DonDatUserDTO();
                        for (int i = 0; i < list.size(); i++) {

                            id = list.get(i);


                        }
                        id.setTrangThai("Hủy");

                        int kq = donDatUserDAO.updateTrangThai(id);

                        if (kq > 0) {

                            Toast.makeText(ChiTietDonDatUserActivity.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            list = donDatUserDAO.donDat();
                            dialog.dismiss();
                            onBackPressed();

                        } else {

                            Toast.makeText(ChiTietDonDatUserActivity.this, "Hủy thất bại", Toast.LENGTH_SHORT).show();


                        }

                    }
                });


                dialog.show();


            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


    }


}