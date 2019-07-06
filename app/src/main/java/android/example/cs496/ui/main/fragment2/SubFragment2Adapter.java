package android.example.cs496.ui.main.fragment2;

import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;

public class SubFragment2Adapter extends PagerAdapter {
    LayoutInflater inflater;
    private ArrayList<Integer> mData;
    public SubFragment2Adapter(LayoutInflater inflater, ArrayList<Integer> data) {
        this.inflater=inflater;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.tab_fragment2_zoominout, viewGroup, false);
        PhotoView photoView = view.findViewById(R.id.photoView);

        if(position == 0){
            photoView.setImageResource(R.drawable.cat);
        }
        else {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Madcamp2/" + MainActivity.imageList.get(position) + ".jpg");

            photoView.setImageURI(Uri.fromFile(file));
            //photoView.setImageResource(MainActivity.picArr[position]);
        }
        viewGroup.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }
}