package com.ntsua.thelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Love extends AppCompatActivity {

    ListView lvLove;
    FoodAdapter adapter;
    ArrayList<Food> arrLove;
    TextView txtName, txtJob, txtMoney;
    ImageView imgAvatar;
    ArrayList<QuanHe> arrRelationship;
    int numberOfBoy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        txtName = findViewById(R.id.textviewName);
        txtJob = findViewById(R.id.textviewJob);
        txtMoney = findViewById(R.id.textviewMoney);
        imgAvatar = findViewById(R.id.imageAvatar);
        loadGame();

        lvLove = (ListView) findViewById((R.id.listviewLove));
        arrLove = new ArrayList<>();
        arrLove.add(new Food("Mai mối", "Tìm người yêu với mức giá phải chăng - 300k một lần thử", R.drawable.maimoi, 300));
        adapter = new FoodAdapter(this, R.layout.food_line, arrLove);
        lvLove.setAdapter(adapter);

        lvLove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                {
                    if (MainActivity.saveGame.getMoney() >= arrLove.get(position).getPrice()) {
                        if (isNewFriend()) {
                            newFriend();
                            MainActivity.saveGame.saveMoney(MainActivity.saveGame.getMoney() - arrLove.get(position).getPrice());
                            txtMoney.setText(MainActivity.saveGame.getMoney() + " VND");
                        }
                    }
                    else MainActivity.createNotification(R.drawable.money,
                            "Nghèo như bạn ai mà thương",
                            Love.this);
                }
            }
        });

        numberOfBoy = countBoy();
    }

    private int countBoy() { //Đếm tổng số lượng bạn nam trong danh sách bạn bè được load lên từ json
        int count = 0;
        for (int i=0; i<MainActivity.arrFriend.size(); i++)
        {
            if (MainActivity.arrFriend.get(i).isBoy())
                count++;
        }

        return count;
    }

    boolean isNewFriend()
    {
        int friend = MainActivity.saveGame.getNumberOfGirlFriend(); //Số bạn nữ đã kết bạn
        int remain = (MainActivity.arrFriend.size() - numberOfBoy) - friend; //Số bạn nữ còn lại
        if (!MainActivity.saveGame.getGender()) { //Nếu là nữ thì tính số bạn nam còn lại
            friend = MainActivity.saveGame.getNumberOfFriends() - friend; //Tính số bạn nam đã kết bạn
            //Toast.makeText(this, MainActivity.saveGame.getNumberOfFriends() + " boy = " + friend, Toast.LENGTH_SHORT).show();
            remain = numberOfBoy - friend;
        }

        //Toast.makeText(this, "remain = " + remain, Toast.LENGTH_SHORT).show();
        if (remain > 0) // Nếu số bạn khác giới còn lại lớn hơn 0
            return true; //Có khả năng thêm bạn khác giới
        else MainActivity.createNotification(R.drawable.cancel, "Hiện tại trung tâm chưa tìm được người hợp với bạn", Love.this);
        return false;
    }

    void newFriend()
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_new_friend);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Anh xa
        TextView txtName = dialog.findViewById(R.id.textviewFriendName);
        TextView txtAge = dialog.findViewById(R.id.textviewFriendAge);
        ImageView imgAvatar = dialog.findViewById(R.id.imageViewNewFriend);
        Button btnAccept = dialog.findViewById(R.id.buttonDialogFriendAccept);
        Button btnCancel = dialog.findViewById(R.id.buttonDialogFriendCancel);

        QuanHe friend = whoIsNewFriend();

        txtName.setText(friend.getHoten());
        txtAge.setText(friend.getTuoi() +" Tuổi");
        imgAvatar.setImageResource(friend.getHinhAnh());

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrRelationship.add(friend);
                MainActivity.saveGame.saveRelationship(arrRelationship);
                MainActivity.createNotification(friend.getHinhAnh(),
                        "Bạn đã đồng ý tìm hểu với " + friend.getHoten(),
                        Love.this);
                MainActivity.saveGame.saveNumberOfFriends(MainActivity.saveGame.getNumberOfFriends() + 1);
                //Toast.makeText(Love.this, "Number of friends = " + MainActivity.saveGame.getNumberOfFriends(), Toast.LENGTH_SHORT).show();
                if (!friend.isBoy())
                    MainActivity.saveGame.saveNumberOfGirlFriend(MainActivity.saveGame.getNumberOfGirlFriend() + 1);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.createNotification(friend.getHinhAnh(),
                        "Bạn đã từ chối tìm hiểu với " + friend.getHoten(),
                        Love.this);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    QuanHe whoIsNewFriend() // random ra nguoi ban moi (Nguoi ay la ai ?)
    {
        QuanHe friend = null;
        Random random = new Random();

        boolean isBoy = MainActivity.saveGame.getGender();
        do {
            int index = random.nextInt(MainActivity.arrFriend.size());
            friend = MainActivity.arrFriend.get(index);
        }
        while (checkFriend(friend) || friend.isBoy() == MainActivity.saveGame.getGender());

        //Toast.makeText(this, friend.isBoy() + " - " + MainActivity.saveGame.getGender(), Toast.LENGTH_SHORT).show();
        return friend;
    }

    boolean checkFriend(QuanHe friend)
    {
        for (int i=0; i<arrRelationship.size(); i++)
        {
            if (friend.getHoten().equals(arrRelationship.get(i).getHoten()))
                return true;
        }
        return false;
    }

    private void loadGame() {
        txtName.setText(MainActivity.saveGame.getName());
        txtMoney.setText(MainActivity.saveGame.getMoney() + "K VND");
        txtJob.setText(MainActivity.saveGame.getJob());
        arrRelationship = MainActivity.saveGame.getRelationship();
        imgAvatar.setImageResource(MainActivity.saveGame.getAvatar());
    }

    public void gotoMainMenu(View view)
    {
        //startActivity(new Intent(Love.this, HoatDong.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);
        onBackPressed();
    }
}