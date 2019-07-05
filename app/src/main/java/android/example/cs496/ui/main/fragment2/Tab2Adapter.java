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
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {
    //private Bitmap[] picArr;
    private ArrayList<PhotoItem> mData;
    private LayoutInflater inf;
    private Context context;
    GridViewHolder viewHolder;

    //    public Tab2Adapter(LayoutInflater inflater, Bitmap[] picArr) {
    public Tab2Adapter(Context context, LayoutInflater inflater, ArrayList<PhotoItem> data) {
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
        }
        else{
            convertView.setLayoutParams(new GridView.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
            viewHolder = (GridViewHolder) convertView.getTag();
        }

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/CS496_caches/" + getItem(position));

        // cache hit
        if(file.exists()) {
            viewHolder.img.setImageURI(Uri.fromFile(file));
        } else {
            new GetSingelItemTask(getItem(position)).execute();
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
            viewHolder.img.setImageURI(MainActivity.imageList.get(position).getUri());
//        if (position!=0) {viewHolder.img.setImageResource(MainActivity.picArr[position]);}
        return convertView;
    }

    private class GetSingelItemTask extends AsyncTask {

        private String id;

        public GetSingelItemTask(String id) {
            this.id = id;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String jsonResponse = "";

            try {

                HttpClient httpClient = new DefaultHttpClient();
                String urlString = "http://13.125.74.66:8082/api/images/" + id;
                URI url = new URI(urlString);

                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = httpClient.execute(httpGet);
                jsonResponse = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                JSONObject obj = new JSONObject(jsonResponse);

                String obj_id           = obj.getString("_id");
                String parsedString     = obj.getString("parsedString");

                byte[] decodedString = Base64.decode(parsedString, Base64.DEFAULT);

                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                saveImageBitmap(bitmap, obj_id);

                return true;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Object o) {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/CS496_caches/" + id);
            viewHolder.img.setImageURI(Uri.fromFile(file));
        }
    }
}
