package com.stockquote.adit.stockquote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class ListStockFetcher extends AsyncTask<Void,Void,ArrayList<ListStockData>>
{
	MainLayout layout;
	static HashMap<String,Long> lastModified=new HashMap<String,Long>();
	public ListStockFetcher(MainLayout layout)
	{
		super();
		this.layout = layout;
	}
	@Override
	protected ArrayList<ListStockData> doInBackground(Void... voids)
	{
		List<String> symbs=layout.getSymbols();
		String stockInfo[]=new String[6];
		ArrayList<ListStockData> stockData=new ArrayList<ListStockData>();
		for(String symbol:symbs) {
            Log.d("modifying", "modifying");
            symbol = symbol.toUpperCase(Locale.getDefault());
            try {
                // Retrieve CSV File
                URL dateSourceURL = new URL(CommonUtils.listStockFetchUrl + symbol);
                URLConnection connection = dateSourceURL.openConnection();
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                // Parse CSV Into Array
                String line = br.readLine();
                line = br.readLine();
                if (line != null) {
                    try {
                        stockInfo = line.split(",");
                        stockData.add(new ListStockData(stockInfo[1], stockInfo[2], stockInfo[3], stockInfo[4], stockInfo[5], symbol));
                    } catch (Exception ex) {
                        Log.d("unexpected data", line);
                        stockData.add(new ListStockData("N/A", "N/A", "N/A", "N/A", "N/A", symbol));
                    }
                }
                // Handle Our Data
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return stockData;
	}
	@Override
	protected void onPostExecute(ArrayList<ListStockData> result)
	{
		super.onPostExecute(result);
		if(result!=null&&result.size()!=0)
		{
			updateLayout(layout.getStockDatas(),result);
			layout.refresh();
		}
        new ListStockFetcher(layout).execute();
	}
	private void updateLayout(List<Object> layoutList, ArrayList<ListStockData> result){
        for (int i=0;i<layoutList.size();i++) {
            String symbol=((ListStockData)layoutList.get(i)).getSymbol();
            for(ListStockData lsd:result){
                if(symbol.equals(lsd.getSymbol()))     {
                    ((ListStockData) layoutList.get(i)).setListStockData(lsd);
                }
            }
        }
    }
}
