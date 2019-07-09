package android.example.cs496.ui.main.fragment4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserItem implements Serializable {
    private String userName;
    private String userId;
    private Map<String, Double> scoreArray;

    public UserItem(){
        this.userName = "";
        this.userId = "";
        this.scoreArray = new HashMap<>();
    }
    public UserItem(String userName, String userId){
        this.userName = userName;
        this.userId = userId;
        this.scoreArray = new HashMap<>();
    }
    public UserItem(String userName, String userId, Map<String, Double> scoreArray){
        this.userName = userName;
        this.userId = userId;
        this.scoreArray = scoreArray;
    }

    public String getUserName(){return userName;}
    public String getUserId(){return userId;}
    public Map<String, Double> getScoreArray(){return scoreArray;}

    public void setUserName(String userName){this.userName = userName;}
    public void setUserId(String userId){this.userId = userId;}
    public void setScoreArray(Map<String, Double> scoreArray){this.scoreArray = scoreArray;}

    public void insertScoreArray(String s, Double d){
        this.scoreArray.remove(s);
        this.scoreArray.put(s, d);
    }

}