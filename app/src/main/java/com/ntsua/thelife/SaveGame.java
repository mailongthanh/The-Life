package com.ntsua.thelife;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveGame {

    private LoadDone loadDone;
    private DatabaseReference reference;
    private PlayerBasicInfomation infomation;
    private ArrayList<QuanHe> arrRelationship;
    private ArrayList<Food> arrAsset;
    private ArrayList<Sick> arrSick;

    boolean isOnCreate = true;

    public SaveGame()
    {

//        if (getDetailActivity().isEmpty())
//            reference.child("Basic").setValue(infomation);
    }

    public void loadData()
    {
        if (!isOnCreate)
        {
            loadDone.onLoaded();
            return;
        }

        assert FirebaseAuth.getInstance().getUid() != null;
        reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());

        arrAsset = new ArrayList<>();
        arrRelationship = new ArrayList<>();
        arrSick = new ArrayList<>();
        infomation = new PlayerBasicInfomation();

        reference.child("Basic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                infomation = snapshot.getValue(PlayerBasicInfomation.class);
                if (infomation == null) {
                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    reference.child("Basic").setValue(new PlayerBasicInfomation());
                    reference.child("Information").child("PhotoUri").setValue(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                    reference.child("Information").child("Name").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                }
                else if (isOnCreate){
                    loadDone.onLoaded();
                    isOnCreate = false;
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        reference.child("Asset").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Food>> objectsGTypeInd = new GenericTypeIndicator<ArrayList<Food>>() {};
                arrAsset = snapshot.getValue(objectsGTypeInd);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        reference.child("Relationship").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<QuanHe>> objectsGTypeInd = new GenericTypeIndicator<ArrayList<QuanHe>>() {};
                arrRelationship = snapshot.getValue(objectsGTypeInd);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        reference.child("Sick").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Sick>> objectsGTypeInd = new GenericTypeIndicator<ArrayList<Sick>>() {};
                arrSick = snapshot.getValue(objectsGTypeInd);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void setOnLoaded(LoadDone loadDone)
    {
        this.loadDone = loadDone;
    }

    public void saveDetailActivity(String detail)
    {
        infomation.setDetailActivity(detail);
        reference.child("Basic").child("detailActivity").setValue(detail);
    }

    public String getDetailActivity() {

        return infomation.getDetailActivity();
    }

    public void saveBirthDay(String day)
    {
        infomation.setBirthday(day);
        reference.child("Basic").child("birthday").setValue(day);
    }

    public String getBirthDay()
    {
        return infomation.getBirthday();
    }

    public void saveAvatar(int id)
    {
        infomation.setAvatar(id);
        reference.child("Basic").child("avatar").setValue(id);
    }

    public int getAvatar()
    {
        return infomation.getAvatar();
    }

    public void saveDating(boolean isDating)
    {
        infomation.setDating(isDating);
        reference.child("Basic").child("dating").setValue(isDating);
    }

    public boolean getDating()
    {
        return infomation.isDating();
    }

    public  void saveEnglish(boolean english)
    {
        infomation.setEnglish(english);
        reference.child("Basic").child("english").setValue(english);
    }

    public boolean getEnglish()
    {
        return infomation.isEnglish();
    }

    public boolean getDriving()
    {
        return infomation.isDriving();
    }

    public void saveDriving(boolean driving)
    {
        infomation.setDriving(driving);
        reference.child("Basic").child("driving").setValue(driving);
    }

    public void saveCrime(int time) {
        infomation.setCrime(time);
        reference.child("Basic").child("crime").setValue(time);
    }

    public int getCrime()
    {
        return infomation.getCrime();
    }

    public String getName() {
        return infomation.getName();
    }

    public void saveName(String name) {
        infomation.setName(name);
        reference.child("Basic").child("name").setValue(name);
    }

    public int getAge() {
        return infomation.getAge();
    }

    public void saveAge(int age) {
        infomation.setAge(age);
        reference.child("Basic").child("age").setValue(age);
    }

    public boolean getGender() {
        return infomation.isBoy();
    }

    public void saveGender(boolean boy) {
        infomation.setBoy(boy);
        reference.child("Basic").child("boy").setValue(boy);
    }


    public String getJob() {
        return infomation.getJob();
    }

    public void saveJob(String job) {
        infomation.setJob(job);
        reference.child("Basic").child("job").setValue(job);
    }

    public int getMoney() {
        return infomation.getMoney();
    }

    public void saveMoney(int money) {
        infomation.setMoney(money);
        reference.child("Basic").child("money").setValue(money);
    }

    public void savePlayerInfo(int happy, int health, int smart, int appearance)
    {
        infomation.setHappy(happy);
        reference.child("Basic").child("happy").setValue(happy);

        infomation.setHealth(health);
        reference.child("Basic").child("health").setValue(health);

        infomation.setSmart(smart);
        reference.child("Basic").child("smart").setValue(smart);

        infomation.setAppearance(appearance);
        reference.child("Basic").child("appearance").setValue(appearance);
    }

    public int getHappy() {
        return infomation.getHappy();
    }

    public int getHealth() {
        return infomation.getHealth();
    }

    public int getSmart() {
        return infomation.getSmart();
    }

    public int getAppearance() {
        return infomation.getAppearance();
    }

    public int getSalary() {
        return infomation.getSalary();
    }

    public void saveSalary(int salary) {
        infomation.setSalary(salary);
        reference.child("Basic").child("salary").setValue(salary);
    }

    public int getSkill() {
        return infomation.getSkill();
    }

    public void saveSkill(int skill) {
        infomation.setSkill(skill);
        reference.child("Basic").child("skill").setValue(skill);
    }

    public int getNumberOfFriends() {
        return infomation.getNumberOfFriend();
    }

    public void saveNumberOfFriends(int numberOfFriend) {
        infomation.setNumberOfFriend(numberOfFriend);
        reference.child("Basic").child("numberOfFriend").setValue(numberOfFriend);
    }

    public int getNumberOfGirlFriend() {
        return infomation.getNumberOfGirlFriend();
    }

    public void saveNumberOfGirlFriend(int numberOfGirlFriend) {
        infomation.setNumberOfGirlFriend(numberOfGirlFriend);
        reference.child("Basic").child("numberOfGirlFriend").setValue(numberOfGirlFriend);
    }

    public int getNewFriendInYear() {
        return infomation.getNewFriendInYear();
    }

    public void saveNewFriendInYear(int newFriendInYear) {
        infomation.setSalary(newFriendInYear);
        reference.child("Basic").child("newFriendInYear").setValue(newFriendInYear);
    }

    public int getJogging() {
        return infomation.getJogging();
    }

    public void saveJogging(int jogging) {
        infomation.setJogging(jogging);
        reference.child("Basic").child("jogging").setValue(jogging);
    }

    public int getExercise() {
        return infomation.getExercise();
    }

    public void saveExercise(int exercise) {
        infomation.setExercise(exercise);
        reference.child("Basic").child("exercise").setValue(exercise);
    }

    public int getLibrary() {
        return infomation.getLibrary();
    }

    public void saveLibrary(int library) {
        infomation.setLibrary(library);
        reference.child("Basic").child("library").setValue(library);
    }

    public int getNhaHang() {
        return infomation.getNhaHang();
    }

    public void saveNhaHang(int nhaHang) {
        infomation.setNhaHang(nhaHang);
        reference.child("Basic").child("nhaHang").setValue(nhaHang);
    }

    public int getNuocEp() {
        return infomation.getNuocEp();
    }

    public void saveNuocEp(int nuocEp) {
        infomation.setNuocEp(nuocEp);
        reference.child("Basic").child("nuocEp").setValue(nuocEp);
    }

    public int getTraSua() {
        return infomation.getTraSua();
    }

    public void saveTraSua(int traSua) {
        infomation.setTraSua(traSua);
        reference.child("Basic").child("traSua").setValue(traSua);
    }

    public int getCaPhe() {
        return infomation.getCaPhe();
    }

    public void saveCaPhe(int caPhe) {
        infomation.setCaPhe(caPhe);
        reference.child("Basic").child("caPhe").setValue(caPhe);
    }

    public int getRuou() {
        return infomation.getRuou();
    }

    public void saveRuou(int ruou) {
        infomation.setRuou(ruou);
        reference.child("Basic").child("ruou").setValue(ruou);
    }

    public int getBia() {
        return infomation.getBia();
    }

    public void saveBia(int bia) {
        infomation.setBia(bia);
        reference.child("Basic").child("bia").setValue(bia);
    }

    public int getHamBurGer() {
        return infomation.getHamburger();
    }

    public void saveHamBurGer(int hamburger) {
        infomation.setHamburger(hamburger);
        reference.child("Basic").child("hamburger").setValue(hamburger);
    }

    public int getBanhMi() {
        return infomation.getBanhMi();
    }

    public void saveBanhMi(int banhMi) {
        infomation.setBanhMi(banhMi);
        reference.child("Basic").child("banhMi").setValue(banhMi);
    }

    public int getMy() {
        return infomation.getMi();
    }

    public void saveMy(int mi) {
        infomation.setMi(mi);
        reference.child("Basic").child("mi").setValue(mi);
    }

    public int getTraiCay() {
        return infomation.getTraiCay();
    }

    public void saveTraiCay(int traiCay) {
        infomation.setTraiCay(traiCay);
        reference.child("Basic").child("traiCay").setValue(traiCay);
    }

    public int getPizza() {
        return infomation.getPizza();
    }

    public void savePizza(int pizza) {
        infomation.setPizza(pizza);
        reference.child("Basic").child("pizza").setValue(pizza);
    }

    public int getLau() {
        return infomation.getLau();
    }

    public void saveLau(int lau) {
        infomation.setLau(lau);
        reference.child("Basic").child("lau").setValue(lau);
    }

    public int getCom() {
        return infomation.getCom();
    }

    public void saveCom(int com) {
        infomation.setCom(com);
        reference.child("Basic").child("com").setValue(com);
    }

    public int getHaiSan() {
        return infomation.getHaiSan();
    }

    public void saveHaiSan(int haiSan) {
        infomation.setHaiSan(haiSan);
        reference.child("Basic").child("haiSan").setValue(haiSan);
    }

    public int getGa() {
        return infomation.getGa();
    }

    public void saveGa(int ga) {
        infomation.setGa(ga);
        reference.child("Basic").child("ga").setValue(ga);
    }

    public int getRauCu() {
        return infomation.getRauCu();
    }

    public void saveRauCu(int rauCu) {
        infomation.setRauCu(rauCu);
        reference.child("Basic").child("rauCu").setValue(rauCu);
    }

    public int getKeo() {
        return infomation.getKeo();
    }

    public void saveKeo(int keo) {
        infomation.setKeo(keo);
        reference.child("Basic").child("keo").setValue(keo);
    }

    public int getFastFood() {
        return infomation.getThucAnNhanh();
    }

    public void saveFastFood(int thucAnNhanh) {
        infomation.setThucAnNhanh(thucAnNhanh);
        reference.child("Basic").child("thucAnNhanh").setValue(thucAnNhanh);
    }

    public int getTuVi() {
        return infomation.getTuVi();
    }

    public void saveTuVi(int tuVi) {
        infomation.setTuVi(tuVi);
        reference.child("Basic").child("tuVi").setValue(tuVi);
    }

    public int getBoiSN() {
        return infomation.getBoiSuNghiep();
    }

    public void saveBoiSN(int boiSuNghiep) {
        infomation.setBoiSuNghiep(boiSuNghiep);
        reference.child("Basic").child("boiSuNghiep").setValue(boiSuNghiep);
    }

    public int getBoiTinh() {
        return infomation.getBoiTinh();
    }

    public void saveBoiTinh(int boiTinh) {
        infomation.setBoiTinh(boiTinh);
        reference.child("Basic").child("boiTinh").setValue(boiTinh);
    }

    public int getNamTu() {return infomation.getNamTu(); }

    public void saveNamTu(int namTu)
    {
        infomation.setNamTu(namTu);
        reference.child("Basic").child("namTu").setValue(namTu);
    }

    public String getTienAn() {return  infomation.getTienAn();}

    public void saveTienAn(String tienAn)
    {
        infomation.setTienAn(tienAn);
        reference.child("Basic").child("tienAn").setValue(tienAn);
    }

    public void saveRelationship(ArrayList<QuanHe> arrRelationship) {
        this.arrRelationship = arrRelationship;
        reference.child("Relationship").setValue(arrRelationship);
    }

    public ArrayList<QuanHe> getRelationship(){
        return arrRelationship;
    }

    public void saveAsset(ArrayList<Food> arrAsset) {
        this.arrAsset = arrAsset;
        reference.child("Asset").setValue(arrAsset);
    }

    public ArrayList<Food> getAsset(){
        return arrAsset;
    }

    public void saveSick(ArrayList<Sick> arrSick) {
        this.arrSick = arrSick;
        reference.child("Sick").setValue(arrSick);
    }
    public int getHongKong() {
        return infomation.getHongKong();
    }

    public void saveHongKong(int hongKong) {
        infomation.setHongKong(hongKong);
        reference.child("Basic").child("hongKong").setValue(hongKong);
    }
    public int getAmerica() {
        return infomation.getAmerica();
    }

    public void saveAmerica(int america) {
        infomation.setAmerica(america);
        reference.child("Basic").child("america").setValue(america);
    }
    public int getKorea() {
        return infomation.getKorea();
    }

    public void saveKorea(int korea) {
        infomation.setKorea(korea);
        reference.child("Basic").child("korea").setValue(korea);
    }
    public int getThaiLand() {
        return infomation.getThaiLand();
    }

    public void saveThaiLand(int thaiLand) {
        infomation.setThaiLand(thaiLand);
        reference.child("Basic").child("thaiLand").setValue(thaiLand);
    }
    public int getVungTau() {
        return infomation.getVungTau();
    }

    public void saveVungTau(int vungTau) {
        infomation.setVungTau(vungTau);
        reference.child("Basic").child("vungTau").setValue(vungTau);
    }
    public int getPhuQuoc() {
        return infomation.getPhuQuoc();
    }

    public void savePhuQuoc(int phuQuoc) {
        infomation.setPhuQuoc(phuQuoc);
        reference.child("Basic").child("phuQuoc").setValue(phuQuoc);
    }
    public int getDaNang() {
        return infomation.getDaNang();
    }

    public void saveDaNang(int daNang) {
        infomation.setDaNang(daNang);
        reference.child("Basic").child("daNang").setValue(daNang);
    }
    public ArrayList<Sick> getSick(){
        return arrSick;
    }
}
interface LoadDone{
    void onLoaded();
}