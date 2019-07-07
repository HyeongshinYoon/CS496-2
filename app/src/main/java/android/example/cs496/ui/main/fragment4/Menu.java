package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menu implements Serializable {
    private String menuName;
    private int menuId;
    private int votedNumber;
    private int totalNumber;

    public Menu(String menuName, int menuId, int votedNumber, int totalNumber) {
        this.menuName =menuName;
        this.menuId = menuId;
        this.votedNumber = votedNumber; 
        this.totalNumber = totalNumber;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getVotedNumber() {
        return votedNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public  String getMenuName(){
        return menuName;
    }

}