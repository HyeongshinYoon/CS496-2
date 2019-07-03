package android.example.cs496.ui.main.fragment1.phonebook;

import android.content.Intent;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.example.cs496.ui.main.fragment1.dummyData.editData;
import static android.example.cs496.ui.main.fragment1.dummyData.insertData;

//import static android.example.cs496.ui.main.fragment1.phoneBookLoader.EditContactsInfo;


public class EditPhoneBook extends AppCompatActivity implements View.OnClickListener {

    private Button mSave, mCancel;
    private EditText textName, textPhone, textGroup, textEmail;
    private RecyclerItem mRecycelerItem;
    private int mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PhoneBookPagerAdapter PhoneBookPagerAdapter = new PhoneBookPagerAdapter(getSupportFragmentManager());
        setContentView(R.layout.sub_fragment1_edit);
        //Initializing ViewPager

        Intent intent = getIntent();
        mRecycelerItem = (RecyclerItem) intent.getSerializableExtra("select");
        mState = intent.getIntExtra("state", 0);
        //mRecycelerItem = getPositionData(mPosition);

        ImageView imageView = findViewById(R.id.iv_set_img);
        imageView.setImageResource(R.drawable.ic_launcher_foreground);
        int colorPrimaryLight = getResources().getColor(R.color.colorPrimaryLight);
        ShapeDrawable bgShape = new ShapeDrawable(new OvalShape());
        bgShape.setTint(colorPrimaryLight);
        imageView.setBackground(bgShape);
        imageView.setClipToOutline(true);
        textName = findViewById(R.id.tv_sub_edit_name);
        textName.setText(mRecycelerItem.getName());
        textPhone = findViewById(R.id.tv_sub_edit_phone);
        textPhone.setText(mRecycelerItem.getPhone());
        textGroup = findViewById(R.id.tv_sub_edit_group);
        textGroup.setText(mRecycelerItem.getGroup());
        textEmail = findViewById(R.id.tv_sub_edit_email);
        textEmail.setText(mRecycelerItem.getEmail());

        mSave = findViewById(R.id.set_save);
        mSave.setOnClickListener(this);
        mCancel = findViewById(R.id.set_cancel);
        mCancel.setOnClickListener(this);
        setResult(0);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.set_save:
                if(textName.getText().toString().isEmpty() || textPhone.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_LONG).show();
                    break;
                }

//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, mRecycelerItem.getPersonId()));
//                intent.putExtra(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, textName.getText().toString());
//                intent.putExtra(ContactsContract.CommonDataKinds.Phone.NUMBER, textName.getText().toString());
//                intent.putExtra(ContactsContract.CommonDataKinds.Email.ADDRESS, textEmail.getText().toString());
//                startActivity(intent);
                RecyclerItem newRecyclerItem
                        = new RecyclerItem(
                        mRecycelerItem.getId(),
                        textName.getText().toString(),
                        mRecycelerItem.getImg(),
                        textPhone.getText().toString(),
                        textGroup.getText().toString(),
                        textEmail.getText().toString());
                if(mState == 1){
                    insertData(newRecyclerItem);
                    Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(1, intent);
                }
                if(mState == 2){
                    editData(mRecycelerItem.getId(), newRecyclerItem);
                    Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("new_item", newRecyclerItem);
                    setResult(1, intent);
                }
                finish();
                break;
            case R.id.set_cancel:
                setResult(0);
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}