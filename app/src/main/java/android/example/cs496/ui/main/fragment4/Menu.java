package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menu implements Serializable {
    private String menuName;
    private int menuId;
    private int votedNumber;
    private int totalScore;

    public void Menu(){
        this.menuName = "";
        this.menuId = 0;
        this.votedNumber = 0;
        this.totalScore = 0;
    }

    public Menu(String menuName, int menuId, int votedNumber, int totalScore) {
        this.menuName =menuName;
        this.menuId = menuId;
        this.votedNumber = votedNumber; 
        this.totalScore = totalScore;
    }

    public int getMenuId() {
        return menuId;
    }
    public int getVotedNumber() {
        return votedNumber;
    }
    public int getTotalNumber() {
        return totalScore;
    }
    public String getMenuName(){
        return menuName;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    public void setVotedNumber(int votedNumber) {
        this.votedNumber = votedNumber;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void setMenuName(String menuName){
        this.menuName = menuName;
    }

}