package com.example.drss.dbadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.drss.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ShopDBAdapter {

    final static String TABLE="shop";
    final static String SHOP_NAME="shop_name";
    final static String SHOP_ID="shop_id";
    final static String SHOP_REGION="shop_region";
    final static String SHOP_ADDR="shop_address";
    final static String SHOP_TEL="shop_tel";

    Context context;

    SQLiteDatabase database;
    DBUtils dbUtils;

    public ShopDBAdapter(Context context){
        this.context=context;
    }

    public void openDB(){
        dbUtils=new DBUtils(context);
        database=dbUtils.getDB();
    }

    public void closeDB(){
        dbUtils.closeDB();
    }

    public List<ShopBean> queryAll(){
        Cursor cursor=database.query(TABLE,null,null,null,null,null,null);
        return convertToShop(cursor);
    }
//=========
    public List<ShopBean> queryByShopid(String shop_id){
        Cursor cursor=database.query(TABLE, null, SHOP_ID+"=?",new String[]{shop_id}, null, null, null, null);
        return convertToShop(cursor);
    }

    public List<ShopBean> queryByName(String name){
        Cursor cursor=database.query(TABLE, null,  SHOP_NAME  +  " like '%" + name + "%'", null, null, null, null);
        return convertToShop(cursor);
    }

//    public List<ShopBean> queryByShopid(String shop_id){
////        int shopid=Integer.parseInt(id);
//        Cursor cursor=database.query(TABLE, null, SHOP_ID+"=?",new String[]{shop_id}, null, null, null, null);
//        //List<Shopbean>
//        return convertToShop(cursor);
//    }

    public List<ShopBean> query(String selection,String selectionArgs){
        Cursor cursor=database.query(TABLE,null,selection+"=?",new String[]{selectionArgs},null,null,null);
        return convertToShop(cursor);
    }
    public long insert(ShopBean shopBean){
        ContentValues values=new ContentValues();
        values.put(SHOP_ID, shopBean.getShop_id());
        values.put(SHOP_NAME, shopBean.getShop_name());
        values.put(SHOP_REGION, shopBean.getShop_region());
        values.put(SHOP_ADDR, shopBean.getShop_address());
        values.put(SHOP_TEL, shopBean.getShop_tel());
        return database.insert(TABLE, null, values);
    }

    public int update(ShopBean shopBean,String selectionArgs){
        ContentValues values=new ContentValues();
        values.put(SHOP_ID, shopBean.getShop_id());
        values.put(SHOP_NAME, shopBean.getShop_name());
        values.put(SHOP_REGION, shopBean.getShop_region());
        values.put(SHOP_ADDR, shopBean.getShop_address());
        values.put(SHOP_TEL, shopBean.getShop_tel());
        return database.update(TABLE, values, SHOP_NAME+"=?", new String[]{selectionArgs});
    }

    public int delete_byName(ShopBean shopBean,String selectionArgs){
        return database.delete(TABLE, SHOP_NAME + "=?", new String[]{selectionArgs});
    }

    public int delete_All(){
        return database.delete(TABLE, null, null);
    }

    private List<ShopBean> convertToShop(Cursor cursor){
        List<ShopBean> shops;
        if (cursor.getCount()==0){
            return null;
        }
        shops=new ArrayList<ShopBean>();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            ShopBean shopBean=new ShopBean();
            shopBean.setShop_id(cursor.getString(cursor.getColumnIndex(SHOP_ID)));
            shopBean.setShop_name(cursor.getString(cursor.getColumnIndex(SHOP_NAME)));
            shopBean.setShop_region(cursor.getString(cursor.getColumnIndex(SHOP_REGION)));
            shopBean.setShop_address(cursor.getString(cursor.getColumnIndex(SHOP_ADDR)));
            //shopBean.setShop_tel(cursor.getString(cursor.getColumnIndex(SHOP_TEL)));
            shops.add(shopBean);
            cursor.moveToNext();
        }
        return shops;
    }
}
