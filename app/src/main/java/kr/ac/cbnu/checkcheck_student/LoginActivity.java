package kr.ac.cbnu.checkcheck_student;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_STORAGE = 1111;

    JSONArray results = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission();
        login();
    }

    public void login(){
        final EditText idText = (EditText)findViewById(R.id.edit_id);
        final EditText passwordText = (EditText)findViewById(R.id.edit_password);
        final ImageButton btn_login = (ImageButton)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = idText.getText().toString();
                final String password = passwordText.getText().toString();

                //4. 콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            results = jsonResponse.getJSONArray("response");
                            JSONObject c = results.getJSONObject(0);
                            String success = c.getString("success");

                            //서버에서 보내준 값이 true이면?
                            if (success.equals("true")) {
                                int studentnumber = c.getInt("studentnumber");
                                String studname = c.getString("studname");
                                String major = c.getString("major");

                                Global.getInstance().setStunumber(studentnumber);
                                Global.getInstance().setStuname(studname);
                                Global.getInstance().setStumajor(major);


                                //로그인에 성공했으므로 MainActivity로 넘어감
                                Intent intent = new Intent(LoginActivity.this, StatsActivity.class);
                                startActivity(intent);
                            } else{//로그인 실패시
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                builder.setMessage("로그인에 실패했습니다.\n아이디와 비밀번호를 확인해주세요")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("인터넷 사용 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_STORAGE:
                for(int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] < 0){
                        Toast.makeText(LoginActivity.this, "권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }

}