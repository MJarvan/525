package com.example.drss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.drss.R;
import com.example.drss.adapter.SearchShopAdapter;
import com.example.drss.bean.ShopBean;
import com.example.drss.dbadapter.DBUtils;


public class SearchActivity extends Activity {
	Context context;
	   
	private SearchShopAdapter searchshopAdapter;
	ListView listView;
    EditText edit_search;
    ImageButton iv_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=SearchActivity.this;
		DBUtils dbUtils=new DBUtils(context);
		dbUtils.initDB();
		setContentView(R.layout.activity_search);
		listView=(ListView)findViewById(R.id.lv_search);
		edit_search=(EditText)findViewById(R.id.edit_search);
	    iv_search=(ImageButton)findViewById(R.id.iv_search);
		iv_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchshopAdapter=new SearchShopAdapter(context,edit_search.getText().toString());
				listView.setAdapter(searchshopAdapter);
			}
		});

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ShopBean shopBean= searchshopAdapter.getShopList().get(position);
				Intent intent = new Intent(context,RecipeActivity.class);
				intent.putExtra("shop_id", shopBean.getShop_id());
				intent.putExtra("name",shopBean.getShop_name());
				intent.putExtra("tel", shopBean.getShop_tel());
				startActivity(intent);
			}
		});

	}

}
