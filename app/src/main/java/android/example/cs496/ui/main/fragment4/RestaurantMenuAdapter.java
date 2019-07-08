package android.example.cs496.ui.main.fragment4;

import android.content.Context;
import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {
    private ArrayList<Menus> mMenus; // 메뉴들의 이름이 들어가 있는 스트링 배열
    private RestaurantSubAdapter adapter;
    private Context mContext;
    private LinearLayout linearLayout;
    public RestaurantMenuAdapter(ArrayList<Menus> menus){
        this.mMenus = menus;
    }

    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Oncreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment4_holder_view, parent, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@OnBind");
//        for(Map.Entry<String,ArrayList<Menu>> entry : mMenus.entrySet()){
//            System.out.println("key : " + entry.getKey() + " , value : " + entry.getValue());
//        }
        String menusName = mMenus.get(position).getmenusName(); // position에 해당하는 태그 스트링 가져오기
        ArrayList<Menu> menuArray =mMenus.get(position).getmMenu(); // 태그에 해당하는 메뉴 종류 어레이리스트 가져오기

        adapter = new RestaurantSubAdapter(menuArray); //하나의 태그에 해당하는 메뉴 종류 어레이리스트
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);

        holder.textView_set_name.setText(menusName);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapter);
        int menuNumber=0;
        double totalScore=0;
        //가지고 온 태그와 그 메뉴 종류 어레이에 따른 if문 , 식당에서 메뉴를 업로드 하지 않을 때, 어레이의 크기가 1일 때(메뉴가 곧 태그일 때), 하나의 태그에 여러개의 메뉴가 있을 때
        if(menuArray.size()==1 && menuArray.get(0).getMenuName() == "식당에서 메뉴를 업로드하지 않았습니다."){
            holder.textView_set_name.setVisibility((View.INVISIBLE)); // 다음 어댑터에서 가져오는 메뉴 점수 표시하지 않게 해야 // durltj
            holder.textView_set_score.setVisibility((View.INVISIBLE));
        }else if(menuArray.size()==1){// 메뉴가 곧 태그일 때
            holder.textView_set_name.setVisibility((View.INVISIBLE));
            holder.textView_set_score.setVisibility((View.INVISIBLE));
        }else{
            for(int i=0; i<= menuArray.size() -1; i++){
                if(menuArray.get(i).getVotedNumber() !=0){
                    menuNumber+=1;
                    totalScore += menuArray.get(i).getMeanScore();
                }
            }if(menuNumber==0){
                holder.textView_set_score.setText("평가한 사람이 없습니다.");
            }else{
                holder.textView_set_score.setText(Double.toString(totalScore/menuNumber));
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.mMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_set_name;
        private RecyclerView recyclerView;
        private TextView textView_set_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ViewHolder");
            textView_set_name = (TextView) itemView.findViewById(R.id.set_menu_title);
            recyclerView = (RecyclerView) itemView.findViewById((R.id.recycler_view));
            textView_set_score = (TextView) itemView.findViewById(R.id.set_menu_star);
            //textView_menu_score = (TextView) itemView.findViewById(R.id.tv_menu_score);

        }
    }
}
