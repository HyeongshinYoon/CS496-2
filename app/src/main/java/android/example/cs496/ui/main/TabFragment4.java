package android.example.cs496.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.MainBackPressCloseHandler;
import android.example.cs496.ui.main.fragment1.PhoneBookActivity;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.RecyclerItemClickListener;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.example.cs496.ui.main.fragment4.ItemObject;
import android.example.cs496.ui.main.fragment4.RestaurantBookActivity;
import android.example.cs496.ui.main.fragment4.RestaurantMenuAdapter;
import android.example.cs496.ui.main.fragment4.Tab4Adapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static android.example.cs496.ui.main.fragment1.dummyData.refreshData;


public class TabFragment4 extends Fragment {

    private static Context context;
    RecyclerView recyclerView;
    static List<RecyclerItem> datas = null;
    private MainBackPressCloseHandler mainBackPressCloseHandler;
    private Paint p = new Paint();
    Tab1Adapter adapter;

    private ArrayList<String> storeArray = new ArrayList();
    private ArrayList<String> menuArray = new ArrayList();
    private ArrayList<ItemObject> totalArray = new ArrayList();
    //RecyclerView.Adapter adapter;
    //RecyclerView.LayoutManager layoutManager;
    // private ArrayList<RecyclerItem> tap1Items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        mainBackPressCloseHandler = new MainBackPressCloseHandler(getActivity());
        View v = inflater.inflate(R.layout.tab_fragment4,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //resetData();// refresh data, set Recyclerview@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@바로바로 업데이트 되게
        new Description().execute(); // 받아오고 연결하는 과정

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context.getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent =new Intent(context, RestaurantBookActivity.class);
                        ItemObject item = totalArray.get(position); // 아이템 오브젝트 하나
                        intent.putExtra("store_select", item); //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 아이템 데이터 타입 확인, 이렇게 해도 가능??
                        intent.putExtra("store_state", "1"); //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 여기 스테이트 의미가 없나?? 아마 없을 듯 아래의 리퀘스트코드
                        startActivityForResult(intent,0);
                    }
                }));
        return v;
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(context); // 문제 생기면 바로 지우기
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://bds.bablabs.com/restaurants?campus_id=JEnfpqCUuR").get();;
                Elements elements =doc.select("h4.card-title");
                Elements menus = doc.select("div.card-title");
                int mSize = elements.size();
                System.out.println("#of stores:"+elements.size());

                for(Element element : elements) {
                    String my_title = element.text();
                    storeArray.add(my_title);
                }
                for(Element element : menus){
                    String my_menu = element.text();
                    menuArray.add(my_menu);
                }
                for(int i=0; i<=mSize-1; i++){
                    String store = storeArray.get(i);
                    String menu = menuArray.get(i);
                    String[] menuSplited = menu.split(" "); // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 엘리멘트 나누는 함수 필요

                    totalArray.add(new ItemObject(store, menuSplited));
                    System.out.println(i);
                    System.out.println(totalArray.get(i).getTitle());
                    System.out.println(menu);
                    for(int j=0; j<=totalArray.get(i).getMenus().length -1; j++){
                        System.out.println(totalArray.get(i).getMenus()[j]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            Tab4Adapter myAdapter = new Tab4Adapter(totalArray);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        resetData();
//    }

//    public void resetData(){
//        datas = refreshData();
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~아래 어댑터 문제 있음
//        adapter = new Tab1Adapter(context, datas);
//        recyclerView.setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
//        //layoutManager.scrollToPositionWithOffset(0,0);
//        recyclerView.setLayoutManager(layoutManager);
//
//    }
}