package com.stockquote.adit.stockquote;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class StockFetcher extends AsyncTask<Void,Void,ArrayList<StockData>>
{
	MainLayout layout;
	static HashMap<String,Long> lastModified=new HashMap<String,Long>();
	public StockFetcher(MainLayout layout)
	{
		super();
		this.layout = layout;
	}
	@Override
	protected ArrayList<StockData> doInBackground(Void... voids)
	{
		List<String> symbs=layout.getSymbols();
		String stockInfo[]=new String[9];
		ArrayList<StockData> stockData=new ArrayList<StockData>();
		for(String symbol:symbs)
		{
            if(!lastModified.containsKey(symbol)){
			    lastModified.put(symbol,System.currentTimeMillis()-60000);
            }
            if((System.currentTimeMillis()-lastModified.get(symbol))>=60000)
			{
                Log.d("Stock modifying", "Stock modifying");
			    symbol=symbol.toUpperCase(Locale.getDefault());
                try
                {
                    // Retrieve CSV File
                    URL dateSourceURL = new URL(CommonUtils.stockFetchUrl+ symbol);
                    URLConnection connection = dateSourceURL.openConnection();
                    InputStreamReader is = new InputStreamReader(connection.getInputStream());
                    BufferedReader br = new BufferedReader(is);
                    // Parse CSV Into Array
                    String line = br.readLine();
                    line=br.readLine();
                    if(line!=null)
                    {
                        try
                        {
                            stockInfo = line.split(",");
                            stockData.add(new StockData(symbol,stockInfo[1],stockInfo[2],stockInfo[3],stockInfo[4],stockInfo[5],stockInfo[6],stockInfo[7],stockInfo[8],stockInfo[0],"US/Eastern","Alpha Vantage"));
                        }
                        catch(Exception ex)
                        {
                            Log.d("unexpected data",line);
                            stockData.add(new StockData(symbol,"N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","Alpha Vantage"));
                        }
                    }
                    // Handle Our Data
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                lastModified.put(symbol,System.currentTimeMillis());
            }
        }
		return stockData;
	}
	@Override
	protected void onPostExecute(ArrayList<StockData> result)
	{
		super.onPostExecute(result);
		if(result!=null&&result.size()!=0)
		{
			layout.getStockDatas().clear();
			layout.getStockDatas().addAll(result);
			layout.refresh();
		}
        new StockFetcher(layout).execute();
	}
}
