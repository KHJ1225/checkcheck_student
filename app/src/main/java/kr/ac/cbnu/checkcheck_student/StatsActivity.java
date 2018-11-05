package kr.ac.cbnu.checkcheck_student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private ListView listView;
    ArrayList<HashMap<String, String>> attendancesList;
    JSONArray attendances = null;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        attendancesList = new ArrayList<HashMap<String, String>>();

        TextView greeting = (TextView)findViewById(R.id.textView_greeting);
        greeting.setText(Global.getInstance().getStunumber() + " " + Global.getInstance().getStumajor()
                            + " " + Global.getInstance().getStuname() + "님, 안녕하세요!");

        settingListTitle();
        settingList();


    }



    private void settingList(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    attendances = jsonResponse.getJSONArray("response");

                    for (int i = 0; i < attendances.length(); i++) {
                        JSONObject c = attendances.getJSONObject(i);
                        String success = c.getString("success");
                        //Toast.makeText(getApplicationContext(), c.getString("2nd"), Toast.LENGTH_LONG).show();

                        //서버에서 보내준 값이 true이면?
                        if (success.equals("true")) {
                            String classname = c.getString("classname");
                            String res_1st = (c.getString("1st").equals("null")?".":c.getString("1st"));
                            String res_2nd = (c.getString("2nd").equals("null")?".":c.getString("2nd"));
                            String res_3rd = (c.getString("3rd").equals("null")?".":c.getString("3rd"));
                            String res_4st = (c.getString("4st").equals("null")?".":c.getString("4st"));
                            String res_5st = (c.getString("5st").equals("null")?".":c.getString("5st"));
                            String res_6st = (c.getString("6st").equals("null")?".":c.getString("6st"));
                            String res_7st = (c.getString("7st").equals("null")?".":c.getString("7st"));
                            String res_8st = (c.getString("8st").equals("null")?".":c.getString("8st"));
                            String res_9st = (c.getString("9st").equals("null")?".":c.getString("9st"));
                            String res_10st = (c.getString("10st").equals("null")?".":c.getString("10st"));
                            String res_11st = (c.getString("11st").equals("null")?".":c.getString("11st"));
                            String res_12st = (c.getString("12st").equals("null")?".":c.getString("12st"));
                            String res_13st = (c.getString("13st").equals("null")?".":c.getString("13st"));
                            String res_14st = (c.getString("14st").equals("null")?".":c.getString("14st"));
                            String res_15st = (c.getString("15st").equals("null")?".":c.getString("15st"));
                            String res_16st = (c.getString("16st").equals("null")?".":c.getString("16st"));



                            HashMap<String, String> attendance = new HashMap<String, String>();

                            attendance.put("classname", classname);
                            attendance.put("1st", res_1st);
                            attendance.put("2nd", res_2nd);
                            attendance.put("3rd", res_3rd);
                            attendance.put("4st", res_4st);
                            attendance.put("5st", res_5st);
                            attendance.put("6st", res_6st);
                            attendance.put("7st", res_7st);
                            attendance.put("8st", res_8st);
                            attendance.put("9st", res_9st);
                            attendance.put("10st", res_10st);
                            attendance.put("11st", res_11st);
                            attendance.put("12st", res_12st);
                            attendance.put("13st", res_13st);
                            attendance.put("14st", res_14st);
                            attendance.put("15st", res_15st);
                            attendance.put("16st", res_16st);

                            int cnt_O = 0, cnt_X = 0;
                            for(String key : attendance.keySet()){
                                if(attendance.get(key).equals("O")){ cnt_O++; }
                                else if(attendance.get(key).equals("X")) { cnt_X++; }
                            }
                            attendance.put("cnt_O", Integer.toString(cnt_O));
                            attendance.put("cnt_X", Integer.toString(cnt_X));

                            attendancesList.add(attendance);
                        } else {//로그인 실패시
                            /*    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                builder.setMessage("로그인에 실패했습니다.\n아이디와 비밀번호를 확인해주세요")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();*/
                        }
                    }

                    ListAdapter adapter = new SimpleAdapter(
                            StatsActivity.this, attendancesList, R.layout.listview_item,
                            new String[]{"classname", "cnt_O","cnt_X","1st", "2nd", "3rd", "4st", "5st", "6st", "7st", "8st", "9st", "10st", "11st", "12st", "13st", "14st", "15st", "16st"},
                            new int[]{R.id.textview_classname, R.id.textview_cnt_O, R.id.textView_cnt_X, R.id.textView_1st, R.id.textView_2nd, R.id.textView_3rd, R.id.textView_4st, R.id.textView_5st,
                                    R.id.textView_6st, R.id.textView_7st, R.id.textView_8st, R.id.textView_9st, R.id.textView_10st,
                                    R.id.textView_11st, R.id.textView_12st, R.id.textView_13st, R.id.textView_14st, R.id.textView_15st, R.id.textView_16st}
                    );

                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        String id = Integer.toString(Global.getInstance().getStunumber());
        StatsRequest statsRequest = new StatsRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(StatsActivity.this);
        queue.add(statsRequest);
    }
    private void settingListTitle(){
        HashMap<String, String> title = new HashMap<String, String>();

        title.put("classname", "강의 이름");
        title.put("cnt_O", "출");
        title.put("cnt_X", "결");
        title.put("1st", "1주차");
        title.put("2nd", "2주차");
        title.put("3rd", "3주차");
        title.put("4st", "4주차");
        title.put("5st", "5주차");
        title.put("6st", "6주차");
        title.put("7st", "7주차");
        title.put("8st", "8주차");
        title.put("9st", "9주차");
        title.put("10st", "10주차");
        title.put("11st", "11주차");
        title.put("12st", "12주차");
        title.put("13st", "13주차");
        title.put("14st", "14주차");
        title.put("15st", "15주차");
        title.put("16st", "16주차");

        attendancesList.add(title);
    }
}