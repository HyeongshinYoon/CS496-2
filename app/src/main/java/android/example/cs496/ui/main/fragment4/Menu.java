package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menu implements Serializable {
    private String menuName;
    private int menuId;
    private double votedNumber;
    private double totalScore;


    public Menu(String menuName, int menuId, double votedNumber, double totalScore) {

        this.menuName =menuName;
        this.menuId = menuId;
        this.votedNumber = votedNumber; 
        this.totalScore = totalScore;
    }

    public int getMenuId() {
        return menuId;
    }
    public double getVotedNumber() {
        return votedNumber;
    }


    public double getTotalNumber() {

        return totalScore;
    }
    public String getMenuName(){
        return menuName;
    }


    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    public void setVotedNumber(double votedNumber) {
        this.votedNumber = votedNumber;
    }
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
    public void setMenuName(String menuName){
        this.menuName = menuName;
    }

    public Double getMeanScore(){
        double mean = totalScore/votedNumber;
        return  mean;
    }
}   

