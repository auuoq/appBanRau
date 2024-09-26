package com.example.appbanrau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.appbanrau.DBHelper.MyDBHelper;
import com.example.appbanrau.DTO.SanPhamAdminDTO;

public class TrangChuAdminDAO {
    MyDBHelper myDBHelper;

    public TrangChuAdminDAO(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    // Lấy danh sách tất cả các sản phẩm
    public ArrayList<SanPhamAdminDTO> getDSSanPhamAdmin() {
        ArrayList<SanPhamAdminDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDBHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tb_san_pham", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPhamAdminDTO(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Thêm sản phẩm mới
    public boolean ThemSanPham(String tensanpham, int dongia, String img, String mota, String loai, String nhacungcap) {
        SQLiteDatabase sqLiteDatabase = myDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ten_san_pham", tensanpham);
        values.put("don_gia", dongia);
        values.put("img_url", img);
        values.put("mo_ta", mota);
        values.put("loai", loai);
        values.put("nhacungcap", nhacungcap);

        long check = sqLiteDatabase.insert("tb_san_pham", null, values);
        return check != -1;
    }

    // Sửa thông tin sản phẩm
    public boolean SuaSanPham(int id_san_pham, String tensanpham, int dongia, String mo_ta, String loai) {
        SQLiteDatabase sqLiteDatabase = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_san_pham", tensanpham);
        values.put("don_gia", dongia);
        values.put("mo_ta", mo_ta);
        values.put("loai", loai);

        int check = sqLiteDatabase.update("tb_san_pham", values, "id_san_pham = ?", new String[]{String.valueOf(id_san_pham)});
        return check != 0;
    }

    // Tìm kiếm sản phẩm theo tên và loại (nếu có)
    public ArrayList<SanPhamAdminDTO> timKiemSanPham(String tenSp) {
        ArrayList<SanPhamAdminDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDBHelper.getReadableDatabase();

        // Truy vấn chỉ tìm theo tên sản phẩm
        String query = "SELECT * FROM tb_san_pham WHERE ten_san_pham LIKE ?";
        String[] args = new String[]{"%" + tenSp + "%"};

        Cursor cursor = sqLiteDatabase.rawQuery(query, args);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPhamAdminDTO(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


}
