package android.example.cs496;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.app.ProgressDialog;
import android.example.cs496.ui.main.SectionsPagerAdapter;
import android.example.cs496.ui.main.TabFragment1;
import android.example.cs496.ui.main.TabFragment2;
import android.example.cs496.ui.main.TabFragment3;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.example.cs496.ui.main.fragment1.dummyData;
import android.example.cs496.ui.main.fragment1.phonebook.GroupPhoneBook;
import android.example.cs496.ui.main.fragment1.phonebook.SearchPhoneBook;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.example.cs496.ui.main.SectionsPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.example.cs496.ui.main.fragment1.dummyData.setInitialData;

public class MainActivity extends AppCompatActivity {
    ImageButton searchButton;
    ImageButton groupButton;
    private TabLayout tabs;
    private ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    Context context;
    private TextView mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        initView();
        ///////
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("KEY","value");
//
//        System.out.println(map.get("KEY"));
//        System.out.println(map[0]);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/2");
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                //new PutDataTask().execute("http://143.248.36.218:3000/api/updatePhone/5","5","업데이트완료","010-0000-4141","업데이트그룹","이미지","업데이트이메일");
                //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/112","112","나영연포포스트","010-1212-4141","뉴그룹","이미지","이메일"); 해당 id 삭제
                //아이디도 스트링으로 받아서, 그 안에서 다시 정수로 변환해줘야
                //new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone","112","나영연포포스트","010-1212-4141","뉴그룹","이미지","이메일");
                //Intent intent = new Intent(MainActivity.this, SearchPhoneBook.class);
                //startActivityForResult(intent,0);

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
        mResult = (TextView) findViewById(R.id.test);

        //new GetDataTask().execute("http://143.248.36.218:3000/api/phones"); 전체 불러옴
        //new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone"); 주소록 한 명 추가하기
        //new PutDataTask().execute("http://143.248.36.218:3000/api/updatePhone/:id"); 주소록 바뀐 사람 추가하기, id 기준
        //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/:id"); 해당 id 삭제
        // 영연 143.248.36.218
        //new PostDataTask().execute("http://143.248.36.220:3000/api/addPhone", );

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

    public void initView(){
        //Initializing the TabLayout;
        tabs = findViewById(R.id.tabs);
        searchButton = findViewById(R.id.search_button);
        groupButton =  findViewById(R.id.group_button);
        //new dummyData();
        new GetDataTask().execute("http://143.248.36.218:3000/api/phones");
        //setInitialData();
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
    // 데이터베이스에서 가져오거나 보내는 4가지
    // 데이터베이스에서 모든 데이터 가져오기
    class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            // initialize and config request, then connect to server
            try {
                return getData(params[0]);
            }catch (IOException ex){
                return "Network error !";
            }

        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            // Result(jSONArray가 String 형식으로 들어옴)
            JSONArray ja = null;
            try {
                // type을 JSONArray로 바꾼 후, setInitialData(JSONArray) 실행
                ja = new JSONArray(result);
                setInitialData(ja);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }

        private String getData(String urlPath) throws IOException {
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* millisecods */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }

            } finally {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }

            System.out.println(result);
            return result.toString();
        }
    }

    //new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone"); 주소록 한 명 추가하기
//    class PostDataTask extends AsyncTask<String, Void, String> {
//
//        ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Inserting data...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params){
//
//            try {
//                return postData(params[0], params[1], params[2], params[3], params[4], params[5],params[6]);
//            } catch (IOException ex){
//                return "Network error !";
//            } catch (JSONException ex){
//                return "Data Invalid !";
//            }
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            mResult.setText(result);
//            if(progressDialog != null) {
//                progressDialog.dismiss();
//            }
//        }
//
//        private String postData(String urlPath, String strId, String name, String phone, String group, String img, String email) throws IOException, JSONException {
//
//            StringBuilder result = new StringBuilder();
//            BufferedWriter bufferedWriter = null;
//            BufferedReader bufferedReader = null;
//
//            int id = Integer.parseInt(strId);
//
//            try {
//                JSONObject dataToSend = new JSONObject();
//                dataToSend.put("id", id);
//                dataToSend.put("name", name);
//                dataToSend.put("phone", phone);
//                dataToSend.put("group", group);
//                dataToSend.put("img", "");
//                dataToSend.put("email", email);
//
//                System.out.println("send"+dataToSend);
//                URL url = new URL(urlPath);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setReadTimeout(10000 /* milliseconds */);
//                urlConnection.setConnectTimeout(10000 /* millisecods */);
//                urlConnection.setRequestMethod("POST");
//                urlConnection.setDoOutput(true); //enable output (body data)
//                urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
//                urlConnection.connect();
//
//                OutputStream outputStream = urlConnection.getOutputStream();
//                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//                bufferedWriter.write(dataToSend.toString());
//                bufferedWriter.flush();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result.append(line).append("\n");
//                }
//            } finally {
//                if( bufferedReader != null) {
//                    bufferedReader.close();
//                }
//                if(bufferedWriter != null){
//                    bufferedWriter.close();
//                }
//            }
//            return result.toString();
//        }
//    }

    //new PutDataTask().execute("http://143.248.36.218:3000/api/updatePhone/:id"); 주소록 바뀐 사람 추가하기, id 기준
    class PutDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Updating data...");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return putData(params[0], params[1], params[2], params[3], params[4], params[5],params[6]);
            } catch (IOException ex) {
                return "Network Error !";
            } catch (JSONException ex) {
                return "Data invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mResult.setText(result);
            System.out.println("update"+result);

            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }

        private String putData(String urlPath, String strId, String name, String phone, String group, String img, String email) throws IOException, JSONException {

            BufferedWriter bufferedWriter = null;
            String result = null;

            int id = Integer.parseInt(strId);

            try {
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("id", id);
                dataToSend.put("name", name);
                dataToSend.put("phone", phone);
                dataToSend.put("group", group);
                dataToSend.put("img", "");
                dataToSend.put("email", email);

                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* millisecods */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true); //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json"); //set header
                urlConnection.connect();
                // write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                System.out.println("ResponseCode: "+urlConnection.getResponseCode());

                if (urlConnection.getResponseCode() == 200) {
                    return "Update successfully !";
                } else {
                    return "Update failed !";
                }
            } finally {
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
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
//            mResult.setText(result);
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
//            URL url = new URL(urlPath);
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
}