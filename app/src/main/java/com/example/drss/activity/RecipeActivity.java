package com.example.drss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.drss.R;
import com.example.drss.adapter.RecipeAdapter;
import com.example.drss.bean.RecipeBean;

public class RecipeActivity extends Activity {

    private GridView gridView;
    Context context;
    RecipeAdapter recipeAdapter;
    String shop_id,shop_tel,shop_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        context=RecipeActivity.this;
        TextView textViewShopName=(TextView)findViewById(R.id.tv_recipe_shopname);

        Intent intent = getIntent();//获取上一个intent
        shop_id =intent.getStringExtra("shop_id");
        shop_tel=intent.getStringExtra("tel");
        shop_name=intent.getStringExtra("name");
        textViewShopName.setText(intent.getStringExtra("name"));

        gridView=(GridView)findViewById(R.id.gridView);
        recipeAdapter=new RecipeAdapter(context,shop_id);
        gridView.setAdapter(recipeAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RecipeBean recipeBean = recipeAdapter.getRecipeList().get(position);
                Intent intent = new Intent(context, FoodDetailsActivity.class);
                intent.putExtra("f_id", recipeBean.getFood_id());
                intent.putExtra("f_img", recipeBean.getFood_img());
                intent.putExtra("f_name", recipeBean.getFood_name());
                intent.putExtra("f_price", recipeBean.getFood_price());
                intent.putExtra("f_class", recipeBean.getFood_class());

                intent.putExtra("tel",shop_tel);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
