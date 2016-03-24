package com.android.nxd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText mAccount;
	private EditText mPwd;
	private Button mRegisterButton;
	private Button mLoginButton;
	private Button mCancleButton;
	private View loginView;
	private View loginSuccessView;
	private TextView loginSuccessShow;
	private UserDataManager mUserDataManager;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		mContext = this;

		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mRegisterButton = (Button) findViewById(R.id.login_btn_register);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
		loginView=findViewById(R.id.login_view);
		loginSuccessView=findViewById(R.id.login_success_view);
		loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

		mRegisterButton.setOnClickListener(mListener);
		mLoginButton.setOnClickListener(mListener);
		mCancleButton.setOnClickListener(mListener);
		
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
	}

	OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn_register:
				register();
				break;
			case R.id.login_btn_login:
				login();
				break;
			case R.id.login_btn_cancle:
				cancle();
				break;
			}
		}
	};

	public void login() {

		if (isUserNameAndPwdValid()) {


			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			//int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
			if("123".equals(userPwd)){
				//login success
				/**loginView.setVisibility(View.GONE);
				loginSuccessView.setVisibility(View.VISIBLE);
				loginSuccessShow.setText(getString(R.string.user_login_sucess, userName));*/
				Toast.makeText(this, getString(R.string.login_sucess),
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext,twoActivity.class);
				startActivity(intent);
			}else {
				//login failed,user does't exist
				Toast.makeText(this, getString(R.string.login_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void register() {
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			//check if user name is already exist
			int count=mUserDataManager.findUserByName(userName);
			
			if(count>0){
				Toast.makeText(this, getString(R.string.name_already_exist, userName),
						Toast.LENGTH_SHORT).show();
				return ;
			}
			
			UserData mUser = new UserData(userName, userPwd);
			mUserDataManager.openDataBase();
			long flag = mUserDataManager.insertUserData(mUser);
			if (flag == -1) {
				Toast.makeText(this, getString(R.string.register_fail),
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, getString(R.string.register_sucess),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void cancle() {
		/**mAccount.setText("");
		mPwd.setText("");*/
		finish();
		System.exit(0);
	}

	public boolean isUserNameAndPwdValid() {
		if (mAccount.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.account_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.pwd_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	@Override
	protected void onResume() {
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
        }
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		if (mUserDataManager != null) {
			mUserDataManager.closeDataBase();
			mUserDataManager = null;
        }
		super.onPause();
	}
}