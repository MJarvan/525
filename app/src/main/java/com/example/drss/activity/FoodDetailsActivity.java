package com.example.drss.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drss.R;
import com.example.drss.dbadapter.LikeDBAdapter;
import com.example.drss.bean.LikeBean;

public class FoodDetailsActivity extends Activity {

    Context context;
    TextView textViewFood,textViewPrice,textViewClass;
    ImageView imageViewFood;
    LikeDBAdapter likeDBAdapter;
    String shop_tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        context=FoodDetailsActivity.this;

        likeDBAdapter=new LikeDBAdapter(context);
        likeDBAdapter.openDB();

        textViewFood=(TextView)findViewById(R.id.tv_details_name);
        textViewPrice=(TextView)findViewById(R.id.tv_details_price);
        textViewClass=(TextView)findViewById(R.id.tv_details_class);
        //imageViewFood=(ImageView)findViewById(R.id.image_food_details);

        Intent intent = getIntent();
        textViewFood.setText(intent.getStringExtra("f_name"));
        textViewPrice.setText(intent.getStringExtra("f_price")+" 元");
        textViewClass.setText(intent.getStringExtra("f_class"));

        /*String food_img=intent.getStringExtra("f_img");
        int img = context.getResources().getIdentifier(food_img, "drawable",
                context.getPackageName());
        imageViewFood.setImageResource(img);*/

        final String food_id=intent.getStringExtra("f_id");
        shop_tel=intent.getStringExtra("tel");

        findViewById(R.id.ibtn_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeBean likeBean=new LikeBean();
                likeBean.food_id=food_id;
                likeDBAdapter.insert(likeBean);
                Toast.makeText(FoodDetailsActivity.this, "领取成功", Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.ibtn_tel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shop_tel));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_details, menu);
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
