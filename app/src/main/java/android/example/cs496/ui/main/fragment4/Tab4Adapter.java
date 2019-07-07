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
            textView_score = (TextView) itemView.findViewById(R.id.tv_score);
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

        holder.textView_title.setText(String.valueOf(mList.get(position).getTitle()));
        holder.textView_score.setText("임의의 점수");
        //holder.imageView_img.set

        //다 해줬는데도 GlideApp 에러가 나면 rebuild project를 해주자.
//        GlideApp.with(holder.itemView).load(mList.get(position).getImg_url())
//                .override(300,400)
//                .into(holder.imageView_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}