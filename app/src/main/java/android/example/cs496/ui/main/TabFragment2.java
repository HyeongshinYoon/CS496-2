package android.example.cs496.ui.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.example.cs496.MainActivity;
import android.example.cs496.ui.main.fragment2.PhotoItem;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.example.cs496.R;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.example.cs496.ui.main.fragment1.dummyData.setInitialData;

public class TabFragment2 extends Fragment {

    private Uri imgUri;
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    GridView gridView;
    private Tab2Adapter adapter;

    private Context mContext;
    private Activity activity;
    private String photoId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if (context instanceof Activity)
            activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment2, container, false);

        gridView = view.findViewById(R.id.gridView1);
        adapter = new Tab2Adapter(mContext, inflater, MainActivity.imageList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View clickView, int position, long id) {
                // 이미지 터치했을 때 동작하는 곳
                if (position == 0) {
                    takePhoto();
                } else {
                    Intent intent = new Intent(mContext, Fragment2SubActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    //사진 찍기 클릭
    public void takePhoto() {

        // 촬영 후 이미지 가져옴
        String state = Environment.getExternalStorageState();
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(mContext, activity.getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        } else {
            Log.v("알림", "저장공간에 접근 불가능");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FROM_CAMERA: {
                //카메라 촬영
                try {
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Objects.requireNonNull(mContext).sendBroadcast(mediaScanIntent);
        MainActivity.imageList.add(new PhotoItem(contentUri, MainActivity.lastImageNum, photoId));
        System.out.println(MainActivity.imageList);
        Future uploading = Ion.with(mContext)
                .load("http://143.248.36.220:3000/api/addPhoto/" + MainActivity.lastImageNum)
                .setMultipartParameter("label", String.valueOf(MainActivity.lastImageNum))
                .setMultipartFile("data", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            MainActivity.lastImageNum += 1;
                            JSONObject job = new JSONObject(result.getResult());
                            Toast.makeText(mContext.getApplicationContext(), job.getString("response"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
        Toast.makeText(mContext, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    public File createImageFile() throws IOException {
        final File[] imageFile = new File[1];
        final File storageDir = new File(Environment.getExternalStorageDirectory() + "/MadCamp2");
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.v("알림", "storageDir 존재함 " + storageDir.toString());

        Future uploading = Ion.with(mContext)
                .load("http://143.248.36.220:3000/api/addPhoto")
                .setMultipartParameter("label", String.valueOf(MainActivity.lastImageNum))
                //.setMultipartFile("data", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            JSONObject job = new JSONObject(result.getResult());
                            photoId = job.getString("_id");
                            String imgFileName = photoId + ".jpg";
                            imageFile[0] = new File(storageDir, imgFileName);
                            Toast.makeText(mContext.getApplicationContext(), job.getString("response"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
        mCurrentPhotoPath = imageFile[0].getAbsolutePath();
        return imageFile[0];
    }

}

//    class DeleteDataTask extends AsyncTask<String, Void, String> {
//
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Deleting data...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                return deleteData(params[0]);
//            } catch (IOException ex) {
//                return "Network error !";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            System.out.println("delete"+result);
//            if(progressDialog != null){
//                progressDialog.dismiss();
//            }
//        }
//
//        private String deleteData(String urlPath) throws IOException {
//
//            String result = null;
//
//            URL url = new URL(urlPath+"/1");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setReadTimeout(10000 /* milliseconds */);
//            urlConnection.setConnectTimeout(10000 /* millisecods */);
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
//            urlConnection.connect();
//
//            System.out.println("delete: "+urlConnection.getResponseCode());
//
//            if (urlConnection.getResponseCode() == 200) {
//                result = "Delete Successfully !";
//            } else {
//                result = "Delete failed !";
//            }
//
//            return result;
//        }
//    }
//}