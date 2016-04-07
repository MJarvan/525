package com.example.drss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drss.R;
import com.example.drss.asynctask.AsyncInterface;
import com.example.drss.asynctask.MyAsyncTask;
import com.example.drss.config.Config;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements AsyncInterface {

    Context context;
    EditText editTextUser,editTextPwd;
    Button buttonLogin;
    TextView textView;
    private SharedPreferences sp;
    private CheckBox rem_pwd, auto_login;
    private String userNameValue, passwordValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        context=LoginActivity.this;
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        initView();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameValue = editTextUser.getText().toString();
                passwordValue = editTextPwd.getText().toString();
                if (userNameValue.equals("ttt") && passwordValue.equals("ttt")) {
                    Toast.makeText(LoginActivity.this, "登录成功",
                            Toast.LENGTH_SHORT).show();
                    // 登录成功和记住密码框为选中状态才保存用户信息
                    if (rem_pwd.isChecked()) {
                        // 记住用户名、密码
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        editor.commit();
                    }
                    // 跳转界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//                // 登录成功和记住密码框为选中状态才保存用户信息
//                if (rem_pwd.isChecked()) {
//                    // 记住用户名、密码
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("USER_NAME", userNameValue);
//                    editor.putString("PASSWORD", passwordValue);
//                    editor.commit();
//                }
//                Intent intent=new Intent(context,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        // 判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            // 设置默认是记录密码状态
            rem_pwd.setChecked(true);
            editTextUser.setText(sp.getString("USER_NAME", ""));
            editTextPwd.setText(sp.getString("PASSWORD", ""));
            // 判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                // 设置默认是自动登录状态
                auto_login.setChecked(true);
                // 跳转界面
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
        // 监听记住密码多选框按钮事件
        rem_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (rem_pwd.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        // 监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void initView(){
        editTextUser=(EditText)findViewById(R.id.et_login_user);
        editTextPwd=(EditText)findViewById(R.id.et_login_pwd);
        buttonLogin=(Button)findViewById(R.id.btn_login_login);
        textView=(TextView)findViewById(R.id.tv_login_register);
        rem_pwd = (CheckBox) findViewById(R.id.rem_pwd);
        auto_login = (CheckBox) findViewById(R.id.auto_login);
    }

    void login(){
        String user=editTextUser.getText().toString();
        String pwd=editTextPwd.getText().toString();
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        BasicNameValuePair namePair=new BasicNameValuePair("user",user);
        BasicNameValuePair pwdPair=new BasicNameValuePair("password",pwd);
        params.add(namePair);
        params.add(pwdPair);

        String loginurl= Config.SERVICE_URL+Config.LOGIN_PATH;

        MyAsyncTask myAsyncTask=new MyAsyncTask((AsyncInterface)context,loginurl,params);
        myAsyncTask.execute();
    }

    public void parseResult(String result){
        try {
            JSONObject jsonObject=new JSONObject(result);
            int code=jsonObject.getInt("code");
            String message=jsonObject.getString("message");
            if (code==0){
                Toast.makeText(context,"登录成功！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
