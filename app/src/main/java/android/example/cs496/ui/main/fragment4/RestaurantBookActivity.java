package android.example.cs496.ui.main.fragment4;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class RestaurantBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String store_name;
    private String[] menu_list;
    private ItemObject item;
    private String test;
    RestaurantMenuAdapter adapter;
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_fragment4);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view44);

        Intent intent = getIntent();
        item = (ItemObject) intent.getSerializableExtra("store_select");
        test = intent.getStringExtra("store_state");
        System.out.println("test for intent of RestaurantBookActivity:" +test);
        TextView store_title = (TextView) findViewById(R.id.restaurants_title);
        store_title.setText(item.getTitle());//
        Map<String, ArrayList<Menu>> menus = item.getMenus();

//        store_name = item.getTitle();
//        menu_list = item.getMenus();
//        System.out.println(store_name);
//        System.out.println(menu_list[0]);
//        System.out.println(menu_list[1]);
//        System.out.println(menu_list[2]);
//        menu_list = new String[2];
//        menu_list[0]= "첫번째";
//        menu_list[1]= "두번째";

        adapter = new RestaurantMenuAdapter( menus); //어댑터 고쳐야 ? 어댑터에서 Map Menus 받아오게 해야
//
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //layoutManager.scrollToPositionWithOffset(0,0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
