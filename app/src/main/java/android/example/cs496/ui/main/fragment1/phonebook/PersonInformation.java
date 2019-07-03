package android.example.cs496.ui.main.fragment1.phonebook;

import android.content.Context;
import android.example.cs496.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.example.cs496.ui.main.fragment1.PhoneBookActivity.getEmail;
import static android.example.cs496.ui.main.fragment1.PhoneBookActivity.getGroup;
import static android.example.cs496.ui.main.fragment1.PhoneBookActivity.getPhone;

public class PersonInformation extends Fragment {

    RecyclerView recyclerView;
    List<String> title;
    List<String> text;
    //RecyclerView.Adapter adapter;
    //RecyclerView.LayoutManager layoutManager;

    // private ArrayList<RecyclerItem> tap1Items = new ArrayList<>();

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();
        this.title = new ArrayList<>();
        this.text = new ArrayList<>();
        String phone = getPhone();
        String group = getGroup();
        String email = getEmail();

        if (!TextUtils.isEmpty(phone)) {
            this.title.add("Phone");
            this.text.add(phone);
        }
        if (!TextUtils.isEmpty(group)) {
            this.title.add("Group");
            this.text.add(group);
        }
        if (!TextUtils.isEmpty(email)) {
            this.title.add("Email");
            this.text.add(email);
        }
        SubCardAdapter adapter = new SubCardAdapter(this.title, this.text);
        View v = inflater.inflate(R.layout.sub_fragment1_recycler_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.sub_fragment1_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //adapter = new Tab1Adapter(textSet, imgSet, phoneSet);
        return v;
    }
}