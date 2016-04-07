package com.example.drss.fragment;

import com.example.drss.R;
import com.example.drss.activity.SearchActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    ImageButton imageButtonSearch;
    private ViewPager mViewPagerH;

    private LinearLayout viewIndicator;//指示器布局

    private int[] picsH = { R.drawable.f_lznrm, R.drawable.f_teqkr, R.drawable.f_ztf };
    final ArrayList<View> viewsH = new ArrayList<View>();

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();

        View view=inflater.inflate(R.layout.fragment_home, null);

        imageButtonSearch=(ImageButton)view.findViewById(R.id.ibtn_home_hot);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SearchActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.ibtn_home_guess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://v5.ele.me/?force"));
                startActivity(i);
            }
        });

        initHomeView(view);
        return view;
    }

    private void initHomeView(View view) {
        // TODO Auto-generated method stub
        mViewPagerH = (ViewPager) view.findViewById(R.id.viewpagerH);

        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        // 将要分页显示的View装入数组中
        for (int i = 0; i < picsH.length; i++) {
            ImageView iv = new ImageView(view.getContext());
            iv.setLayoutParams(mParams);
            iv.setImageResource(picsH[i]);
            viewsH.add(iv);
        }

        // 填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewsH.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(viewsH.get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {

                ((ViewPager) container).addView(viewsH.get(position));
                return viewsH.get(position);
            }
        };
        mViewPagerH.setAdapter(mPagerAdapter);
        mViewPagerH.setOnPageChangeListener(new ViewPageChangeListener());//设置监听

        //设置Elistview
        viewIndicator = (LinearLayout) view.findViewById(R.id.vpindicator);

        LinearLayout.LayoutParams ind_params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        );
        for(int i = 0; i < picsH.length; ++i) {
            ImageView iv = new ImageView(view.getContext());
            if(i == 0)
                iv.setBackgroundResource(R.drawable.oval);
            else
                iv.setBackgroundResource(R.drawable.oval_selected);
            iv.setLayoutParams(ind_params);
            viewIndicator.addView(iv);
        }

    }

    private class ViewPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        //监听页面改变事件来改变viewIndicator中的指示图片
        @Override
        public void onPageSelected(int arg0) {
            int len = viewIndicator.getChildCount();
            for(int i = 0; i < len; ++i)
                viewIndicator.getChildAt(i).setBackgroundResource(R.drawable.oval);
            viewIndicator.getChildAt(arg0).setBackgroundResource(R.drawable.oval_selected);
        }

    }
}
