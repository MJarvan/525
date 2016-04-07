package com.example.drss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drss.R;
import com.example.drss.bean.RecipeBean;
import com.example.drss.dbadapter.RecipeDBAdapter;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {
    Context context;
    List<RecipeBean> recipeList;

    public RecipeAdapter(Context context,String shop_id){
        this.context=context;
        setRecipeList(shop_id);
    }

    private void setRecipeList(String shop_id){
        RecipeDBAdapter recipeDBAdapter = new RecipeDBAdapter(context);
        recipeDBAdapter.openDB();


        RecipeBean recipeBean=new RecipeBean();
        recipeBean.setFood_id("f06");
        recipeBean.getFood_id();
        recipeBean.setFood_name("从心出发");
        recipeBean.getFood_name();
        recipeBean.setFood_price(" 活动地点：深圳");
        recipeBean.getFood_price();
        recipeBean.setFood_class("(1)活动名额的限制，所以如果名单内没您的名字，请您下次再报名参加\n" +
                "(2) 路途比较遥远，请大家在13：40前到达集中地点。请勿迟到！");
        recipeBean.getFood_class();
        recipeBean.setShop_id("sq1001");
        recipeBean.getShop_id();
        recipeDBAdapter.update(recipeBean, "竹筒饭");

        recipeList=recipeDBAdapter.queryAll(shop_id);

        recipeDBAdapter.closeDB();
    }

    public List<RecipeBean> getRecipeList(){
        return recipeList;
    }

    @Override
    public int getCount() {
        if (recipeList==null){
            return 0;
        }else {
            return recipeList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (recipeList==null){
            return null;
        }else {
            return recipeList.get(position);
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
            convertView=inflater.inflate(R.layout.gridview_recipe,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.imageView_item_gridview);
            holder.textView=(TextView)convertView.findViewById(R.id.textView_item_gridview);

            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        int img = context.getResources().getIdentifier(
                recipeList.get(position).getFood_img(), "drawable",
                context.getPackageName());
        holder.imageView.setImageResource(img);
        holder.textView.setText(recipeList.get(position).getFood_name());

        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

}
