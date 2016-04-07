package com.example.drss.dbadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.drss.bean.LikeBean;
import com.example.drss.bean.RecipeBean;

import java.util.ArrayList;
import java.util.List;

public class LikeDBAdapter {

    final static String TABLE_LIKE="like";
    final static String LIKE_ID="like_id";
    final static String FOOD_ID="food_id";

    Context context;

    SQLiteDatabase database;
    DBUtils dbUtils;

    public LikeDBAdapter(Context context){
        this.context=context;
    }

    public void openDB(){
        dbUtils=new DBUtils(context);
        database=dbUtils.getDB();
    }

    public void closeDB(){
        dbUtils.closeDB();
    }

    public List<LikeBean> queryAll(){
        Cursor cursor=database.query(TABLE_LIKE,null,null,null,null,null,null);
        return convertToLike(cursor);
    }

    public long insert(LikeBean likeBean){
        ContentValues values=new ContentValues();
//        values.put(LIKE_ID, likeBean.getLike_id());
        values.put(FOOD_ID, likeBean.getFood_id());
        return database.insert(TABLE_LIKE, null, values);
    }

    public int deleteByFoodId(String name){
        String[] args={name};
        return database.delete(TABLE_LIKE, FOOD_ID + "=?", args);
    }

    private List<LikeBean> convertToLike(Cursor cursor){
        List<LikeBean> likes;
        if (cursor.getCount()==0){
            return null;
        }
        likes=new ArrayList<LikeBean>();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            LikeBean likeBean=new LikeBean();
            likeBean.setLike_id(cursor.getString(cursor.getColumnIndex(LIKE_ID)));
            likeBean.setFood_id(cursor.getString(cursor.getColumnIndex(FOOD_ID)));
            likes.add(likeBean);
            cursor.moveToNext();
        }
        return likes;
    }
}
