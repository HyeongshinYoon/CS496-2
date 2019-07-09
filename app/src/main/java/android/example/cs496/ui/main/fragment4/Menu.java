package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Menu implements Serializable {
    private String menuName;
    private int menuId;
    private int votedNumber = 0;
    private double totalScore = 0;


    public Menu(String menuName, int menuId, int votedNumber, double totalScore) {

        this.menuName = menuName;
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
    public double getTotalNumber() { return totalScore; }
    public String getMenuName(){
        return menuName;
    }

    public void setMenu(Menu m) {
        this.menuName = m.menuName;
        this.menuId = m.menuId;
        this.votedNumber = m.votedNumber;
        this.totalScore = m.totalScore;
    }
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    public void setVotedNumber(int votedNumber) {
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

