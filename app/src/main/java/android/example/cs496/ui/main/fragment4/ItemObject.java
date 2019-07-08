package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ItemObject implements Serializable {
    private String title;
    private int id;
    private ArrayList<Menus> menus;

    public ItemObject(String title, ArrayList<Menus> arrayOfMenus){
        this.title = title; // 가게 이름
        this.menus = arrayOfMenus;
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

    public double getMeanScore(){
        int sumVote =0;
        double sumTotal =0;
        for (int i=0; i<=menus.size()-1; i++){
            Menus menus1 = menus.get(i);
            for(int j= 0; j<= menus1.getmMenu().size()-1; j++){
                Menu menu = menus1.getmMenu().get(j);
                double votedNumber = menu.getVotedNumber();
                double totalNumber = menu.getTotalNumber();// total Score
                if(votedNumber!=0){
                    sumVote += votedNumber;
                    sumTotal += totalNumber;
                }
            }
        }
        return sumTotal/sumVote;
    }



}