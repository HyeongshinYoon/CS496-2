package android.example.cs496.ui.main.fragment4;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import static android.example.cs496.MainActivity.userInfo;
import static android.example.cs496.ui.main.fragment1.dummyData.editData;
import static android.example.cs496.ui.main.fragment1.dummyData.insertData;

public class RestaurantEditActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    RestaurantEditAdapter adapter;
    Context context;
    private  Menus mMenus;
    private String storeName;
    private TextView storeTitle, setMenuTitle;
    private ImageButton btn_back, btn_save;
    public static UserItem miniUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_fragment4_vote);
        context = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        storeTitle = (TextView) findViewById(R.id.restaurants_title);
        setMenuTitle = (TextView) findViewById(R.id.set_menu_title);
        btn_back = (ImageButton) findViewById(R.id.back);
        btn_save = (ImageButton) findViewById(R.id.save);
        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        Intent intent = getIntent();
        mMenus = (Menus) intent.getSerializableExtra("menus");
        storeName = (String) intent.getStringExtra("store_name");
        setMenuTitle.setText(mMenus.getmenusName());
        storeTitle.setText(storeName);

        miniUser = userInfo;

        adapter = new RestaurantEditAdapter(mMenus); //어댑터 고쳐야 ? 어댑터에서 Map Menus 받아오게 해야
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        //layoutManager.scrollToPositionWithOffset(0,0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save:
                userInfo = miniUser;


                Set <String> keySet = userInfo.getScoreArray().keySet();
                JsonObject j = new JsonObject();
                for ( String key : userInfo.getScoreArray().keySet() ) {
                    j = new JsonObject();
                    j.addProperty("menuId", key);
                    j.addProperty("score", userInfo.getScoreArray().get(key) );

                    Ion.with(context)
                            .load("http://143.248.36.220:3000/api/addUserStar/" + userInfo.getUserId())
                            .setJsonObjectBody(j)
                            .asJsonArray()
                            .setCallback(new FutureCallback<JsonArray>() {
                                @Override
                                public void onCompleted(Exception e, JsonArray result) {
                                    // do stuff with the result or error
                                    System.out.println("update: "+result);
                                }
                            });
                    System.out.println("방법1) key : " + key +" / value : " + userInfo.getScoreArray().get(key));
                }
                //JsonArray jj = j.getAsJsonArray();
//                json.add("scoreArray", j);
//                System.out.println(json);



                Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_LONG).show();
                finish();
                onBackPressed();
                break;
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
