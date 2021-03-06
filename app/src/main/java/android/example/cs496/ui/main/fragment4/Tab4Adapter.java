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

public class Tab4Adapter extends RecyclerView.Adapter<Tab4Adapter.ViewHolder> {
    //데이터 배열 선언
    private ArrayList<ItemObject> mList;

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_img;
        private TextView textView_title, textView_score;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.iv_store);
            textView_title = (TextView) itemView.findViewById(R.id.tv_store);
            textView_score = (TextView) itemView.findViewById(R.id.tv_score); // store의 평균 점수 = 메뉴들의 평균 점수
        }
    }
    //생성자
    public Tab4Adapter(ArrayList<ItemObject> list) { //tab1 어댑터에서는 컨텍스트도 인풋으로 받음

        this.mList = list;
    }

    @NonNull
    @Override
    public Tab4Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment4_holder_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab4Adapter.ViewHolder holder, int position) {
        ItemObject itemObject = mList.get(position);

        holder.textView_title.setText(String.valueOf(itemObject.getTitle()));
        //평균 점수 계산, 하나의 메뉴의 votedNUmber가 0인 메뉴는 빼고 (각 메뉴의 평균 점수의 합/ 메뉴 수의 합)
        //그냥 아이템오브젝트의 attr 하나로 추가하고, 나머지 attr들로 계산되게 하면 될듯
        
        holder.textView_score.setText(Double.toString(itemObject.getMeanScore()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}