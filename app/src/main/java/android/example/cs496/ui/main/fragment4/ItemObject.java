package android.example.cs496.ui.main.fragment4;

public class ItemObject {
    private String title;
    private String[] menus;

    public ItemObject(String title, String[] menus){
        this.title = title; // 가게 이름
        this.menus = menus; //가게 메뉴들
    }

    public String getTitle() {
        return title;
    }

    public String[] getMenus(){
        return menus;
    }
}