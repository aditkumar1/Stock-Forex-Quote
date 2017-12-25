package com.stockquote.adit.stockquote;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class NewsActivity extends Activity implements OnScrollListener,AdapterView.OnItemClickListener
{
	private int visibleThreshold = 10;
    private int currentPage = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem=0,visibleItemCount=0,totalItemCount=0;
	ListView newsView;
	NewsAdapter na;
	ArrayList<NewsEntry> listData;
	View loadMoreView;
	String symbol;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_news);
		newsView=(ListView)findViewById(R.id.listView1);
        listData=new ArrayList<NewsEntry>();
		na=new NewsAdapter(this,listData);
		 loadMoreView = ((LayoutInflater)this
				    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				    .inflate(R.layout.loadmore, null, false);
		newsView.setAdapter(na);
		symbol=this.getIntent().getExtras().getString("Symbol");
		new NewsFetcher(symbol,na,Integer.valueOf(currentPage)).execute();
		newsView.setOnScrollListener(this);
		newsView.setOnItemClickListener(this);
		Toast.makeText(getApplicationContext(), "Fetching Data - "+symbol, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
		{
			if (loading) 
	        {
	            if (totalItemCount > previousTotal)
	            {
	                loading = false;
	                previousTotal = totalItemCount;
	                currentPage++;
	            }
	        }
	        if (!loading&&(totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))
	        {
	            // I load the next page of gigs using a background task,
	            // but you can call any function here.
	        	Toast toast= Toast.makeText(getApplicationContext(), "Fetching More Data", Toast.LENGTH_LONG);
	        	toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
	        	toast.show();
	            new NewsFetcher(symbol,na,currentPage).execute();
	            loading = true;
	        }

		}
		
	}
	@Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount)
    {
		this.firstVisibleItem=firstVisibleItem;
		this.visibleItemCount=visibleItemCount;
		this.totalItemCount=totalItemCount;
     }

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		if(na!=null){
			String url =((NewsEntry) na.getItem(i)).getLink();
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			Toast.makeText(this,"Opening URL",Toast.LENGTH_SHORT).show();
			startActivity(intent);
		}
	}
}