package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ItemObject implements Serializable {
    private String title;
    private Map<String, ArrayList<Menu>> menus;

    public ItemObject(String title, Map<String, ArrayList<Menu>> menus){
        this.title = title; // 가게 이름
        this.menus = menus;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, ArrayList<Menu>> getMenus(){
        return menus;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setMenus(Map<String,ArrayList<Menu>> map){
        this.menus = map;
    }


}