package com.example.drss.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends Activity implements AsyncInterface {

    Context context;
    Button buttonRegister;
    EditText editTextRegUser,editTextReg,editTextRegConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=RegisterActivity.this;
        initView();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    void initView(){
        editTextRegUser=(EditText)findViewById(R.id.et_reg_user);
        editTextReg=(EditText)findViewById(R.id.et_reg_pwd);
        editTextRegConfirm=(EditText)findViewById(R.id.et_reg_confirm);
        buttonRegister=(Button)findViewById(R.id.btn_reg);
    }

    void register(){
        String user=editTextRegUser.getText().toString();
        String pwd=editTextReg.getText().toString();
        String conPwd=editTextRegConfirm.getText().toString();
        if (pwd.equals(conPwd)&&pwd.length()!=0&&conPwd.length()!=0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            BasicNameValuePair namePair = new BasicNameValuePair("user", user);
            BasicNameValuePair pwdPair = new BasicNameValuePair("password", pwd);
            params.add(namePair);
            params.add(pwdPair);

            String registerurl = Config.SERVICE_URL + Config.REGISTER_PATH;

            MyAsyncTask myAsyncTask = new MyAsyncTask((AsyncInterface) context, registerurl, params);
            myAsyncTask.execute();
        }
    }

    public void parseResult(String result){
        try {
            JSONObject jsonObject=new JSONObject(result);
            int code=jsonObject.getInt("code");
            String message=jsonObject.getString("message");
            if (code==0){
                Toast.makeText(context, "注册成功！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
