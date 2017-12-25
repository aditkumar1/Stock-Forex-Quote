package com.stockquote.adit.stockquote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

public class LineGraphHandler
{
	
	public GraphicalView getView(Context context,GraphStockData str)
	{
		ArrayList<Date> dates=str.getDates();
		ArrayList<Double> closePrice=str.getClose();
		Calendar calendar=Calendar.getInstance();
		TimeSeries series=new TimeSeries("Price");
		for(int i=0;i<dates.size();i++)
		{
			series.add(i+1,closePrice.get(i));
		}
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		dataset.addSeries(series);

		XYSeriesRenderer renderer=new XYSeriesRenderer();
		renderer.setColor(Color.YELLOW);
		renderer.setLineWidth(2);
		XYMultipleSeriesRenderer renderseries=new XYMultipleSeriesRenderer();
		
		renderseries.setXLabels(0);
		if(str.getRange().equals("1min")||str.getRange().equals("15min")||str.getRange().equals("30min")||str.getRange().equals("60min"))
		{
			for(int i=0;i<dates.size();i+=(dates.size()/5))
			{
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
				calendar.setTime(dates.get(i));
				renderseries.addXTextLabel(i+1,calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
			}
		}
		if(str.getRange().equals("D")||str.getRange().equals("W"))
		{
			for(int i=0;i<dates.size();i+=(dates.size()/5))
			{
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
				calendar.setTime(dates.get(i));
				renderseries.addXTextLabel(i+1,calendar.get(Calendar.DAY_OF_MONTH)+" "+this.getMonthName(""+calendar.get(Calendar.MONTH)));
			}
		}
				
		if(str.getRange().equals("M"))
		{
			for(int i=0;i<dates.size();i+=(dates.size()/4))
			{
				calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
				calendar.setTime(dates.get(i));
				renderseries.addXTextLabel(i+1,calendar.get(Calendar.MONTH)+"/"+this.getMonthName(""+calendar.get(Calendar.YEAR)));
			}
		}
		
		renderseries.setChartTitle(str.getSymbol());
		renderseries.setYLabels(10);
		renderseries.setYLabelsAlign(Align.RIGHT);
		renderseries.setApplyBackgroundColor(true);
		renderseries.setBackgroundColor(Color.parseColor("#323232"));
		renderseries.setMarginsColor(Color.parseColor("#323232"));
		renderseries.setYLabelsAngle(330);
		//renderseries.setXTitle(xTitle);
		//renderseries.setYTitle(yTitle);
		renderseries.setGridColor(Color.WHITE);
		renderseries.setShowCustomTextGrid(true);
		renderseries.setShowGrid(true);
		renderseries.addSeriesRenderer(renderer);
		//renderseries.setZoomButtonsVisible(true);
		//renderseries.setPanEnabled(false);
		//renderseries.setZoomEnabled(false);
		if(dates.size()>0&&closePrice.size()>0)
		{
			double[] panLimits={0,dates.size(),0,closePrice.get(0)+closePrice.get(closePrice.size()-1)};
			renderseries.setPanLimits(panLimits);
		}
		return ChartFactory.getLineChartView(context, dataset, renderseries);
	}
	public String getMonthName(String month)
	{
		int monthvalue=Integer.parseInt(month);
		switch(monthvalue)
		{
			case 1:
				return "Jan";
			case 2:
				return "Feb";
			case 3:
				return "Mar";
			case 4:
				return "Apr";
			case 5:
				return "May";
			case 6:
				return "Jun";
			case 7:
				return "Jul";
			case 8:
				return "Aug";
			case 9:
				return "Sep";
			case 10:
				return "Oct";
			case 11:
				return "Nov";
			case 12:
				return "Dec";
				
		}
		return "mm";
	}

}
