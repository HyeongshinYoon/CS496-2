package android.example.cs496.ui.main.fragment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Tab1Adapter extends RecyclerView.Adapter<Tab1Adapter.MyViewHolder> {

    private List<RecyclerItem> datas;
    private final Context context;

    public Tab1Adapter(Context context, List<RecyclerItem> datas){
        this.datas = datas;
        this.context = context;
    }

    public void removeItem(int position, int deletedId) {
        datas.remove(position);
        String stringId = Integer.toString(deletedId);
        // datas에서 지우면 데이터베이스에서도 지우기, id를 받아와야
        new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/" + stringId);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
    }

    class DeleteDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            //progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Deleting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return deleteData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //mResult.setText(result);
            System.out.println("delete"+result);
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }

        private String deleteData(String urlPath) throws IOException {

            String result = null;

            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(10000 /* millisecods */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
            urlConnection.connect();

            System.out.println("delete: "+urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == 200) {
                result = "Delete Successfully !";
            } else {
                result = "Delete failed !";
            }

            return result;
        }
    }

    class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = new ProgressDialog(mcontext);
            //progressDialog.setMessage("Inserting data...");
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){

            try {
                return postData(params[0], params[1], params[2], params[3], params[4], params[5],params[6]);
            } catch (IOException ex){
                return "Network error !";
            } catch (JSONException ex){
                return "Data Invalid !";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //mResult.setText(result);
            if(progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String postData(String urlPath, String strId, String name, String phone, String group, String img, String email) throws IOException, JSONException {

            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            int id = Integer.parseInt(strId);

            try {
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("id", id);
                dataToSend.put("name", name);
                dataToSend.put("phone", phone);
                dataToSend.put("group", group);
                dataToSend.put("img", "");
                dataToSend.put("email", email);

                System.out.println("send"+dataToSend);
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* millisecods */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true); //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally {
                if( bufferedReader != null) {
                    bufferedReader.close();
                }
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            }
            return result.toString();
        }
    }

    public void restoreItem(RecyclerItem model, int position) {
        datas.add(position, model);

        int id = model.getId();
        String name = model.getName();
        String phone = model.getPhone();
        //Img img =model.getImg(); // 이미지는 일단 고려하지 않고
        String group = model.getGroup();
        String email = model.getEmail();

        String strId = Integer.toString(id);
        new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone",strId, name, phone, "", group, email);

        // notify item added by position
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View holderView = inflater.inflate(R.layout.fragment1_holder_view, viewGroup, false);
        return new MyViewHolder(holderView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i){

        RecyclerItem data = datas.get(i);
        System.out.println(i);
        System.out.println(data.getName());
        myViewHolder.textView.setText(data.getName());
        myViewHolder.phoneView.setText(data.getPhone());
        ShapeDrawable bgShape;
        int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
        if ( i == 0 ){
            myViewHolder.imageView.setPadding(20, 20, 20, 20);
            //int colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
            myViewHolder.cardView.setCardBackgroundColor(colorPrimaryLight);
            myViewHolder.textView.setText("Add a New Friend:)");
            myViewHolder.textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            myViewHolder.textView.setTextColor(Color.WHITE);
            myViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_addperson));
            //myViewHolder.imageView.setImageResource(data.getImg());
            bgShape = new ShapeDrawable(new OvalShape());
            bgShape.setTint(Color.WHITE);
        }
        else {
            myViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher_foreground));
            //myViewHolder.imageView.setImageResource(data.getImg());
            bgShape = new ShapeDrawable(new OvalShape());
            bgShape.setTint(colorPrimaryLight);
        }
        myViewHolder.imageView.setBackground(bgShape);
        myViewHolder.imageView.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public TextView phoneView;
        public CardView cardView;
        OnClickListener onClickListener;

        public MyViewHolder(@NonNull View view){
            super(view);
            this.imageView = view.findViewById(R.id.iv_pic);
            this.textView = view.findViewById(R.id.tv_text);
            this.phoneView = view.findViewById(R.id.tv_phone);
            this.cardView = view.findViewById(R.id.sub_card);
        }

        @Override
        public void onClick(View view){
            onClickListener.onPhoneClick(getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void onPhoneClick(int position);
    }



//    public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
//        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
//        if(input != null)
//            return resizingBitmap(BitmapFactory.decodeStream(input));
//        else
//            Log.d("PHOTO", "first try failed to load photo");
//
//        byte[] photoBytes = null;
//        Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
//        Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
//        try {
//            if(c.moveToFirst())
//                photoBytes = c.getBlob(0);
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//
//        if(photoBytes != null)
//            return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
//        else
//            Log.d("PHOTO", "second try also failed");
//        return null;
//    }
//
//    public Bitmap resizingBitmap(Bitmap oBitmap){
//        if(oBitmap == null)return null;
//        float width = oBitmap.getWidth();
//        float height = oBitmap.getHeight();
//        float resizing_size = 250;
//        Bitmap rBitmap = null;
//        if(width > resizing_size){
//            float mWidth = (float) (width / 100);
//            float fScale = (float) (resizing_size / mWidth);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
//        if(height > resizing_size){
//            float mHeight = (float) (height / 100);
//            float fScale = (float) (resizing_size / mHeight);
//            width *= (fScale / 100);
//            height *= (fScale / 100);
//        }
//
//        Log.d("PHOTO", "rBitmap : " + width + ", " + height);
//        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int)height, true);
//        return rBitmap;
//    }
}
