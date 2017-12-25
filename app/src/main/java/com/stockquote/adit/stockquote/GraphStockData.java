package com.stockquote.adit.stockquote;

import java.util.ArrayList;
import java.util.Date;

public class GraphStockData 
{
	ArrayList<Date> dates;
	ArrayList<Double> volume;
	ArrayList<Double> close;
	String symbol;
	String range;

	
	public GraphStockData(ArrayList<Date> dates, ArrayList<Double> close, ArrayList<Double> volume, String symbol, String range)
	{
		this.dates=dates;
		this.close=close;
		this.volume=volume;
		this.symbol=symbol;
		this.range=range;
	}
	public ArrayList<Date> getDates()
	{
		return dates;
	}
	public ArrayList<Double> getClose()
	{
		return close;
	}
	public ArrayList<Double> getVolume()
	{
		return volume;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public String getRange()
	{
		return range;
	}
	
}
