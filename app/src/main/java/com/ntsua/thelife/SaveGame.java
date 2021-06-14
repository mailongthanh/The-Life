package com.ntsua.thelife;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveGame {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Gson gson;

    public SaveGame(SharedPreferences preferences) {
        this.preferences = preferences;
        editor = this.preferences.edit();
        gson = new Gson();
    }

    public void saveAvatar(int id)
    {
        editor.putInt("avatar", id);
        editor.commit();
    }

    public int getAvatar()
    {
        return preferences.getInt("avatar", 0);
    }

    public void saveDating(boolean isDating)
    {
        editor.putBoolean("dating", isDating);
        editor.commit();
    }

    public  void saveEnglish(boolean english)
    {
        editor.putBoolean("english", english);
        editor.commit();
    }

    public void saveDriving(boolean driving)
    {
        editor.putBoolean("driving", driving);
        editor.commit();
    }

    public void saveNumberOfFriends(int number)
    {
        editor.putInt("allfriend", number);
        editor.commit();
    }

    public void saveJogging(int time) {
        editor.putInt("jogging", time);
        editor.commit();
    }

    public void saveNewFriendInYear(int friend)
    {
        editor.putInt("friend", friend);
        editor.commit();
    }

    public void saveExercise(int time) {
        editor.putInt("exercise", time);
        editor.commit();
    }

    public void saveLibrary(int time) {
        editor.putInt("library", time);
        editor.commit();
    }

    public void saveSalary(int salary) {
        editor.putInt("salary", salary);
        editor.commit();
    }

    public void saveSkill(int skill) {
        editor.putInt("skill", skill);
        editor.commit();
    }

    public void saveName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public void saveMoney(int money) {
        editor.putInt("money", money);
        editor.commit();
    }
    public void saveNhaHang(int mon) {
        editor.putInt("Nhà hàng", mon);
        editor.commit();
    }

    public void saveAge(int age) {
        editor.putInt("age", age);
        editor.commit();
    }

    public void saveGender(boolean isBoy)
    {
        editor.putBoolean("gender", isBoy);
        editor.commit();
    }

    public void savePlayerInfo(int happy, int health, int smart, int appearance) {
        editor.putInt("happy", happy);
        editor.putInt("health", health);
        editor.putInt("smart", smart);
        editor.putInt("appearance", appearance);
        editor.commit();
    }

    public void saveDetailActivity(String s) {
        editor.putString("detail", s);
        editor.commit();
    }

    public void saveNumberOfGirlFriend(int number)
    {
        editor.putInt("girl", number);
        editor.commit();
    }

    public void saveRelationship(ArrayList<QuanHe> arrRelationship) {
        String json = gson.toJson(arrRelationship);
        editor.putString("relationship", json);
        editor.commit();
    }
    public void saveAsset(ArrayList<Food> arrAsset) {
        String json = gson.toJson(arrAsset);
        editor.putString("asset", json);
        editor.commit();
    }

    public void saveJob(String job) {
        editor.putString("job", job);
        editor.commit();
    }
    public void saveTraSua(int mon) {
        editor.putInt("Trà sữa", mon);
        editor.commit();
    }
    public int getTraSua()
    {
        return preferences.getInt("Trà sữa", 0);
    }
    public void saveCaPhe(int mon) {
        editor.putInt("Cà phê", mon);
        editor.commit();
    }
    public int getCaPhe()
    {
        return preferences.getInt("Cà phê", 0);
    }
    public void saveRuou(int mon) {
        editor.putInt("Rượu", mon);
        editor.commit();
    }
    public int getRuou()
    {
        return preferences.getInt("Rượu", 0);
    }
    public void saveBia(int mon) {
        editor.putInt("Bia", mon);
        editor.commit();
    }
    public int getBia()
    {
        return preferences.getInt("Bia", 0);
    }
    public void saveHamBurGer(int mon) {
        editor.putInt("Hamburger", mon);
        editor.commit();
    }
    public int getHamBurGer()
    {
        return preferences.getInt("Hamburger", 0);
    }
    public void saveBanhMi(int mon) {
        editor.putInt("Bánh mì", mon);
        editor.commit();
    }
    public int getBanhMi()
    {
        return preferences.getInt("Bánh mì", 0);
    }
    public void saveMy(int mon) {
        editor.putInt("Mỳ", mon);
        editor.commit();
    }
    public int getMy()
    {
        return preferences.getInt("Mỳ", 0);
    }
    public void saveTraiCay(int mon) {
        editor.putInt("Trái cây", mon);
        editor.commit();
    }
    public int getTraiCay()
    {
        return preferences.getInt("Trái cây", 0);
    }
    public void savePizza(int mon) {
        editor.putInt("Pizza", mon);
        editor.commit();
    }
    public int getPizza()
    {
        return preferences.getInt("Pizza", 0);
    }
    public void saveLau(int mon) {
        editor.putInt("Lẩu", mon);
        editor.commit();
    }
    public int getLau()
    {
        return preferences.getInt("Lẩu", 0);
    }
    public void saveCom(int mon) {
        editor.putInt("Cơm", mon);
        editor.commit();
    }
    public int getCom()
    {
        return preferences.getInt("Cơm", 0);
    }
    public void saveHaiSan(int mon) {
        editor.putInt("Hải sản", mon);
        editor.commit();
    }
    public int getHaiSan()
    {
        return preferences.getInt("Hải sản", 0);
    }
    public void saveGa(int mon) {
        editor.putInt("Gà", mon);
        editor.commit();
    }
    public int getGa()
    {
        return preferences.getInt("Gà", 0);
    }
    public void saveRauCu(int mon) {
        editor.putInt("Rau củ", mon);
        editor.commit();
    }
    public int getRauCu()
    {
        return preferences.getInt("Rau củ", 0);
    }
    public void saveKeo(int mon) {
        editor.putInt("Kẹo", mon);
        editor.commit();
    }
    public int getKeo()
    {
        return preferences.getInt("Kẹo", 0);
    }
    public void saveFastFood(int mon) {
        editor.putInt("Thức ăn nhanh", mon);
        editor.commit();
    }
    public int getFastFood()
    {
        return preferences.getInt("Thức ăn nhanh", 0);
    }
    public void saveNuocEp(int mon) {
        editor.putInt("Nước ép trái cây", mon);
        editor.commit();
    }
    public int getNuocEp()
    {
        return preferences.getInt("Nước ép trái cây", 0);
    }
    public boolean getDating()
    {
        return preferences.getBoolean("dating", false);
    }
    public int getNhaHang()
    {
        return preferences.getInt("Nhà hàng", 0);
    }

    public boolean getEnglish()
    {
        return preferences.getBoolean("english", false);
    }

    public boolean getDriving()
    {
        return preferences.getBoolean("driving", false);
    }

    public int getNumberOfFriends()
    {
        return preferences.getInt("allfriend", 0);
    }

    public int getNewFriendInYear()
    {
        return preferences.getInt("friend", 0);
    }

    public int getJogging()
    {
        return preferences.getInt("jogging", 0);
    }

    public int getTuVi()
    {
        return preferences.getInt("Bói tử vi", 0);
    }

    public int getBoiSN()
    {
        return preferences.getInt("Bói công danh sự nghiệp", 0);
    }

    public int getBoiTinh()
    {
        return preferences.getInt("Bói tình duyên", 0);
    }

    public int getExercise()
    {
        return preferences.getInt("exercise", 0);
    }

    public int getLibrary()
    {
        return preferences.getInt("library", 0);
    }

    public int getSalary()
    {
        return preferences.getInt("salary", 0);
    }

    public String getName(){
        return preferences.getString("name", "NoName");
    }

    public boolean getGender() { return preferences.getBoolean("gender", false);}

    public int getHappy()
    {
        return  preferences.getInt("happy", 0);
    }

    public int getHealth()
    {
        return  preferences.getInt("health", 0);
    }

    public int getSmart()
    {
        return  preferences.getInt("smart", 0);
    }

    public int getAppearance()
    {
        return  preferences.getInt("appearance",0);
    }

    public String getJob()
    {
        return preferences.getString("job", "unemployment");
    }

    public int getMoney()
    {
        return preferences.getInt("money", 5000);
    }

    public int getAge(){ return preferences.getInt("age", 0);}

    public String getDetailActivity() {
        return preferences.getString("detail", "");
    }

    public int getSkill()
    {
        return preferences.getInt("skill", 0);
    }

    public int getNumberOfGirlFriend()
    {
        return preferences.getInt("girl", 999);
    }

    public ArrayList<QuanHe> getRelationship(){
        String json = preferences.getString("relationship", "");
        Type type = new TypeToken<ArrayList<QuanHe>>() {}.getType();
        ArrayList<QuanHe> arrRelationship = gson.fromJson(json, type);
        return arrRelationship;
    }

    public ArrayList<Food> getAsset(){
        String json = preferences.getString("asset","");
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        ArrayList<Food> arrAsset = gson.fromJson(json,type);
        return arrAsset;
    }

    public void saveSick(ArrayList<Sick> arrSick) {
        String json = gson.toJson(arrSick);
        editor.putString("sick", json);
        editor.commit();
    }

    public ArrayList<Sick> getSick(){
        String json = preferences.getString("sick","");
        Type type = new TypeToken<ArrayList<Sick>>() {}.getType();
        ArrayList<Sick> arrSick = gson.fromJson(json,type);
        return arrSick;
    }
}
