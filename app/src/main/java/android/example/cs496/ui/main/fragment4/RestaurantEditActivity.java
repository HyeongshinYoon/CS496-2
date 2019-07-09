package android.example.cs496.ui.main.fragment4;

import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_fragment4_vote);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        storeTitle = (TextView) findViewById(R.id.restaurants_title);
        setMenuTitle = (TextView) findViewById(R.id.set_menu_title); //태그 이름
        btn_back = (ImageButton) findViewById(R.id.back);
        btn_save = (ImageButton) findViewById(R.id.save);
        btn_back.setOnClickListener(this);

        Intent intent = getIntent();
        mMenus = (Menus) intent.getSerializableExtra("menus");
        ArrayList<Menu> menuArrayList = mMenus.getmMenu();
        storeName = (String) intent.getStringExtra("store_name");
        storeTitle.setText(storeName);
        if(menuArrayList.size()==1){
            setMenuTitle.setVisibility(View.GONE);
        }else{
            setMenuTitle.setText(mMenus.getmenusName());
        }




//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                //layoutManager.scrollToPositionWithOffset(0,0);
//                //recyclerView.smoothScrollToPosition(0);
//                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,new RecyclerView.State(), 0);
//            }
//        });
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy==0){
//                    fab.hide();
//                }else {
//                    fab.show();
//                }
//            }
//        });

        adapter = new RestaurantEditAdapter(mMenus); //어댑터 고쳐야 ? 어댑터에서 Map Menus 받아오게 해야
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        //layoutManager.scrollToPositionWithOffset(0,0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
//            case R.id.set_save: //저장 버튼 누를 경우
//                if(textName.getText().toString().isEmpty() || textPhone.getText().toString().isEmpty()){ // 이름 또는 번호가 공란일 경우
//                    Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_LONG).show(); // 저장 못하게 브레이크
//                    break;
//                }
////                Intent intent = new Intent(Intent.ACTION_EDIT);
////                intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, mRecycelerItem.getPersonId()));
////                intent.putExtra(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, textName.getText().toString());
////                intent.putExtra(ContactsContract.CommonDataKinds.Phone.NUMBER, textName.getText().toString());
////                intent.putExtra(ContactsContract.CommonDataKinds.Email.ADDRESS, textEmail.getText().toString());
////                startActivity(intent);
//                RecyclerItem newRecyclerItem // editText 에 있는 정보로 새로운 리사이클러 아이템 정의
//                        = new RecyclerItem(
//                        mRecycelerItem.getId(),
//                        textName.getText().toString(),
//                        mRecycelerItem.getImg(),
//                        textPhone.getText().toString(),
//                        textGroup.getText().toString(),
//                        textEmail.getText().toString());
//                if(mState == 1){ // 추가할 경우
//                    int newId = insertData(newRecyclerItem);
//                    String strId = Integer.toString(newId);
//                    new EditPhoneBook.PostDataTask().execute("http://143.248.36.220:3000/api/addPhone",strId,textName.getText().toString(),textPhone.getText().toString(),textGroup.getText().toString(),"",textEmail.getText().toString());
//                    // 인서트되고 나서 id 정해짐
//                    Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent();
//                    setResult(1, intent);
//                }
//                if(mState == 2){ // 수정할 경우
//                    editData(mRecycelerItem.getId(), newRecyclerItem);
//                    String strId = Integer.toString(mRecycelerItem.getId());
//                    new EditPhoneBook.PutDataTask().execute("http://143.248.36.220:3000/api/updatePhone/"+strId,strId,textName.getText().toString(),textPhone.getText().toString(),textGroup.getText().toString(),"",textEmail.getText().toString());
//                    System.out.println(strId);
//                    Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent();
//                    intent.putExtra("new_item", newRecyclerItem);
//                    setResult(1, intent);
//                }
//                finish();
//                break;
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
