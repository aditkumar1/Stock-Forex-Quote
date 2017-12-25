package com.stockquote.adit.stockquote;

public class ListStockData 
{
	String open,high,low,close,volume,symbol;
	public ListStockData(String open, String high, String low, String close,String volume,String symbol)
	{
		super();
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume=volume;
		this.symbol=symbol;
	}
	public void setListStockData(ListStockData lsd)
	{
		this.open = lsd.getOpen();
		this.high = lsd.getHigh();
		this.low = lsd.getLow();
		this.close = lsd.getClose();
		this.volume=lsd.getVolume();
		this.symbol=lsd.getSymbol();
	}


	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
