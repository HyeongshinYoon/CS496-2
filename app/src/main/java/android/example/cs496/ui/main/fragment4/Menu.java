package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menu implements Serializable {
    private String menuName;
    private int menuId;
    private int votedNumber;
    private double totalScore;

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

    public double getTotalNumber() {
        return totalScore;
    }

    public  String getMenuName(){
        return menuName;
    }

    public Double getMeanScore(){
        double mean = totalScore/votedNumber;
        return  mean;
}   }