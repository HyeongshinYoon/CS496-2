package android.example.cs496.ui.main.fragment4;

import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantEditAdapter extends RecyclerView.Adapter<RestaurantEditAdapter.ViewHolder>{

    private Menus mMenus;
    private Menu mMenu;
    public RestaurantEditAdapter(Menus mMenus){

        this.mMenus = mMenus;
    }

    @NonNull
    @Override
    public RestaurantEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Oncreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment4_vote_holder_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@OnBind");
        mMenu =mMenus.getmMenu().get(position);
        String menuName = mMenu.getMenuName();
        holder.tv_menu.setText(menuName);
        holder.ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //float v가 레이팅 점수
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenus.getmMenu().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu;
        private RatingBar ratingBar1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ViewHolder");
            tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
            ratingBar1 = (RatingBar) itemView.findViewById(R.id.ratingBar1);

        }
    }
}

