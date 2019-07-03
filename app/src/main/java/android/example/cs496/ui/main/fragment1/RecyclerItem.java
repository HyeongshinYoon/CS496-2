package android.example.cs496.ui.main.fragment1;

import java.io.Serializable;

public class RecyclerItem
        implements Comparable<RecyclerItem>, Serializable {
    //private int person_id = 0;
    public String name = "";
    private int img = 0;
    private String phone = "";
    private String group = "";
    private String email = "";
    private int id = 0;

    public RecyclerItem(){}
    public RecyclerItem(RecyclerItem mRecyclerItem){
        this.id = mRecyclerItem.getId();
        this.name = mRecyclerItem.getName();
        this.img = mRecyclerItem.getImg();
        this.phone = mRecyclerItem.getPhone();
        this.group = mRecyclerItem.getGroup();
        this.email = mRecyclerItem.getEmail();
        getPhNumberchanged();
    }
    public RecyclerItem(int id, String name, int img, String phone, String group, String email) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.phone = phone;
        this.group = group;
        this.email = email;
        getPhNumberchanged();
    }

    //public void setPersonId(int id) { this.id = id; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setImg(int img) {this.img = img; }
    public void setPhone(String phone) { this.phone = phone;getPhNumberchanged(); }
    public void setGroup(String group) { this.group = group; }
    public void setEmail(String email) { this.email = email; }

    //public int getPersonId() { return person_id; }
    public int getId() { return id; }
    public String getName() {
        return name;
    }
    public int getImg() {
        return img;
    }
    public String getPhone() {
        return phone;
    }
    public String getGroup() { return group; }
    public String getEmail() { return email; }

//    @Override
//    public String toString(){
//        return this.phone;
//    }

    @Override
    public int hashCode() {
        return getPhNumberchanged().hashCode();
    }
    public String getPhNumberchanged(){ return phone.replace("-",""); }

    @Override
    public boolean equals(Object o){
        if(o instanceof RecyclerItem)
            return getPhNumberchanged().equals(((RecyclerItem) o).getPhNumberchanged());
        return false;
    }

    @Override
    public int compareTo(RecyclerItem r){
        return this.getName().toUpperCase().compareTo(r.getName().toUpperCase());
    }
}
