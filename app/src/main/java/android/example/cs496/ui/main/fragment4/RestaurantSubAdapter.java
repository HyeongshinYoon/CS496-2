package android.example.cs496.ui.main.fragment4;


import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantSubAdapter extends RecyclerView.Adapter<RestaurantSubAdapter.ViewHolder> {
    private ArrayList<Menu> mMenuArray; // 메뉴들의 이름이 들어가 있는 스트링 배열, 태그 없음,

    public RestaurantSubAdapter(ArrayList<Menu> menuArray){
        this.mMenuArray = menuArray;
    }

    @NonNull
    @Override
    public RestaurantSubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Oncreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment4_item_holder_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@OnBind");
        Menu mMenu =mMenuArray.get(position);
        String menuName = mMenu.getMenuName(); // position에 해당하는 태그 스트링 가져오기
        double score =Math.round(((mMenu.getTotalNumber()/mMenu.getVotedNumber()*100))/100.0);
        String scoreStr = Double.toString(score);
        holder.tv_menu.setText(menuName);
        holder.tv_menu_score.setText(scoreStr);
    }

    @Override
    public int getItemCount() {
        return this.mMenuArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu, tv_menu_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ViewHolder");
            tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
            tv_menu_score = (TextView) itemView.findViewById(R.id.tv_menu_score);

        }
    }
}
