package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menus implements Serializable {
    private String menusName;
    private ArrayList<Menu> mMenu;

    public Menus(String menusName, ArrayList<Menu> mMenu) {
        this.menusName =menusName;
        this.mMenu = mMenu;
    }

    public String getmenusName() {
        return menusName;
    }
    public ArrayList<Menu> getmMenu() {
        return mMenu;
    }

}