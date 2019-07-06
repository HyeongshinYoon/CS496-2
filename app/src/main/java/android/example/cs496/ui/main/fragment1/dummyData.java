package android.example.cs496.ui.main.fragment1;

import android.app.Notification;
import android.app.ProgressDialog;
import android.example.cs496.MainActivity;
import android.example.cs496.R;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class dummyData {

    static JSONArray personArray;
    static List<RecyclerItem> datas = new ArrayList<>();
    private static int lastNum = 0;

    public static void setInitialData(JSONArray personArray) throws JSONException {
        datas = new ArrayList<>();
        RecyclerItem data = new RecyclerItem(0, "", R.drawable.ic_addperson, "", "", "");
        datas.add(data);
        //제이슨 어레이를 받아 그 안에 있는 제이슨 오브젝트 하나하나를 리사이클러 아이템으로 바꾼 후,
        for (int i = 0; i < personArray.length(); i++) {
            int img;
            String phone, group, email;
            //한줄씩 object로 바꿔서 해당값 확인 후, datas에 add
            JSONObject jObject = personArray.getJSONObject(i);

            int id = jObject.getInt("id");
            String name = jObject.getString("name");
            phone = jObject.getString("phone");

            if(jObject.isNull("img")){
                img = R.drawable.ic_launcher_foreground_primarylight;
            }else {
                img = R.drawable.ic_launcher_foreground_primarylight;
                //img = jObject.getInt("img");
            }
            if(jObject.isNull("group")){
                group = "";
            }else {
                group = jObject.getString("group");
            }
            if(jObject.isNull("email")) {
                email = "";
            }else {
                email = jObject.getString("email");
            }

            data = new RecyclerItem(id, name, img, phone, group, email);
            datas.add(data);

            if(lastNum <= id) lastNum = id + 1;
        }
        //이름순 정렬 - sort (RecyclerItem)
        Collections.sort(datas);
    }

    public static List<RecyclerItem> refreshData() {
        Collections.sort(datas);
        return datas;
    }

    public static void editData(int position, RecyclerItem new_item) {

        // PutDataTask
        int real_position = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getId() == position) {
                real_position = i;
                break;
            }
        }
        datas.remove(real_position);
        datas.add(new_item);
        JSONObject dataToSend = new JSONObject();
        try {
            dataToSend.put("id", new_item.getId());
            dataToSend.put("name", new_item.getName());
            dataToSend.put("phone", new_item.getPhone());
            dataToSend.put("group", new_item.getGroup());
            dataToSend.put("img", new_item.getImg());
            dataToSend.put("email", new_item.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(datas);
    }

    public static void deleteData(int position, RecyclerItem new_item) {

        // DeleteDataTask
        int real_position = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getId() == position) {
                real_position = i;
                break;
            }
        }
        datas.remove(real_position);
    }

    public static int insertData(RecyclerItem new_item) {

        // PostDataTask
        new_item.setImg(R.drawable.ic_launcher_foreground_primarylight);
        new_item.setId(lastNum);
        lastNum += 1;
        datas.add(new_item);
        Collections.sort(datas);
        return new_item.getId();
    }
}