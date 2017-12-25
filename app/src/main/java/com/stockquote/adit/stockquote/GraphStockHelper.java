package com.stockquote.adit.stockquote;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

public class GraphStockHelper
{
	public GraphStockHelper()
	{
	}
	public Double handleDouble(String x) throws NumberFormatException
	{
		try
		{
			return Double.parseDouble(x);
		}
		catch(NumberFormatException ex)
		{
			throw ex;
		}
	}
	public Long handleLong2(String x) throws NumberFormatException
	{
		try
		{
			return Long.parseLong(x);
		}
		catch(NumberFormatException ex)
		{
			throw ex;
		}
	}
	public long handleLong(String x)
	{
		try
		{
			return Long.parseLong(x);
		}
		catch(NumberFormatException ex)
		{
			return 0;
		}
	}
	public double handleDouble2(String x) throws NumberFormatException
	{
		try
		{
			return (Double.parseDouble(x))/1000000;
		}
		catch(NumberFormatException ex)
		{
			throw ex;
		}
	}
	public Float handleFloat(String x) throws NumberFormatException
	{

		try
		{
			return Float.parseFloat(x);
		}
		catch(NumberFormatException ex)
		{
			throw ex;
		}
	}
	public Date handleDate(String x) throws ParseException
	{
		try
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
			Date date = sdfDate.parse(x);
			return date;
		}
		catch(ParseException ex)
		{
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
			Date date = sdfDate.parse(x);
			return date;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
}
