package com.jinfukeji.jinyihuiup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jinfukeji.jinyihuiup.JinYiHuiApplication;
import com.jinfukeji.jinyihuiup.MainActivity;
import com.jinfukeji.jinyihuiup.R;
import com.jinfukeji.jinyihuiup.bean.Message;
import com.jinfukeji.jinyihuiup.bean.User;
import com.jinfukeji.jinyihuiup.utils.DialogUtils;

import java.util.HashMap;
import java.util.Map;


public class Denglu extends AppCompatActivity {
    private ImageView denglu_return_img;
    private ImageView denglu_home_img;
    private ImageView denglu_img;
    private EditText denglu_mingzi_text;
    private EditText denglu_mima_text;
    private TextView wangjiMM_text;
    private TextView zhuce_text;
    String loginId,password;
    String url = JinYiHuiApplication.URL_BOOT +"user/login";
    User user;
    RequestQueue queue;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu);
        isLogin = JinYiHuiApplication.getInstace().isLogin();
        if(isLogin){
            startMainActivity();
        }else {
            queue = Volley.newRequestQueue(this);
            //初始化控件
            initView();
            initData();
            setOnClikListener();
        }
    }

    //view控件的监听事件
    private void setOnClikListener() {
        denglu_return_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Denglu.this.finish();
            }
        });
        denglu_home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Denglu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        zhuce_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Denglu.this, Zhuce.class);
                startActivity(intent);
            }
        });
        /*wangjiMM_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });*/
        denglu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                if (TextUtils.isEmpty(loginId)) {
                    denglu_mingzi_text.setError("用户名不能为空");
                    denglu_mingzi_text.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    denglu_mima_text.setError("密码不能为空");
                    denglu_mima_text.requestFocus();
                }
                login(loginId,password);
            }
        });
    }

    private void initData() {
        loginId = denglu_mingzi_text.getText().toString();
        password = denglu_mima_text.getText().toString();
    }

    //初始化控件
    private void initView() {
        denglu_return_img= (ImageView) findViewById(R.id.denglu_return_img);
        denglu_home_img= (ImageView) findViewById(R.id.denglu_home_img);
        denglu_img= (ImageView) findViewById(R.id.denglu_img);
        denglu_mingzi_text= (EditText) findViewById(R.id.denglu_mingzi_text);
        denglu_mima_text= (EditText) findViewById(R.id.denglu_mima_text);
        wangjiMM_text= (TextView) findViewById(R.id.wangjiMM_text);
        zhuce_text= (TextView) findViewById(R.id.zhuce_text);
    }

    //使用volley进行登录操作
    public void login(String nameortele, final String password) {
        user = new User();
        user.setUsername(loginId);
        user.setPassword(password);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.contains("ok")){
                    Gson gson = new Gson();
                    Message message = gson.fromJson(s, Message.class);
                    isLogin = true;
                    JinYiHuiApplication.getInstace().setUser(message.getUser());
                    JinYiHuiApplication.getInstace().setLogin(isLogin);
                    startMainActivity();
                    DialogUtils.createToasdt(Denglu.this,message.getMessage());
                }else {
                    DialogUtils.createToasdt(Denglu.this,"用户名或者密码错误");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogUtils.createToasdt(Denglu.this,"请检查网络连接是否正确");
                    }
                });
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",loginId);
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
    }

    private void startMainActivity() {
        Intent intent = new Intent(Denglu.this, MainActivity.class);
        intent.putExtra("isLogin", isLogin);
        startActivity(intent);
    }
}
