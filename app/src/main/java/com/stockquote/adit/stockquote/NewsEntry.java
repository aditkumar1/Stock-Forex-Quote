package com.stockquote.adit.stockquote;

public class NewsEntry 
{
	public 	String title;
    public  String link;
    public  String pubDate;
	public NewsEntry(String title, String link, String pubDate)
	{
		super();
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
	}
	public NewsEntry()
	{
		
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public String getPubDate()
	{
		return pubDate;
	}
	public void setPubDate(String pubDate)
	{
		this.pubDate = pubDate;
	}
	
}
