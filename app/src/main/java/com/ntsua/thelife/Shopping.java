package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.ProtectionDomain;
import java.util.ArrayList;

public class Shopping extends AppCompatActivity {

    ListView lvShopping;
    ArrayList<Food> arrShopping;
    ArrayList<Food> arrAsset;
    FoodAdapter adapter;
    TextView txtMoney, txtJob, txtName;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        txtMoney = findViewById(R.id.textviewMoney);
        txtJob = findViewById(R.id.textviewJob);
        txtName = findViewById(R.id.textviewName);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvShopping = findViewById(R.id.listviewShopping);

        arrAsset = MainActivity.saveGame.getAsset();
        if  (arrAsset == null)
            arrAsset = new ArrayList<>();
        arrShopping = new ArrayList<>();
        arrShopping.add(new Food("Phương tiện đi lại","Công cụ di chuyển",R.drawable.vehicle,0));
        arrShopping.add(new Food("Bất động sản","Tài sản đứng im",R.drawable.house,0));
        arrShopping.add(new Food("Công cụ","Dụng cụ tổng hợp",R.drawable.tool,0));
        arrShopping.add(new Food("Hàng nóng","Hàng hoá trái phép",R.drawable.weapon,0));
        adapter = new FoodAdapter(this,R.layout.food_line, arrShopping);
        lvShopping.setAdapter(adapter);

        lvShopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrShopping.get(position).getFoodName() == "Phương tiện đi lại")
                {
                    ArrayList<Food> arrVehicle = new ArrayList<>();
                    arrVehicle.add(new Food("Siêu xe","Tốc độ cao hơn, mẫu mã đẹp hơn nhưng mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 1 tỷ 2 VND",R.drawable.supercar,1200000));
                    arrVehicle.add(new Food("Ô tô","Tốc độ cao, mẫu mã bình thường, mỗi tháng tốn thêm 1 khoản trung bình chi phí bảo quản. GIÁ 120 triệu VND",R.drawable.car,120000));
                    arrVehicle.add(new Food("Vespa","Tốc độ trung bình, mẫu mã bình thường, mỗi tháng tốn thêm 1 khoản ít chi phí bảo quản. GIÁ 30 triệu VND",R.drawable.vespa,30000));
                    arrVehicle.add(new Food("Xe thể thao","Tốc độ cao, mẫu mã đẹp, mỗi tháng tốn thêm 1 khoản trung bình chi phí bảo quản. GIÁ 90 triệu VND",R.drawable.motorcycle,90000));
                    arrVehicle.add(new Food("Trực thăng","Di chuyển trên không, mỗi tháng tốn thêm 1 khoảng trung bình chi phí bảo quản. GIÁ 1 tỷ 5 VND",R.drawable.helicopter,1500000));
                    arrVehicle.add(new Food("Máy bay","Di chuyển trên không, mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 2 tỷ VND",R.drawable.airplane,2000000));
                    arrVehicle.add(new Food("Tàu","Di chuyển trên mặt nước, mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 2 tỷ VND",R.drawable.boat,2000000));
                    CreateDialogMuaSam(arrVehicle,"SHOP PHƯƠNG TIỆN");
                }
                if(arrShopping.get(position).getFoodName() == "Bất động sản")
                {
                    ArrayList<Food> arrHouse = new ArrayList<>();
                    arrHouse.add(new Food("Cửa hàng nhỏ","Cửa hàng buôn bán nhỏ, giày dép quần áo,... GIÁ 3 tỷ VND",R.drawable.store,3000000));
                    arrHouse.add(new Food("Quán cà phê","Cung cấp các loại thức uống như cà phê, trà sữa, cappuchiano,... GIÁ 5 tỷ VND",R.drawable.coffeeshop,5000000));
                    arrHouse.add(new Food("Quán ăn","Bán cơm, bán cháo, bán bún,... GIÁ 4 tỷ VND",R.drawable.bistro,4000000));
                    arrHouse.add(new Food("Chung cư","Mua căn hộ có sẵn trong chung cư, mỗi tháng tốn thêm 1 khoản ít chi phí bảo quản. GIÁ 500 triệu VND",R.drawable.chungcu,500000));
                    arrHouse.add(new Food("Nhà ở","Cung cấp chỗ che mưa gió, mỗi tháng tốn thêm 1 khoảng trung bình chi phí bảo quản. GIÁ 5 tỷ VND",R.drawable.simplehouse,5000000));
                    arrHouse.add(new Food("Vinh thự","Thiết kế sang trọng, hiện đại, mỗi tháng tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 50 tỷ VND",R.drawable.masion,50000000));
                    arrHouse.add(new Food("Công ty","Công ty tư nhân, mỗi tháng tăng thêm thu nhập, tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 100 tỷ VND",R.drawable.company,100000000));
                    arrHouse.add(new Food("Nhà máy","Nhà máy sản xuất, mỗi tháng tăng thêm thu nhập, tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 150 tỷ VND",R.drawable.factory,150000000));
                    CreateDialogMuaSam(arrHouse,"MUA BÁN ĐẤT");
                }
                if(arrShopping.get(position).getFoodName() == "Công cụ")
                {
                    ArrayList<Food> arrTool = new ArrayList<>();
                    arrTool.add(new Food("laptop","Máy tính xách tay, tiện cho việc di chuyển, cấu hình trung bình. GIÁ 10.000.000 VND",R.drawable.laptop,10000));
                    arrTool.add(new Food("Webcam","Công cụ hữu ích cho các streamer. GIÁ 200.000 VND",R.drawable.webcam,200));
                    arrTool.add(new Food("PC","Máy tính để bàn, cấu hình cao, phù hợp cho việc stream. GIÁ 30.000.000 VND",R.drawable.pc,30000));
                    arrTool.add(new Food("Smartphone","Điện thoại thông minh, mang lại nhiều tiện ích cho người dùng. GIÁ 5.000.000 VND",R.drawable.smartphones,5000));
                    arrTool.add(new Food("Bộ vẽ","Bao gồm cọ vẽ, thùng sơn, giá để. GIÁ 1.000.000 VND",R.drawable.painttool,1000));
                    arrTool.add(new Food("Bộ công cụ","Bao gồm tua vít, cờ-lê,...GIÁ 500.000 VND",R.drawable.toolbox,500));
                    arrTool.add(new Food("Giày thể thao","Đôi giày được may vá tỉ mỉ, chất lượng cao cấp. GIÁ 500.000 VND",R.drawable.runningshoes,500));
                    CreateDialogMuaSam(arrTool,"SHOP ĐỒ NGHỀ");
                }
                if(arrShopping.get(position).getFoodName() == "Hàng nóng")
                {
                    ArrayList<Food> arrWeapon = new ArrayList<>();
                    arrWeapon.add(new Food("Dao","GIÁ 50.000 VND",R.drawable.dao,50));
                    arrWeapon.add(new Food("Thuốc độc","GIÁ 300.000 VND",R.drawable.poison,200));
                    arrWeapon.add(new Food("Súng","GIÁ 10.000.000 VND",R.drawable.gun,10000));
                    arrWeapon.add(new Food("Mã tấu","GIÁ 5.000.000 VND",R.drawable.matau,5000));
                    CreateDialogHangNong(arrWeapon,"HÀNG NÓNG");
                }
            }
        });

    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    private void CreateDialogHangNong(ArrayList<Food> arrProduct,String text)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_hangnong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        overridePendingTransition(R.anim.enter, R.anim.exit);
        ListView lvProduct = dialog.findViewById(R.id.lvHangnong);
        TextView tvCuahang = dialog.findViewById(R.id.tvCuahang);
        Button btnRoi = dialog.findViewById(R.id.btn_Roi);

        tvCuahang.setText(text);
        CrimeAdapter adapterPro = new CrimeAdapter(this,R.layout.crime,arrProduct);
        lvProduct.setAdapter(adapterPro);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.saveGame.getMoney() >= arrProduct.get(3).getPrice()) {

                    int money = MainActivity.saveGame.getMoney() - arrProduct.get(3).getPrice();
                    MainActivity.saveGame.saveMoney(money);
                    loadGame();
                    arrAsset.add(arrProduct.get(position));
                    MainActivity.saveGame.saveAsset(arrAsset);
                    MainActivity.createNotification(R.drawable.payment,"Thanh toán thành công", Shopping.this);
                } else createDialogOFM();
            }
        });

        btnRoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void CreateDialogMuaSam(ArrayList<Food> arrProduct, String text)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_muasam);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ListView lvProduct = dialog.findViewById(R.id.lvmuasam);
        TextView tvCuahang = dialog.findViewById(R.id.tvCuahang);
        Button btnRoi = dialog.findViewById(R.id.btn_Roi);

        overridePendingTransition(R.anim.enter, R.anim.exit);
        tvCuahang.setText(text);
        FoodAdapter adapterPro = new FoodAdapter(this,R.layout.food_line,arrProduct);
        lvProduct.setAdapter(adapterPro);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MainActivity.saveGame.getMoney() >= arrProduct.get(3).getPrice())
                {
                    int money = MainActivity.saveGame.getMoney() - arrProduct.get(3).getPrice();
                    MainActivity.saveGame.saveMoney(money);
                    
                    arrAsset.add(arrProduct.get(position));
                    MainActivity.saveGame.saveAsset(arrAsset);
                    loadGame();
                    MainActivity.createNotification(R.drawable.payment,"Thanh toán thành công", Shopping.this);
                } else createDialogOFM();
            }
        });

        btnRoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void createDialogOFM() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_out_of_money);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout dialogResult = dialog.findViewById(R.id.dialog_event_result);
        Button btnOke = dialog.findViewById(R.id.buttonDialogEventOke);
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void gotoMainMenu(View view)
    {
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }

}