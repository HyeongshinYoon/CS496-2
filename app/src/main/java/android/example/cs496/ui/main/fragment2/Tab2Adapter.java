package android.example.cs496.ui.main.fragment2;

import android.app.ActionBar;
import android.content.Context;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {
    //private Bitmap[] picArr;
    private ArrayList<Integer> mData;
    private LayoutInflater inf;
    private Context context;
    GridViewHolder viewHolder;

    //    public Tab2Adapter(LayoutInflater inflater, Bitmap[] picArr) {
    public Tab2Adapter(Context context, LayoutInflater inflater, ArrayList<Integer> data) {
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

        if (convertView == null) {
            convertView = inf.inflate(R.layout.griditem, parent, false);
            viewHolder = new GridViewHolder();
            viewHolder.img = convertView.findViewById(R.id.imageView1);
            convertView.setTag(viewHolder);
        } else {
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
        }
        else {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Madcamp2/" + MainActivity.imageList.get(position) + ".jpg");

            // cache hit
            if (file.exists()) {
                viewHolder.img.setImageURI(Uri.fromFile(file));
            } else {
                String imgFileName = getItem(position) + ".jpg";
                File storageDir = new File(Environment.getExternalStorageDirectory() + "/MadCamp2");
                Ion.with(context)
                        .load("http://143.248.36.220:3000/api/photo/" + getItem(position))
                        .write(new File(storageDir, imgFileName))
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File file) {
                                Toast.makeText(context, "image load", Toast.LENGTH_SHORT).show();
                            }
                        });

                File new_file = new File(Environment.getExternalStorageDirectory().toString() + "/Madcamp2/" + MainActivity.imageList.get(position) + ".jpg");
                viewHolder.img.setImageURI(Uri.fromFile(new_file));
            }
        }
        return convertView;
    }
}
