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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.ProtectionDomain;
import java.util.ArrayList;

public class Shopping extends AppCompatActivity {

    ListView lvShopping;
    ArrayList<Food> arrShopping;
    ArrayList<product> arrAsset;
    FoodAdapter adapter;
    TextView txtMoney, txtJob, txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        txtMoney = findViewById(R.id.textviewMoney);
        txtJob = findViewById(R.id.textviewJob);
        txtName = findViewById(R.id.textviewName);
        loadGame();

        lvShopping = findViewById(R.id.listviewShopping);

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
                    ArrayList<product> arrVehicle = new ArrayList<>();
                    arrVehicle.add(new product("PT01","Siêu xe","Tốc độ cao hơn, mẫu mã đẹp hơn nhưng mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 1.200.000.000 VND",R.drawable.supercar,1200000));
                    arrVehicle.add(new product("PT02","Ô tô","Tốc độ cao, mẫu mã bình thường, mỗi tháng tốn thêm 1 khoản trung bình chi phí bảo quản. GIÁ 120.000.000 VND",R.drawable.car,120000));
                    arrVehicle.add(new product("PT03","Vespa","Tốc độ trung bình, mẫu mã bình thường, mỗi tháng tốn thêm 1 khoản ít chi phí bảo quản. GIÁ 30.000.000 VND",R.drawable.vespa,30000));
                    arrVehicle.add(new product("PT04","Xe thể thao","Tốc độ cao, mẫu mã đẹp, mỗi tháng tốn thêm 1 khoản trung bình chi phí bảo quản. GIÁ 90.000.000 VND",R.drawable.motorcycle,90000));
                    arrVehicle.add(new product("PT05","Trực thăng","Di chuyển trên không, mỗi tháng tốn thêm 1 khoảng trung bình chi phí bảo quản. GIÁ 1.500.000.000 VND",R.drawable.helicopter,1500000));
                    arrVehicle.add(new product("PT06","Máy bay","Di chuyển trên không, mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 2.000.000.000 VND",R.drawable.airplane,2000000));
                    arrVehicle.add(new product("PT07","Tàu","Di chuyển trên mặt nước, mỗi tháng tốn thêm 1 khoảng nhiều chi phí bảo quản. GIÁ 2.000.000.000 VND",R.drawable.boat,2000000));
                    CreateDialogMuaSam(arrVehicle,"SHOP PHƯƠNG TIỆN");
                }
                if(arrShopping.get(position).getFoodName() == "Bất động sản")
                {
                    ArrayList<product> arrHouse = new ArrayList<>();
                    arrHouse.add(new product("DD01","Chung cư","Mua căn hộ có sẵn trong chung cư, mỗi tháng tốn thêm 1 khoản ít chi phí bảo quản. GIÁ 500.000.000 VND",R.drawable.chungcu,500000));
                    arrHouse.add(new product("DD02","Nhà ở","Cung cấp chỗ che mưa gió, mỗi tháng tốn thêm 1 khoảng trung bình chi phí bảo quản. GIÁ 5.000.000.000 VND",R.drawable.simplehouse,5000000));
                    arrHouse.add(new product("DD03","Vinh thự","Thiết kế sang trọng, hiện đại, mỗi tháng tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 50.000.000.000 VND",R.drawable.masion,50000000));
                    arrHouse.add(new product("DD04","Công ty","Công ty tư nhân, mỗi tháng tăng thêm thu nhập, tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 100.000.000.000 VND",R.drawable.company,100000000));
                    arrHouse.add(new product("DD05","Nhà máy","Nhà máy sản xuất, mỗi tháng tăng thêm thu nhập, tốn thêm 1 khoản nhiều chi phí bảo quản. GIÁ 150.000.000.000 VND",R.drawable.factory,150000000));
                    CreateDialogMuaSam(arrHouse,"MUA BÁN ĐẤT");
                }
                if(arrShopping.get(position).getFoodName() == "Công cụ")
                {
                    ArrayList<product> arrTool = new ArrayList<>();
                    arrTool.add(new product("NN01","laptop","Máy tính xách tay, tiện cho việc di chuyển, cấu hình trung bình. GIÁ 10.000.000 VND",R.drawable.laptop,10000));
                    arrTool.add(new product("NN02","Webcam","Công cụ hữu ích cho các streamer. GIÁ 200.000 VND",R.drawable.webcam,200));
                    arrTool.add(new product("NN03","PC","Máy tính để bàn, cấu hình cao, phù hợp cho việc stream. GIÁ 30.000.000 VND",R.drawable.pc,30000));
                    arrTool.add(new product("NN04","Smartphone","Điện thoại thông minh, mang lại nhiều tiện ích cho người dùng. GIÁ 5.000.000 VND",R.drawable.smartphones,5000));
                    arrTool.add(new product("NN05","Bộ vẽ","Bao gồm cọ vẽ, thùng sơn, giá để. GIÁ 1.000.000 VND",R.drawable.painttool,1000));
                    arrTool.add(new product("NN06","Bộ công cụ","Bao gồm tua vít, cờ-lê,...GIÁ 500.000 VND",R.drawable.toolbox,500));
                    arrTool.add(new product("NN07","Giày thể thao","Đôi giày được may vá tỉ mỉ, chất lượng cao cấp. GIÁ 500.000 VND",R.drawable.runningshoes,500));
                    CreateDialogMuaSam(arrTool,"SHOP ĐỒ NGHỀ");
                }
                if(arrShopping.get(position).getFoodName() == "Hàng nóng")
                {
                    ArrayList<product> arrWeapon = new ArrayList<>();
                    arrWeapon.add(new product("VK01","Dao","GIÁ 50.000 VND",R.drawable.dao,50));
                    arrWeapon.add(new product("VK02","Thuốc độc","GIÁ 300.000 VND",R.drawable.poison,200));
                    arrWeapon.add(new product("VK03","Súng","GIÁ 10.000.000 VND",R.drawable.gun,10000));
                    arrWeapon.add(new product("VK03","Mã tấu","GIÁ 5.000.000 VND",R.drawable.matau,5000));
                    CreateDialogHangNong(arrWeapon,"HÀNG NÓNG");
                }
            }
        });

    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "VND");
        txtJob.setText(MainActivity.saveGame.getJob());
    }

    private void CreateDialogHangNong(ArrayList<product> arrProduct,String text)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_hangnong);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ListView lvProduct = dialog.findViewById(R.id.lvHangnong);
        TextView tvCuahang = dialog.findViewById(R.id.tvCuahang);
        Button btnRoi = dialog.findViewById(R.id.btn_Roi);

        tvCuahang.setText(text);
        WeaponAdapter adapterPro = new WeaponAdapter(this,R.layout.crime,arrProduct);
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
                }
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

    private void CreateDialogMuaSam(ArrayList<product> arrProduct, String text)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_muasam);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ListView lvProduct = dialog.findViewById(R.id.lvmuasam);
        TextView tvCuahang = dialog.findViewById(R.id.tvCuahang);
        Button btnRoi = dialog.findViewById(R.id.btn_Roi);

        tvCuahang.setText(text);
        productAdapter adapterPro = new productAdapter(this,R.layout.food_line,arrProduct);
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
                    Toast.makeText(Shopping.this, "Bạn đã mua thành công", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(Shopping.this, "Bạn không đủ tiền", Toast.LENGTH_SHORT).show();
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

    public void gotoMainMenu(View view)
    {
        startActivity(new Intent(Shopping.this, HoatDong.class));
    }

}