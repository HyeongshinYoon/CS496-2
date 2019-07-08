package android.example.cs496.ui.main.fragment1.phonebook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.example.cs496.ui.main.fragment1.RecyclerItem;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import static android.example.cs496.ui.main.fragment1.dummyData.editData;
import static android.example.cs496.ui.main.fragment1.dummyData.insertData;

//import static android.example.cs496.ui.main.fragment1.phoneBookLoader.EditContactsInfo;


public class EditPhoneBook extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mSave, mCancel;
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
        mRecycelerItem = (RecyclerItem) intent.getSerializableExtra("select"); //탭1에서 가져온 리사이클러 아이템
        mState = intent.getIntExtra("state", 0); // 탭1에서 가져온 스테이트
        //mRecycelerItem = getPositionData(mPosition);

        ImageView imageView = findViewById(R.id.iv_set_img);
        imageView.setImageResource(R.drawable.ic_launcher_foreground);
        int colorPrimaryLight = getResources().getColor(R.color.colorPrimaryLight);
        ShapeDrawable bgShape = new ShapeDrawable(new OvalShape());
        bgShape.setTint(colorPrimaryLight);
        imageView.setBackground(bgShape);
        imageView.setClipToOutline(true);
        textName = findViewById(R.id.tv_sub_edit_name);
        textName.setText(mRecycelerItem.getName()); // 네임이 없는데 setText를 하니깐 아무 것도 없는 공란이 뜨는 것
        textPhone = findViewById(R.id.tv_sub_edit_phone);
        textPhone.setText(mRecycelerItem.getPhone());
        textGroup = findViewById(R.id.tv_sub_edit_group);
        textGroup.setText(mRecycelerItem.getGroup());
        textEmail = findViewById(R.id.tv_sub_edit_email);
        textEmail.setText(mRecycelerItem.getEmail());

        mSave =  findViewById(R.id.set_save);
        mSave.setOnClickListener(this);
        mCancel = findViewById(R.id.set_cancel);
        mCancel.setOnClickListener(this);
        setResult(0);
    }

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

            //mResult.setText(result);
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

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.set_save: //저장 버튼 누를 경우
                if(textName.getText().toString().isEmpty() || textPhone.getText().toString().isEmpty()){ // 이름 또는 번호가 공란일 경우
                    Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_LONG).show(); // 저장 못하게 브레이크
                    break;
                }
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, mRecycelerItem.getPersonId()));
//                intent.putExtra(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, textName.getText().toString());
//                intent.putExtra(ContactsContract.CommonDataKinds.Phone.NUMBER, textName.getText().toString());
//                intent.putExtra(ContactsContract.CommonDataKinds.Email.ADDRESS, textEmail.getText().toString());
//                startActivity(intent);
                RecyclerItem newRecyclerItem // editText 에 있는 정보로 새로운 리사이클러 아이템 정의
                        = new RecyclerItem(
                        mRecycelerItem.getId(),
                        textName.getText().toString(),
                        mRecycelerItem.getImg(),
                        textPhone.getText().toString(),
                        textGroup.getText().toString(),
                        textEmail.getText().toString());
                if(mState == 1){ // 추가할 경우
                    int newId = insertData(newRecyclerItem);
                    String strId = Integer.toString(newId);
                    new PostDataTask().execute("http://143.248.36.220:3000/api/addPhone",strId,textName.getText().toString(),textPhone.getText().toString(),textGroup.getText().toString(),"",textEmail.getText().toString());
                    // 인서트되고 나서 id 정해짐
                    Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(1, intent);
                }
                if(mState == 2){ // 수정할 경우
                    editData(mRecycelerItem.getId(), newRecyclerItem);
                    String strId = Integer.toString(mRecycelerItem.getId());
                    new PutDataTask().execute("http://143.248.36.220:3000/api/updatePhone/"+strId,strId,textName.getText().toString(),textPhone.getText().toString(),textGroup.getText().toString(),"",textEmail.getText().toString());
                    System.out.println(strId);
                    Toast.makeText(getApplicationContext(), "수정 완료!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("new_item", newRecyclerItem);
                    setResult(1, intent);
                }
                finish();
                break;
            case R.id.set_cancel: // 취소 버튼 누를 경우
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