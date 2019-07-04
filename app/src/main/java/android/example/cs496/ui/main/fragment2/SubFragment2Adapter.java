package android.example.cs496.ui.main.fragment2;

import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;

public class SubFragment2Adapter extends PagerAdapter {
    LayoutInflater inflater;
    public SubFragment2Adapter(LayoutInflater inflater) {
        this.inflater=inflater;
    }

    @Override
    public int getCount() {
        return MainActivity.picArr.length;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.tab_fragment2_zoominout, viewGroup, false);
        PhotoView photoView = view.findViewById(R.id.photoView);
        photoView.setImageResource(MainActivity.picArr[position]);

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