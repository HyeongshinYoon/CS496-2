package android.example.cs496.ui.main.fragment4;

import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {
    private String [] mList; // 메뉴들의 이름이 들어가 있는 스트링 배열

    public RestaurantMenuAdapter(String[] list){
        this.mList = list;
    }

    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Oncreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_fragment4_item_holder_view, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@OnBind");
        holder.textView_menu_name.setText(mList[position]);
        holder.textView_menu_score.setText("임의의 메뉴 점수");

    }

    @Override
    public int getItemCount() {
        return this.mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_menu_name, textView_menu_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ViewHolder");
            textView_menu_name = (TextView) itemView.findViewById(R.id.tv_menu);
            textView_menu_score = (TextView) itemView.findViewById(R.id.tv_menu_score);

        }
    }
}
