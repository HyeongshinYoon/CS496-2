package android.example.cs496.ui.main.fragment4;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.PhoneBookActivity;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.RecyclerItemClickListener;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import static android.example.cs496.ui.main.fragment1.dummyData.editData;
import static android.example.cs496.ui.main.fragment1.dummyData.insertData;

public class RestaurantBookActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private String store_name;
    private String[] menu_list;
    private ItemObject item;
    RestaurantMenuAdapter adapter;
    private  ArrayList<Menus> arrayOfMenus;
    Context context;
    private ImageButton back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_fragment4);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view44);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);

        Intent intent = getIntent();
        item = (ItemObject) intent.getSerializableExtra("store_select");
        TextView store_title = (TextView) findViewById(R.id.restaurants_title);
        store_title.setText(item.getTitle());//
        arrayOfMenus =  item.getMenus();

        adapter = new RestaurantMenuAdapter(arrayOfMenus); //어댑터 고쳐야 ? 어댑터에서 Map Menus 받아오게 해야
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(RestaurantBookActivity.this, RestaurantEditActivity.class);
                        Menus menus = arrayOfMenus.get(position);
                        intent.putExtra("menus", menus);
                        intent.putExtra("store_name", item.getTitle());
                        startActivityForResult(intent,0);
                    }
                }));
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.back: // 취소 버튼 누를 경우
                setResult(0);
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
