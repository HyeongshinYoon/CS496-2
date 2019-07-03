package android.example.cs496.ui.main.fragment1.phonebook;

import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubCardAdapter extends RecyclerView.Adapter<SubCardAdapter.MyViewHolder> {

    private List<String> title;
    private List<String> text;

    public SubCardAdapter(List<String> title, List<String> text){
        System.out.println(title);
        System.out.println(text);
        this.title = title;
        this.text = text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View holderView = inflater.inflate(R.layout.sub_fragment1_holder_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i){
        String title = this.title.get(i);
        String text = this.text.get(i);
        myViewHolder.textView1.setText(title);
        myViewHolder.textView2.setText(text);
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView1, textView2;
        public MyViewHolder(@NonNull View view){
            super(view);
            this.textView1 = view.findViewById(R.id.tv_sub_card_name);
            this.textView2 = view.findViewById(R.id.tv_sub_text);
        }
    }
}
