package android.example.cs496;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.ui.main.SectionsPagerAdapter;
import android.example.cs496.ui.main.TabFragment1;
import android.example.cs496.ui.main.TabFragment2;
import android.example.cs496.ui.main.TabFragment4;
import android.example.cs496.ui.main.fragment1.phonebook.GroupPhoneBook;
import android.example.cs496.ui.main.fragment4.ItemObject;
import android.example.cs496.ui.main.fragment4.UserItem;
import android.example.cs496.ui.main.fragment4.Menus;
import android.example.cs496.ui.main.fragment4.Tab4Adapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.example.cs496.ui.main.fragment1.dummyData.setInitialData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "";
    ImageButton searchButton;
    ImageButton groupButton;
    private TabLayout tabs;
    private ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    public static ArrayList<Integer> imageList = new ArrayList<>();
    public static int lastImageNum = 1;
    public static int last_store_id = 1;
    Context context;
    private CallbackManager callbackManager;

    private ArrayList<String> storeArray = new ArrayList();
    private ArrayList<String> menuArray = new ArrayList();
    public static ArrayList<ItemObject> totalArray = new ArrayList();
    public static UserItem userInfo = new UserItem();


    LoginButton facebook_login;
    Button set_button;
    LinearLayout is_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        checkPermissions();
        new GetStores().execute("http://143.248.36.220:3000/api/stores");
        System.out.println("last_store"+last_store_id);
        initView();
        facebook_login();
        new Description().execute(); // 받아오고 연결하는 과정


        //new GetDataTask().execute("http://143.248.36.218:3000/api/phones"); 전체 불러옴
        //new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone"); 주소록 한 명 추가하기
        //new PutDataTask().execute("http://143.248.36.218:3000/api/updatePhone/:id"); 주소록 바뀐 사람 추가하기, id 기준
        //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/:id"); 해당 id 삭제
        // 영연 143.248.36.218
        //new PostDataTask().execute("http://143.248.36.220:3000/api/addPhone", );

        addStore("학부 식당");

    }



    @Override
    public void onClick(View view){
        switch( view.getId() ){
            case R.id.search_button:
                //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/2");
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                //new PutDataTask().execute("http://143.248.36.218:3000/api/updatePhone/5","5","업데이트완료","010-0000-4141","업데이트그룹","이미지","업데이트이메일");
                //new DeleteDataTask().execute("http://143.248.36.218:3000/api/deletePhone/112","112","나영연포포스트","010-1212-4141","뉴그룹","이미지","이메일"); 해당 id 삭제
                //아이디도 스트링으로 받아서, 그 안에서 다시 정수로 변환해줘야
                //new PostDataTask().execute("http://143.248.36.218:3000/api/addPhone","112","나영연포포스트","010-1212-4141","뉴그룹","이미지","이메일");
                //Intent intent = new Intent(MainActivity.this, SearchPhoneBook.class);
                //startActivityForResult(intent,0);
                break;
            case R.id.group_button:
                //Toast.makeText(MainActivity.this, "group", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, GroupPhoneBook.class);
                startActivityForResult(intent,0);
                break;
        }
    }
    //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 객체 생성
    //import android.widget.PopupMenu.OnMenuItemClickListener 가 되어있어야 합니다.
    //OnMenuItemClickListener 클래스는 다른 패키지에도 많기 때문에 PopupMenu에 반응하는 패키지를 임포트하셔야 합니다.

    public void setupViewPager(ViewPager mViewPager) {
        sectionsPagerAdapter.addFragment(new TabFragment1(), "Phone");
        sectionsPagerAdapter.addFragment(new TabFragment2(), "Photos");
        sectionsPagerAdapter.addFragment(new TabFragment4(), "Restaurants");
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
        facebook_login = findViewById(R.id.login_button);
        is_login = findViewById(R.id.is_login);
        set_button = findViewById(R.id.set_btn);

        new GetDataTask().execute("http://143.248.36.220:3000/api/phones");
        new GetImageTask().execute("http://143.248.36.220:3000/api/photos");

        //Initializing ViewPager
        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setupWithViewPager(viewPager);
        //selecting tabs

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(!isLoggedIn){
            facebook_login.setVisibility(View.VISIBLE);
            is_login.setVisibility(View.INVISIBLE);
        } else {
            facebook_login.setVisibility(View.INVISIBLE);
            is_login.setVisibility(View.VISIBLE);
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //검색, 그룹 버튼이 Tab1에만 보이도록
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                if(tab.getPosition()==-1){
                    searchButton.setVisibility(View.VISIBLE);
                    groupButton.setVisibility(View.VISIBLE);
                }else {
                    searchButton.setVisibility(View.INVISIBLE);
                    groupButton.setVisibility(View.INVISIBLE);
                }
                if(!isLoggedIn){
                    facebook_login.setVisibility(View.VISIBLE);
                    is_login.setVisibility(View.INVISIBLE);
                } else {
                    facebook_login.setVisibility(View.INVISIBLE);
                    is_login.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p = new PopupMenu(
                        getApplicationContext(), // 현재 화면의 제어권자
                        view); // anchor : 팝업을 띄울 기준될 위젯
                getMenuInflater().inflate(R.menu.popup_setting, p.getMenu());
                // 이벤트 처리
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch( item.getItemId() ){//눌러진 MenuItem의 Item Id를 얻어와 식별
                            case R.id.logout:
                                LoginManager.getInstance().logOut();

                                facebook_login.setVisibility(View.VISIBLE);
                                is_login.setVisibility(View.INVISIBLE);
                                userInfo = new UserItem();

                                Toast.makeText(MainActivity.this, "LOGOUT", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.leave_account:

                                Ion.with(context)
                                        .load("http://143.248.36.220:3000/api/deleteUser/" + userInfo.getUserId())
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                // do stuff with the result or error
                                                Toast.makeText(context, "good!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                LoginManager.getInstance().logOut();
                                facebook_login.setVisibility(View.VISIBLE);
                                is_login.setVisibility(View.INVISIBLE);

                                userInfo = new UserItem();
                                Toast.makeText(MainActivity.this, "leave_account", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return false;
                    }
                });
                p.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popup_setting, menu);
        return true;
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

    class GetImageTask extends AsyncTask<String, Void, String> {

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
                setInitialImage(ja);
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

    void setInitialImage(JSONArray ImageArray) throws JSONException{

        imageList = new ArrayList<>();
        imageList.add(0);

        for (int i = 0; i < ImageArray.length(); i++) {
            int imageId;
             //한줄씩 object로 바꿔서 해당값 확인 후, datas에 add
             JSONObject jObject = ImageArray.getJSONObject(i);

             imageId = jObject.getInt("label");
             imageList.add(imageId);
             if(lastImageNum <= imageId) lastImageNum = imageId + 1;

            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Madcamp2/" + imageId + ".jpg");

            if (!file.exists()) {
                System.out.println("hello");
                String imgFileName = imageId + ".jpg";
                File storageDir = new File(Environment.getExternalStorageDirectory() + "/MadCamp2");
                Ion.with(this)
                        .load("http://143.248.36.220:3000/api/photo/" + imageId)
                        .write(new File(Environment.getExternalStorageDirectory() + "/Madcamp2/" + imageId + ".jpg"))
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File file) {
                                Toast.makeText(getApplicationContext(), "image load", Toast.LENGTH_SHORT).show();
                            }
                        });

                File new_file = new File(Environment.getExternalStorageDirectory().toString() + "/Madcamp2/" + imageId + ".jpg");
            }
            System.out.println("imageList"+imageList);
        }
    }

    private void facebook_login(){
        //facebook
        callbackManager = CallbackManager.Factory.create();
        facebook_login = findViewById(R.id.login_button);
        facebook_login.setReadPermissions("email");

        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String userId = loginResult.getAccessToken().getUserId();
                        Log.d("TAG", "페이스북 UserID -> " + loginResult.getAccessToken().getUserId());
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("TAG", "페이스북 로그인 결과"+response.toString());
                                try {
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    userLoginSuccess(id, name);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name");
                        request.setParameters(parameters);
                        request.executeAsync();

                        facebook_login.setVisibility(View.INVISIBLE);
                        is_login.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        Log.d("TAG", "취소됨");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        exception.printStackTrace();
                    }
                });
    }
    private void userLoginSuccess(final String id, final String name){

        userInfo = new UserItem(name, id);
        Ion.with(context)
                .load("http://143.248.36.220:3000/api/user/"+id)

                .asString()
                .setCallback(new FutureCallback<String>() {

                // .asJsonObject()
                // .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        System.out.println(result+" result");

                        JsonArray jsonArray = new JsonParser().parse(result).getAsJsonArray();
                        JsonObject jsonObject = (JsonObject) jsonArray.get(0);
                        if(jsonObject.get("java") != null){
                            JsonObject json = new JsonObject();
                            json.addProperty("id", id);
                            json.addProperty("name", name);

                            Ion.with(context)
                                    .load("http://143.248.36.220:3000/api/addUser")
                                    .setJsonObjectBody(json)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            // do stuff with the result or error
                                            Toast.makeText(context, "good!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    class GetStores extends AsyncTask<String, Void, String> {

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
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject j = ja.getJSONObject(i);
                    if(j.getInt("id") >= last_store_id) last_store_id = j.getInt("id") + 1;
                }
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

    private int addStore(String store_name) {
        Ion.with(context)
                .load("http://143.248.36.220:3000/api/addStore")
                .setBodyParameter("id", String.valueOf(last_store_id))
                .setBodyParameter("name", store_name)
                .asJsonArray();

        last_store_id += 1;
        return last_store_id - 1;
    }



    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
//            progressDialog = new ProgressDialog(context); // 문제 생기면 바로 지우기
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("잠시 기다려 주세요.");
//            progressDialog.show();

        }
        protected Void doInBackground(Void... params) {
            try {
                totalArray = new ArrayList<>();
                Document doc = Jsoup.connect("https://bds.bablabs.com/restaurants?campus_id=JEnfpqCUuR").get();;
                Elements elements = doc.select("h4.card-title");
                Elements menus = doc.select("div.card-title");
                int mSize = elements.size();
                System.out.println("#of stores:"+elements.size());

                for(Element element : elements) {
                    String my_title = element.text();
                    storeArray.add(my_title);
                }
                for(Element element : menus){
                    String my_menu = element.text();
                    menuArray.add(my_menu);
                }
                for(int i = 0; i <= mSize - 1; i++){
                    String store = storeArray.get(i);
                    String menu = menuArray.get(i);
                    String[] menuRefined = refineString(menu);
                    ItemObject itemObject = makeObject(store, menuRefined);
                    totalArray.add(itemObject);
                    System.out.println(itemObject.getTitle());
                }

                // 토탈어레이를 이용해서 DB에 없는 가게와 메뉴를 추가하고, totalArray의 ItemObject의 attr 결정
//                initialization(totalArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.

//            progressDialog.dismiss();
        }
    }

//    public void initialization(ArrayList<ItemObject> totalArray){
//        for(int i=0; i<= totalArray.size()-1; i++){
//            ItemObject itemObject = totalArray.get(i);
//            String storeName = itemObject.getTitle();
//            if(디비 안에 storeName 을 name으로 갖는 store가 없다면){
////                //디비 안에 새로운 Store 객체 추가, 새로운 아이디, 비어 있는 scoreArray를 갖는다.
////                // 그리고 item.setId(storeId)
//                //storeId =totalArray.get(i).getId;
//            }else{
//                //
//            }
//            ArrayList<Menus> menusArrayList = totalArray.get(i).getMenus();
//            for(int j=0; j<= menusArrayList.size()-1;j++ ){
//                Menus menus = menusArrayList.get(j);
//                ArrayList<android.example.cs496.ui.main.fragment4.Menu> menuArrayOfMenus = menus.getmMenu();
//                for(int k=0; k<=menuArrayOfMenus.size()-1;k++){
//                    android.example.cs496.ui.main.fragment4.Menu menu =menuArrayOfMenus.get(k);
//                    String menuName = menu.getMenuName();
//                    if(storeId를 갖는 store의 scoreArray에 menuName을 갖는 엘레멘트가 없다면 ){
//                        //중복되지 않는 아이디인 menuId를 생성, votedNumber=0,totalNumber=0으로 갖는 엘레멘트 추가
//                        //menu.setId, menu.setVotednumber, menuSetTotalNumber
//                    }else{
//                         //menu.setId, menu.setVotednumber, menuSetTotalNumber
//                    }
//
//                }
//            }
//        }

//    }
    public String[] refineString(String string){
        string = string.trim();
        if(string.startsWith("식당에서")){
            String[] list = new String[1];
            list[0] ="식당에서 메뉴를 업로드하지 않았습니다.";
            return  list;
        }else{
            string = string.replace("₩ ", "₩");
            string = string.replace("-마로니애- ", "");
            string = string.replace("메뉴 ", "메뉴");
            string = string.replace("0 ","0");
            string = string.replace("> ", ">");
            string = string.replace("0원>","0원> ");
            String[] list = string.split(" ");
            return  list;
        }
    }



    public ItemObject makeObject(String store, String[] list){
        int nowTag = -1;

        ArrayList<android.example.cs496.ui.main.fragment4.Menu> new_menus = new ArrayList<>();
        ItemObject result;
        ArrayList<Menus> map = new ArrayList<>();
        for(int i = 0; i<=list.length-1; i++){
            if(list[i].startsWith("<")||list[i].startsWith("(한식")||list[i].startsWith("(죽식")){ // 태그 일 때, 다른 태그도 있을 수 있음, 북측의 일품메뉴는 별로 시작함
                if(nowTag != -1){
                    String key = list[nowTag];
                    ArrayList<android.example.cs496.ui.main.fragment4.Menu> value = new_menus;
                    map.add(new Menus(key, value));
                }
                nowTag = i;
                new_menus = new ArrayList<>();
            }
            else {
                if(nowTag == -1){
                    String key = list[i];// 맵에 더하기
                    android.example.cs496.ui.main.fragment4.Menu menu = new android.example.cs496.ui.main.fragment4.Menu(list[i],0,1,1);
                    new_menus = new ArrayList<>();
                    new_menus.add(menu);
                    map.add(new Menus(key, new_menus));
                }
                else {
                    new_menus.add(new android.example.cs496.ui.main.fragment4.Menu(list[i],0,1,1));
                }
            }
        }
        if(nowTag != -1){
            String key = list[nowTag];
            ArrayList<android.example.cs496.ui.main.fragment4.Menu> value = new_menus;
            map.add(new Menus(key, value));
        }
        result = new ItemObject(store, map);
        return result;


    }


//    private void getHashKey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d(TAG, "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

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