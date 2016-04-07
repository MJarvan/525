package com.example.drss.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.drss.R;
import com.example.drss.activity.RecipeActivity;
import com.example.drss.dbadapter.DBUtils;
import com.example.drss.adapter.ShopAdapter;
import com.example.drss.bean.ShopBean;

public class FragmentShop extends Fragment {
    Context context;
    ShopAdapter shopAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();
        DBUtils dbUtils=new DBUtils(context);
        dbUtils.initDB();
        View view=inflater.inflate(R.layout.fragment_shop, null);

        ListView listView=(ListView)view.findViewById(R.id.List_view_shop);
        shopAdapter=new ShopAdapter(context);
        listView.setAdapter(shopAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShopBean shopBean= shopAdapter.getShopList().get(position);
//                String shop_id = shopAdapter.getShopList().get(position).getShop_id();
                Intent intent = new Intent(context,RecipeActivity.class);
                intent.putExtra("shop_id", shopBean.getShop_id());
                intent.putExtra("name",shopBean.getShop_name());
                intent.putExtra("tel", shopBean.getShop_tel());
                startActivity(intent);

            }
        });
        return view;
    }

}
