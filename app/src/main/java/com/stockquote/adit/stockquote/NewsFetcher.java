package com.stockquote.adit.stockquote;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

public class NewsFetcher extends AsyncTask<Void,Void,ArrayList<NewsEntry>>
{
	NewsAdapter na;
	String sym;
	int page;
	boolean errorIO=false,errorURL=false,errorParse=false,errorDateParse=false;
	public NewsFetcher(String symbol,NewsAdapter na,int page){
		this.na=na;
		this.page=page;
		this.sym=symbol;
	}
	@Override
	protected ArrayList<NewsEntry> doInBackground(Void...params)
	{
		sym = sym.toUpperCase(Locale.getDefault());
		InputStream is=null;
		try
		{
			URL url=new URL("https://feeds.finance.yahoo.com/rss/2.0/headline?s="+sym+"&region=US&lang=en-US");
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(10000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.connect();
			int response=connection.getResponseCode();
			is=connection.getInputStream();		
			return new XMLParser().parse(is,page);
		}
		catch(MalformedURLException ex)
		{
			this.errorURL=true;
			ex.printStackTrace();
		}
		catch(IOException e)
		{
			this.errorIO=true;
			e.printStackTrace();
		} catch (XmlPullParserException e)
		{
			this.errorParse=true;
			e.printStackTrace();
		} catch (ParseException e)
		{
			this.errorDateParse=true;
			e.printStackTrace();
		}
		return null;
	}


	@Override
	protected void onPostExecute(ArrayList<NewsEntry> result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null)
		{
			na.getEntries().clear();
			na.getEntries().addAll(result);
			na.notifyDataSetChanged();
		}
		else{
			new NewsFetcher(sym,na,page).execute();
		}
		if(this.errorDateParse||this.errorParse)
		{
			
		}
		if(this.errorIO)
		{
			
		}
		if(this.errorURL)
		{
			
		}
	}
	
}
