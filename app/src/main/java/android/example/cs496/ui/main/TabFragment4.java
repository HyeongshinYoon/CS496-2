package android.example.cs496.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.MainBackPressCloseHandler;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.RecyclerItemClickListener;
import android.example.cs496.ui.main.fragment1.Tab1Adapter;
import android.example.cs496.ui.main.fragment4.ItemObject;
import android.example.cs496.ui.main.fragment4.Menu;
import android.example.cs496.ui.main.fragment4.RestaurantBookActivity;
import android.example.cs496.ui.main.fragment4.Tab4Adapter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.example.cs496.MainActivity.totalArray;


public class TabFragment4 extends Fragment {

    private static Context context;
    RecyclerView recyclerView;

    Tab4Adapter myAdapter;

    private FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.tab_fragment4,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //resetData();// refresh data, set Recyclerview@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@바로바로 업데이트 되게, 이 다음(별점 수정 부분)에서 정보 업데이트되면 바로 반영되게

        myAdapter = new Tab4Adapter(totalArray);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //layoutManager.scrollToPositionWithOffset(0,0);
                //recyclerView.smoothScrollToPosition(0);
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,new RecyclerView.State(), 0);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy==0){
                    fab.hide();
                }else {
                    fab.show();
                }
            }
        });


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context.getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent =new Intent(context, RestaurantBookActivity.class);
                        ItemObject item = totalArray.get(position); // 아이템 오브젝트 하나
                        intent.putExtra("store_select", item);
                        System.out.println(item + "I'm a item");
                        startActivityForResult(intent,0);//

                    }
                }));
        return v;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myAdapter.notifyDataSetChanged();
    }
}