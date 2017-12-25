package com.stockquote.adit.stockquote;

import com.viewpagerindicator.CirclePageIndicator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class GraphActivity extends FragmentActivity implements View.OnClickListener
{
 
    private ViewPager viewpager;
    private CustomFragmentAdapter customfragmentadapter;
    private TabHost tabhost;

	private CirclePageIndicator indicate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int fullscreenheight = metrics.heightPixels;
        LinearLayout layout=(LinearLayout)this.findViewById(R.id.mainlayout);
        Button newsBtn=(Button)this.findViewById(R.id.newsBtn);
        newsBtn.setOnClickListener(this);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height =fullscreenheight/3;
        layout.requestLayout();
        indicate=(CirclePageIndicator)findViewById(R.id.indicator);
        viewpager = (ViewPager)findViewById(R.id.pager);
        viewpager.setOffscreenPageLimit(1);
        tabhost = (TabHost)findViewById(android.R.id.tabhost);
        tabhost.setup();

        Intent i1=getIntent();
        
        Toast.makeText(this.getApplicationContext(),i1.getStringExtra("Symbol"),Toast.LENGTH_LONG).show();
        customfragmentadapter= new CustomFragmentAdapter(this,tabhost,viewpager,i1.getStringExtra("Symbol"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("1min").setIndicator("1m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("15min").setIndicator("15m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("30min").setIndicator("30m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("60min").setIndicator("60m"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("D").setIndicator("D"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("W").setIndicator("W"));
        customfragmentadapter.addTab(this.tabhost.newTabSpec("M").setIndicator("M"));
        indicate=(CirclePageIndicator)findViewById(R.id.indicator);
        viewpager = (ViewPager)findViewById(R.id.pager);
        viewpager.setOffscreenPageLimit(1);
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
        	tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.DKGRAY);
        	((TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title)).setTextColor(Color.WHITE);
        }
        indicate.setViewPager(viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public CustomFragmentAdapter getCustomfragmentadapter() 
    {
		return customfragmentadapter;
	}

    @Override
    public void onClick(View view) {
        String symbol=this.getIntent().getExtras().getString("Symbol");
        Intent i=new Intent(this,NewsActivity.class);
        i.putExtra("Symbol",symbol);
        Toast.makeText(this.getApplicationContext(),(String)symbol,Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}
