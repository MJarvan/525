package com.example.drss.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.drss.R;
import com.example.drss.fragment.FragmentAbout;
import com.example.drss.fragment.FragmentHome;
import com.example.drss.fragment.FragmentLike;
import com.example.drss.fragment.FragmentShop;

public class MainActivity extends FragmentActivity{

	FragmentManager manager;
	List<Fragment> fragments;
	RadioGroup radioGroup;
	int choosed=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioGroup=(RadioGroup)findViewById(R.id.radioGroup1);
	
		radioGroup.check(R.id.radio_home);
		initFragments();
	
		FragmentTransaction transaction=manager.beginTransaction();
		transaction.add(R.id.container_tab, fragments.get(0));
		transaction.commit();

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int current = 0;
				switch (checkedId) {
					case R.id.radio_home:
						current=0;
						break;
					case R.id.radio_shop:
						current=1;
						break;
					case R.id.radio_like:
						current=2;
						break;
					case R.id.radio_about:
						current=3;
						break;
				}
				if(choosed!=current){
					choosed=current;
					
					FragmentTransaction transaction=manager.beginTransaction();
					transaction.replace(R.id.container_tab, fragments.get(choosed));
					transaction.commit();
				}
			}
		});

	}
	void initFragments() {
		manager=getSupportFragmentManager();
		fragments=new ArrayList<Fragment>();
		FragmentHome fragmentHome=new FragmentHome();
		FragmentShop fragmentShop=new FragmentShop();
		FragmentLike fragmentLike=new FragmentLike();
		FragmentAbout fragmentAbout=new FragmentAbout();
		fragments.add(fragmentHome);
		fragments.add(fragmentShop);
		fragments.add(fragmentLike);
		fragments.add(fragmentAbout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
