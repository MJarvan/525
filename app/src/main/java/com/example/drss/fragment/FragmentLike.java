package com.example.drss.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.example.drss.R;
import com.example.drss.activity.FoodDetailsActivity;
import com.example.drss.adapter.LikeAdapter;
import com.example.drss.adapter.ShopAdapter;
import com.example.drss.bean.RecipeBean;
import com.example.drss.bean.ShopBean;
import com.example.drss.dbadapter.DBUtils;
import com.example.drss.dbadapter.LikeDBAdapter;
import com.example.drss.dbadapter.ShopDBAdapter;

public class FragmentLike extends Fragment {
    Context context;
    LikeAdapter likeAdapter;
    final static int CONTEXTMENU1=1;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context=getActivity().getApplicationContext();
        DBUtils dbUtils=new DBUtils(context);
        dbUtils.initDB();
        View view=inflater.inflate(R.layout.fragment_like, null);

        listView=(ListView)view.findViewById(R.id.List_view_like);
        likeAdapter=new LikeAdapter(context);
        listView.setAdapter(likeAdapter);
        registerForContextMenu(listView);

        return view;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("菜单");
        menu.add(0, CONTEXTMENU1, 0, "取消");
        menu.add(0, CONTEXTMENU1 + 1, 1, "查看");
    }
    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
        final int pos=menuInfo.position;
        if (item.getItemId()==CONTEXTMENU1) {
            new AlertDialog.Builder(this.getActivity())
                    .setTitle("提示")
                    .setMessage("确定取消此次活动吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            LikeDBAdapter likeDBAdapter=new LikeDBAdapter(context);
                            likeDBAdapter.openDB();
                            likeDBAdapter.deleteByFoodId(likeAdapter.getLikeList().get(pos).getFood_id());
                            likeAdapter=new LikeAdapter(context);
                            listView.setAdapter(likeAdapter);

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                        }
                    }).show();
        }
        if (item.getItemId()==CONTEXTMENU1+1){
//            RecipeBean recipeBean= likeAdapter.getRecipeList().get(pos);
////            ShopBean shopBean= shopAdapter.getShopList().get(pos);
//            Intent intent = new Intent(context,FoodDetailsActivity.class);
//            intent.putExtra("f_name",recipeBean.getFood_name());
//            intent.putExtra("f_price", recipeBean.getFood_price());
//            intent.putExtra("f_class", recipeBean.getFood_class());
//            intent.putExtra("f_img", recipeBean.getFood_img());
//
////            intent.putExtra("tel", shopBean.getShop_tel());
//            startActivity(intent);

            String dish_id = likeAdapter.getRecipeList().get(pos).getFood_id();
            RecipeBean recipeBean = likeAdapter.getRecipeBean(dish_id);
            String shop_id = recipeBean.getShop_id();
            ShopDBAdapter shopDB = new ShopDBAdapter(context);
            shopDB.openDB();
            ShopBean shop = shopDB.queryByShopid(shop_id).get(0);
            shopDB.closeDB();

            Intent intent = new Intent(context, FoodDetailsActivity.class);

            intent.putExtra("tel",shop.getShop_tel());
            intent.putExtra("id",recipeBean.getFood_id());
            intent.putExtra("f_img",recipeBean.getFood_img());
            intent.putExtra("f_name",recipeBean.getFood_name());
            intent.putExtra("f_price",recipeBean.getFood_price());
            intent.putExtra("f_class",recipeBean.getFood_class());

            startActivity(intent);

        }
        return super.onContextItemSelected(item);

    }

}
