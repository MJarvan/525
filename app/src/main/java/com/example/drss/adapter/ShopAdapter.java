package com.example.drss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.drss.R;
import com.example.drss.bean.ShopBean;
import com.example.drss.dbadapter.ShopDBAdapter;

import java.util.List;

public class ShopAdapter extends BaseAdapter{
    Context context;
    List<ShopBean> shopList;
    public ShopAdapter(Context context){
        this.context=context;
        setShopList();
    }

    private void setShopList(){
        ShopDBAdapter shopDBAdapter = new ShopDBAdapter(context);
        shopDBAdapter.openDB();

        ShopBean shopBean=new ShopBean();
       /* shopBean.setShop_id("sq1008");
        shopBean.getShop_id();
        shopBean.setShop_name("捐款资助");
        shopBean.getShop_name();
        shopBean.setShop_region("中山，惠州");
        shopBean.getShop_region();
        shopBean.setShop_address("中山，惠州");
        shopBean.getShop_address();
        shopBean.setShop_tel("2");
        shopBean.getShop_tel();*/
        shopDBAdapter.delete_byName(shopBean,"重庆鸡公煲");


        shopList=shopDBAdapter.queryAll();
        shopDBAdapter.closeDB();
    }

    public List<ShopBean> getShopList(){
        return shopList;
    }

    @Override
    public int getCount() {
        if (shopList==null){
            return 0;
        }else {
            return shopList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (shopList==null){
            return null;
        }else {
            return shopList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.listview_item_shop,null);
//            holder.imageView=(ImageView)convertView.findViewById(R.id.list_img);
            holder.ShopName=(TextView)convertView.findViewById(R.id.tv_shop_name);
            holder.ShopTel=(TextView)convertView.findViewById(R.id.tv_shop_tel);
            holder.ShopAddr=(TextView)convertView.findViewById(R.id.tv_shop_addr);

            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        ShopBean shopBean=shopList.get(position);
        holder.ShopName.setText(shopBean.getShop_name());
        holder.ShopTel.setText(shopBean.getShop_tel());
        holder.ShopAddr.setText(shopBean.getShop_address());
//        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        return convertView;

    }
    class ViewHolder{
//        ImageView imageView;
        TextView ShopName,ShopTel,ShopAddr;
    }


}
