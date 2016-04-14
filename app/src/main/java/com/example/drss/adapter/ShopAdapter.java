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

       /* ShopBean shopBean=new ShopBean();


        shopBean.setShop_id("sq1008");


        shopDBAdapter.delete_byName(shopBean, "佳味得");
        shopDBAdapter.delete_byName(shopBean,"重庆鸡公煲");
        shopDBAdapter.delete_byName(shopBean,"面之味");
        shopDBAdapter.delete_byName(shopBean,"秋野家");
        shopDBAdapter.delete_byName(shopBean,"重庆小面");
        shopDBAdapter.delete_byName(shopBean,"山西刀削面");
        shopDBAdapter.delete_byName(shopBean,"东北饺子馆");
        shopDBAdapter.delete_byName(shopBean,"台湾手抓饼");
        shopDBAdapter.delete_byName(shopBean,"天津小笼包");
        shopDBAdapter.delete_byName(shopBean,"麻辣小屋");
        shopDBAdapter.delete_byName(shopBean,"串串香");
        shopDBAdapter.delete_byName(shopBean,"黄焖鸡米饭");*/
       /* ShopBean shopBean=new ShopBean();
        shopBean.setShop_id("ha");
        shopBean.getShop_id();
        shopBean.setShop_name("bb");
        shopBean.getShop_name();
        shopBean.setShop_region("三期");
        shopBean.getShop_region();
        shopBean.setShop_address("三期七");
        shopBean.getShop_address();
        shopBean.setShop_tel("2");
        shopBean.getShop_tel();*/






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
