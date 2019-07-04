package android.example.cs496.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.MainActivity;
import android.example.cs496.ui.main.fragment2.Tab2Adapter;
import android.media.ExifInterface;
import android.net.Uri;
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

import android.example.cs496.R;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class TabFragment2 extends Fragment{

    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    GridView gridView;
    private Tab2Adapter adapter;

    private  Context mContext;

    private Activity activity;
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
        adapter = new Tab2Adapter(mContext, inflater, MainActivity.picArr);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parentView, View clickView, int position, long id){
                // 이미지 터치했을 때 동작하는 곳
                if (position == 0) {
                    takePhoto();
                }
                else {
                    Intent intent = new Intent(mContext, Fragment2SubActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    //사진 찍기 클릭
    public void takePhoto(){

        // 촬영 후 이미지 가져옴
        String state = Environment.getExternalStorageState();
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(mContext.getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }

                if(photoFile != null){
                    Uri providerURI = FileProvider.getUriForFile(mContext,activity.getPackageName(),photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        }else{
            Log.v("알림", "저장공간에 접근 불가능");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        photoURI = imgUri;

        switch (requestCode){
            case FROM_CAMERA : {
                //카메라 촬영
                try{
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Objects.requireNonNull(mContext).sendBroadcast(mediaScanIntent);
        MainActivity.imageList.add(contentUri);
        System.out.println(MainActivity.imageList);
        Toast.makeText(mContext,"사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }
}