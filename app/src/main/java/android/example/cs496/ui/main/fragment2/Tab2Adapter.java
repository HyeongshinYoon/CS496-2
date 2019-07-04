package android.example.cs496.ui.main.fragment2;

import android.app.ActionBar;
import android.content.Context;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {
    //private Bitmap[] picArr;
    private ArrayList<Uri> mData;
    private LayoutInflater inf;
    private Context context;
    GridViewHolder viewHolder;

    //    public Tab2Adapter(LayoutInflater inflater, Bitmap[] picArr) {
    public Tab2Adapter(Context context, LayoutInflater inflater, ArrayList<Uri> data) {
        this.inf = inflater;
        this.context = context;
        this.mData = data;
    }

    @Override
    public int getCount() {return mData.size();}

    @Override
    public Object getItem(int position) {return mData.get(position);}

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 캐시된 뷰가 없을 경우 새로 생성하고 뷰홀더를 생성한다.
        if (convertView == null) {
            convertView = inf.inflate(R.layout.griditem, parent, false);
            viewHolder = new GridViewHolder();
            if (position==0) {
                viewHolder.img = convertView.findViewById(R.id.imageView1);
            }
            else{ viewHolder.img = convertView.findViewById(R.id.imageView1); }
            convertView.setTag(viewHolder);
        }
        // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다.
        else{
            convertView.setLayoutParams(new GridView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
            viewHolder = (GridViewHolder) convertView.getTag();
        }

        if (position==0) {
            viewHolder.img.setImageResource(R.drawable.add_camera);
            int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
            OvalShape ovalShape = new OvalShape();
            ovalShape.resize(30, 30);
            ShapeDrawable bgShape = new ShapeDrawable(ovalShape);
            viewHolder.img.setBackground(bgShape);
            bgShape.setTint(colorPrimaryLight);
            viewHolder.img.setClipToOutline(true);
            viewHolder.img.setScaleType(ImageView.ScaleType.CENTER);
        }else
            viewHolder.img.setImageURI(MainActivity.imageList.get(position));
//        if (position!=0) {viewHolder.img.setImageResource(MainActivity.picArr[position]);}
        return convertView;
    }
}
