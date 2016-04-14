package com.example.drss.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drss.R;
import com.example.drss.bean.LikeBean;
import com.example.drss.bean.RecipeBean;
import com.example.drss.bean.ShopBean;
import com.example.drss.dbadapter.LikeDBAdapter;
import com.example.drss.dbadapter.RecipeDBAdapter;
import com.example.drss.dbadapter.ShopDBAdapter;

import java.util.List;

public class LikeAdapter extends BaseAdapter{
    Context context;
    List<LikeBean> likeList;
    List<ShopBean> shopList;
    List<RecipeBean> recipeList;
    public LikeAdapter(Context context){
        this.context=context;
        setLikeList();
    }

    private void setLikeList(){
        LikeDBAdapter likeDBAdapter = new LikeDBAdapter(context);
        likeDBAdapter.openDB();
        likeList=likeDBAdapter.queryAll();
        likeDBAdapter.closeDB();
    }

    public List<LikeBean> getLikeList(){
        return likeList;
    }

//==================================
    private void setRecipeList(String food_id){
        RecipeDBAdapter recipeDBAdapter = new RecipeDBAdapter(context);
        recipeDBAdapter.openDB();
        recipeList=recipeDBAdapter.queryByFoodId(food_id);
        recipeDBAdapter.closeDB();
    }

    public List<RecipeBean> getRecipeList(){
        return recipeList;
    }

//====================================

    private void setShopList(String shop_id){
        ShopDBAdapter shopDBAdapter = new ShopDBAdapter(context);
        shopDBAdapter.openDB();
        shopList=shopDBAdapter.queryByShopid(shop_id);
        shopDBAdapter.closeDB();
    }

    public List<ShopBean> getShopList(){
        return shopList;
    }

//====================================

    public RecipeBean getRecipeBean(String dish_id){
        RecipeDBAdapter recipeDBAdapter=new RecipeDBAdapter(context);
        recipeDBAdapter.openDB();
        RecipeBean dish=recipeDBAdapter.queryByFoodId(dish_id).get(0);
        recipeDBAdapter.closeDB();
        return dish;
    }

    @Override
    public int getCount() {
        if (likeList==null){
            return 0;
        }else {
            return likeList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (likeList==null){
            return null;
        }else {
            return likeList.get(position);
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
            convertView=inflater.inflate(R.layout.listview_item_like,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.list_img);
            holder.FoodName=(TextView)convertView.findViewById(R.id.tv_like_foodname);
            holder.ShopName=(TextView)convertView.findViewById(R.id.tv_like_shopname);

            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        if (likeList != null){
            String food_id = getLikeList().get(position).getFood_id();
            Log.i("tag", "===>" + position);
            setRecipeList(food_id);
            for (int i = 0; i < getRecipeList().size(); i++) {
                String shopid=getRecipeList().get(i).getShop_id();
                String food_name=getRecipeList().get(i).getFood_name();
                int img = context.getResources().getIdentifier(
                        getRecipeList().get(i).getFood_img(), "drawable",
                        context.getPackageName());

                setShopList(shopid);
                for (int j = 0; j < getShopList().size(); j++) {
                    String shop_name=getShopList().get(j).getShop_name();
                    holder.ShopName.setText("类别："+shop_name);
                }
                holder.FoodName.setText(food_name);

                holder.imageView.setImageResource(img);
            }
        }
        return convertView;

    }
    class ViewHolder{
        ImageView imageView;
        TextView FoodName,ShopName;
    }

}
