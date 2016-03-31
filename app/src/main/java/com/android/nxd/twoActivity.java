package com.android.nxd;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.TabHost;

/**
 * Created by admin on 2016/3/24.
 */
public class twoActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);

       /** String html="<font>图片一</font><img src=‘image1'>";
        Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable=getResources().getDrawable()
                return null;
            }
        },null);*/

        TabHost m = (TabHost)findViewById(R.id.tabhost);
        m.setup();

        LayoutInflater i=LayoutInflater.from(this);
        i.inflate(R.layout.tab1, m.getTabContentView());
        i.inflate(R.layout.tab2, m.getTabContentView());//动态载入XML，而不需要Activity
        i.inflate(R.layout.tab3, m.getTabContentView());

        m.addTab(m.newTabSpec("tab1").setIndicator("活动发布").setContent(R.id.LinearLayout01));
        m.addTab(m.newTabSpec("tab2").setIndicator("活动领取").setContent(R.id.LinearLayout02));
        m.addTab(m.newTabSpec("tab3").setIndicator("社区交流").setContent(R.id.LinearLayout03));
    }
}
