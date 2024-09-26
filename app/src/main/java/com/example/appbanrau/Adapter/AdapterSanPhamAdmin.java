package com.example.appbanrau.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanrau.ChiTietSanPhamActivity;
import com.example.appbanrau.DTO.SanPhamAdminDTO;
import com.example.appbanrau.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSanPhamAdmin extends RecyclerView.Adapter<AdapterSanPhamAdmin.ViewHolderAdmin> {
    private ArrayList<SanPhamAdminDTO> list;
    private Context context;
    private SanPhamAdminInterface listener;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public AdapterSanPhamAdmin(ArrayList<SanPhamAdminDTO> list, Context context, SanPhamAdminInterface listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Cập nhật danh sách sản phẩm và làm mới RecyclerView.
     *
     * @param newList Danh sách sản phẩm mới.
     */
    public void updateList(ArrayList<SanPhamAdminDTO> newList) {
        list = newList;
        notifyDataSetChanged();
    }

    public interface SanPhamAdminInterface {
        void updateSanPham(SanPhamAdminDTO dto);
    }

    @NonNull
    @Override
    public ViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danh_sach_san_pham_admin, parent, false);
        return new ViewHolderAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdmin holder, @SuppressLint("RecyclerView") int position) {

        SanPhamAdminDTO id = list.get(position);

        String nameImg = list.get(position).getImg_url();
        int resourceImg = ((Activity) context).getResources().getIdentifier(nameImg, "drawable", ((Activity) context).getPackageName());
        holder.imgDanhSachSanPhamAdmin.setImageResource(resourceImg);

        String base64 = list.get(position).getImg_url();
        try {
            byte[] imageBytes = android.util.Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            if (bitmap != null) {
                holder.imgDanhSachSanPhamAdmin.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvGiaSanPhamDanhSachSanPhamAdmin.setText(decimalFormat.format(list.get(position).getDon_gia()) + " VND / 1kg");
        holder.tvTenDanhSachSanPhamAdmin.setText(list.get(position).getTen_san_pham());

        holder.tvSuaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, SuaSanPhamAdmin.class);
//                context.startActivity(intent);
                listener.updateSanPham(list.get(position));

            }
        });

        //Xem chi tiết sản phẩm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                remenBerProduct(id.getTen_san_pham(), id.getDon_gia(), id.getImg_url(), id.getMo_ta());
                context.startActivity(new Intent(context, ChiTietSanPhamActivity.class));


            }
        });


    }

    private void remenBerProduct(String tenSanPham, int donGia, String imgUrl, String moTa) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("PRODUCT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("tenSp", tenSanPham);
        editor.putInt("doGia", donGia);
        editor.putString("anhSp", imgUrl);
        editor.putString("moTa", moTa);

        editor.apply();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderAdmin extends RecyclerView.ViewHolder {
        ImageView imgDanhSachSanPhamAdmin;
        TextView tvTenDanhSachSanPhamAdmin, tvGiaSanPhamDanhSachSanPhamAdmin, tvSuaSanPham;


        public ViewHolderAdmin(@NonNull View itemView) {
            super(itemView);
            imgDanhSachSanPhamAdmin = itemView.findViewById(R.id.imgDanhSachSanPhamAdmin);
            tvTenDanhSachSanPhamAdmin = itemView.findViewById(R.id.tvTenDanhSachSanPhamAdmin);
            tvGiaSanPhamDanhSachSanPhamAdmin = itemView.findViewById(R.id.tvGiaSanPhamDanhSachSanPhamAdmin);
            tvSuaSanPham = itemView.findViewById(R.id.tvSuaSanPham);


        }
    }


}