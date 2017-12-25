package com.stockquote.adit.stockquote;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class GraphFetcher extends AsyncTask<String,Void,GraphStockData>
{  
	LinearLayout lv;
	RelativeLayout rv;
	GraphActivity context;
	
	public GraphFetcher(Activity context,LinearLayout lv,RelativeLayout rv)
	{
		this.context=(GraphActivity)context;
		this.lv=lv;
		this.rv=rv;
	}
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		lv.setVisibility(View.GONE);
		rv.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected GraphStockData doInBackground(String... params)
	{
		boolean dataReached=false;
		String companyName="";
		long offset=0;
		ArrayList<Date> dates=new ArrayList<Date>();
		ArrayList<Double> closePrice=new ArrayList<Double>();
		ArrayList<Double> volume=new ArrayList<Double>();
		GraphStockHelper sh=new GraphStockHelper();
		CustomFragmentAdapter adapter=context.getCustomfragmentadapter();
		String sym =adapter.getSymbol().toUpperCase(Locale.getDefault());
		String range=adapter.getRange();
		try
		{
			String timeFunction="TIME_SERIES_INTRADAY";
			String rng=range;
			if(range.contains("min")){
				timeFunction="TIME_SERIES_INTRADAY";
			}
			if(range.contains("D")){
				timeFunction="TIME_SERIES_DAILY";
				rng="";
			}
			if(range.contains("W")){
				timeFunction="TIME_SERIES_WEEKLY";
				rng="";
			}
			if(range.contains("M")){
				timeFunction="TIME_SERIES_MONTHLY";
				rng="";
			}
			URL sourceUrl = new URL("https://www.alphavantage.co/query?function="+timeFunction+"&interval="+rng+"&outputsize=full&apikey="+CommonUtils.APIkey+"&datatype=csv&symbol="+sym);
			URLConnection connection = sourceUrl.openConnection();
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);
			// Parse CSV Into ArrayList
			String buffer=br.readLine();
			buffer=br.readLine();
			while(buffer!=null)
			{
				try
				{
					String stockInfo[]=buffer.split(",");
					dates.add(sh.handleDate(stockInfo[0]));
					closePrice.add(sh.handleDouble(stockInfo[4]));
					volume.add(sh.handleDouble2(stockInfo[5]));
				}
				catch(NumberFormatException ex)
				{
					buffer=br.readLine();
					continue;
				}
				catch(ParseException ex)
				{
					ex.printStackTrace();
				}
				buffer=br.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		return new GraphStockData(dates,closePrice,volume,sym,range);
	}
	@Override
	protected void onPostExecute(GraphStockData result)
	{
		super.onPostExecute(result);
		if(result.getDates().size()>0)
		{
			if(((String)lv.getTag()).equals("LineGraph"))
			{
				lv.removeAllViews();
				lv.addView(new LineGraphHandler().getView(context,result));
			}
			if(((String)lv.getTag()).equals("BarGraph"))
			{
				lv.removeAllViews();
				lv.addView(new BarGraphHandler().getView(context,result));
			}
			rv.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}
	}
}
