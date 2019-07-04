package android.example.cs496;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.ui.main.SectionsPagerAdapter;
import android.example.cs496.ui.main.TabFragment1;
import android.example.cs496.ui.main.TabFragment2;
import android.example.cs496.ui.main.TabFragment3;
import android.example.cs496.ui.main.fragment1.dummyData;
import android.example.cs496.ui.main.fragment1.phonebook.GroupPhoneBook;
import android.example.cs496.ui.main.fragment1.phonebook.SearchPhoneBook;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;

import android.example.cs496.ui.main.SectionsPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.example.cs496.ui.main.fragment1.dummyData.setInitialData;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        ImageButton groupButton = (ImageButton) findViewById(R.id.group_button);
        initView(searchButton,groupButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchPhoneBook.class);
                startActivityForResult(intent,0);

            }
        });

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "group", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, GroupPhoneBook.class);
                startActivityForResult(intent,0);

            }
        });
    }

    public void setupViewPager(ViewPager mViewPager) {
        sectionsPagerAdapter.addFragment(new TabFragment1(), "Phone");
        sectionsPagerAdapter.addFragment(new TabFragment2(), "Photos");
        sectionsPagerAdapter.addFragment(new TabFragment3(), "Weather");
        mViewPager.setAdapter(sectionsPagerAdapter);
    }

    private void checkPermissions() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission, you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(new String[] {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.SEND_SMS})
                .check();
    }

    public void initView(final ImageButton searchButton, final ImageButton groupButton){
        //Initializing the TabLayout;
        tabs = findViewById(R.id.tabs);
        try {
            new dummyData();
            setInitialData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Initializing ViewPager
        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(viewPager);
        //selecting tabs
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //검색, 그룹 버튼이 Tab1에만 보이도록
                if(tab.getPosition()==0){
                    searchButton.setVisibility(View.VISIBLE);
                    groupButton.setVisibility(View.VISIBLE);
                }else {
                    searchButton.setVisibility(View.INVISIBLE);
                    groupButton.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


}