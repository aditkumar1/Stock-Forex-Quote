package com.stockquote.adit.stockquote;

import java.util.Calendar;

public class StockData
{
	private String symbol;
	private String open;
	private String high;
	private String low;
	private String close;
	private String adj_clos;
	private String volume;
	private String div_amt;
	private String split_cof;
	private String last_upd;
	private String tm_zn;
	private String API_prov;

	public StockData(String symbol, String open, String high, String low, String close, String adj_clos, String volume, String div_amt, String split_cof, String last_upd, String tm_zn, String API_prov) {
		this.symbol = symbol;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adj_clos = adj_clos;
		this.volume = volume;
		this.div_amt = div_amt;
		this.split_cof = split_cof;
		this.last_upd = last_upd;
		this.tm_zn = tm_zn;
		this.API_prov = API_prov;
	}

	public String getLast_upd() {
		return last_upd;
	}

	public void setLast_upd(String last_upd) {
		this.last_upd = last_upd;
	}

	public String getTm_zn() {
		return tm_zn;
	}

	public void setTm_zn(String tm_zn) {
		this.tm_zn = tm_zn;
	}

	public String getAPI_prov() {
		return API_prov;
	}

	public void setAPI_prov(String API_prov) {
		this.API_prov = API_prov;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	public String getAdj_clos() {
		return adj_clos;
	}

	public void setAdj_clos(String adj_clos) {
		this.adj_clos = adj_clos;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getDiv_amt() {
		return div_amt;
	}

	public void setDiv_amt(String div_amt) {
		this.div_amt = div_amt;
	}

	public String getSplit_cof() {
		return split_cof;
	}

	public void setSplit_cof(String split_cof) {
		this.split_cof = split_cof;
	}
}
	
