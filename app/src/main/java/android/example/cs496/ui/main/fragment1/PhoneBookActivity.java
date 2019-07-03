package android.example.cs496.ui.main.fragment1;

import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;
import android.example.cs496.ui.main.fragment1.phonebook.MessageSend;
import android.example.cs496.ui.main.fragment1.phonebook.PersonInformation;
import android.example.cs496.ui.main.fragment1.phonebook.PhoneBookPagerAdapter;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

//import android.example.cs496.ui.main.fragment1.phonebook.EditPhoneBook;

//import static android.example.cs496.ui.main.TabFragment1.getPositionData;


public class PhoneBookActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mCall, mMessage, mVideocall, mBack, mSet;
    private static ViewPager viewPager;
    private static RecyclerItem mRecycelerItem;
    PhoneBookPagerAdapter PhoneBookPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PhoneBookPagerAdapter = new PhoneBookPagerAdapter(getSupportFragmentManager());
        setContentView(R.layout.sub_fragment1);
        //Initializing ViewPager

        Intent intent = getIntent();
        mRecycelerItem = (RecyclerItem) intent.getSerializableExtra("select");

        personLoad();

        mCall = findViewById(R.id.sub_call);
        ((View) mCall).setOnClickListener(this);
        mMessage = findViewById(R.id.sub_message);
        mMessage.setOnClickListener(this);
        mVideocall = findViewById(R.id.sub_videocall);
        mVideocall.setOnClickListener(this);
        mBack = findViewById(R.id.sub_fragment1_back);
        mBack.setOnClickListener(this);
        mSet = findViewById(R.id.sub_fragment1_set);
        mSet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        String tel = "tel:" + mRecycelerItem.getPhone();
        switch(v.getId()){
            case R.id.sub_call:
                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                break;
            case R.id.sub_message:
                Intent intent = new Intent(this, MessageSend.class);
                intent.putExtra("phone", mRecycelerItem.getPhone());
                startActivity(intent);
                break;
            case R.id.sub_videocall:
                Toast.makeText(getApplicationContext(), "준비중입니다.", Toast.LENGTH_LONG).show();
                break;
            case R.id.sub_fragment1_back:
                onBackPressed();
                break;
            case R.id.sub_fragment1_set:
//                Intent mintent = new Intent(Intent.ACTION_EDIT);
//                mintent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, mRecycelerItem.getPersonId()));
                System.out.println("hello");
                Intent mintent = new Intent(this, EditPhoneBook.class);
                mintent.putExtra("select", mRecycelerItem);
                mintent.putExtra("state", 2);
                startActivityForResult(mintent, 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            mRecycelerItem = (RecyclerItem) data.getSerializableExtra("new_item");
        }
        personLoad();
    }

    public static String getPhone(){
        return mRecycelerItem.getPhone();
    }
    public static String getGroup(){
        return mRecycelerItem.getGroup();
    }
    public static String getEmail() {
        return mRecycelerItem.getEmail();
    }

    public void personLoad() {
        //mRecycelerItem = getPositionData(mPosition);

        //loadImageView();
        ImageView imageView = findViewById(R.id.iv_sub_img);
        imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_launcher_foreground_primarylight));
        ShapeDrawable bgShape = new ShapeDrawable(new OvalShape());
        bgShape.setTint(Color.WHITE);
        imageView.setBackground(bgShape);
        imageView.setClipToOutline(true);
        TextView textName = findViewById(R.id.tv_sub_name);
        textName.setText(mRecycelerItem.getName());
        TextView textGroup = findViewById(R.id.tv_sub_group);
        textGroup.setText(mRecycelerItem.getGroup());

        viewPager = findViewById(R.id.sub_view_pager);
        PhoneBookPagerAdapter.add(new PersonInformation());
        viewPager.setAdapter(PhoneBookPagerAdapter);
    }

//    public void loadImageView(){
//
//        ImageView imageView = findViewById(R.id.iv_sub_img);
//        imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_launcher_foreground));
//        Bitmap profile = loadContactPhoto(this.getContentResolver(),mRecycelerItem.getId(), mRecycelerItem.getImg());
//        if(profile != null) {
//            if(Build.VERSION.SDK_INT >=21){
//                imageView.setBackground(new ShapeDrawable((new OvalShape())));
//                imageView.setClipToOutline(true);
//            }
//            imageView.setImageBitmap(profile);
//        } else {
//            if(Build.VERSION.SDK_INT>=21){
//                imageView.setClipToOutline(false);
//            }
//        }
//    }
//
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
//        float resizing_size = 500;
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