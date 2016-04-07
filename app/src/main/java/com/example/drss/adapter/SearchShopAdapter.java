package com.example.drss.adapter;


import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.drss.bean.ShopBean;
import com.example.drss.dbadapter.ShopDBAdapter;

public class SearchShopAdapter extends BaseAdapter {

	Context context;
	List<ShopBean> shopList;
	public SearchShopAdapter(Context context, String name) {
		this.context=context;
		setShopList(name);
	}

	private void setShopList(String name) {
		ShopDBAdapter shopDBAdapter = new ShopDBAdapter(context);
		shopDBAdapter.openDB();
		shopList=shopDBAdapter.queryByName(name);
		shopDBAdapter.closeDB();

	}
	
	public List<ShopBean> getShopList(){
		return shopList;
	}
	
	@Override
	public int getCount() {
		if(shopList==null){
		return 0;
		}else{
			return shopList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if(shopList==null){
			return null;
			}else{
				return shopList.get(position);
			}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if(convertView==null){
			textView=new TextView(context);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(30);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
		}else{
			textView=(TextView)convertView;
		}
		
		ShopBean shopBean=shopList.get(position);
		textView.setText(shopBean.getShop_name());
		return textView;
	}

}
