package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ItemObject implements Serializable {
    private String title;
    private int id;
    private ArrayList<Menus> menus;
    private String meanScore;

    public ItemObject(String title, ArrayList<Menus> menus){
        this.title = title; // 가게 이름
        this.menus = menus;
//        for (int i=0; i<=menus.size()-1; i++){
//            Menus arrayOfMenus = this.menus.get(i);
//            for(int j= 0)
//        }
        this.meanScore = "10";
    }

    public String getTitle() {
        return title;
    }

    public int getId() { return id; }

    public ArrayList<Menus> getMenus(){
        return menus;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setId(int id) { this.id = id; }
    public void setMenus(ArrayList<Menus> menus){
        this.menus = menus;
    }
    public void setMeanScore(String meanScore){
        this.meanScore =meanScore;
    }



}