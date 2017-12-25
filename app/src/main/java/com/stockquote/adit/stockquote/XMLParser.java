package com.stockquote.adit.stockquote;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XMLParser 
{
	 NewsEntry entry; 
	 private RSSXMLTag currentTag;

	    
	    public ArrayList<NewsEntry> parse(InputStream in,int page) throws XmlPullParserException, ParseException, IOException 	   
	    {
	    	ArrayList<NewsEntry> postDataList = new ArrayList<NewsEntry>();
		 
    		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(in, null);

			int eventType = xpp.getEventType();
			NewsEntry entry = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, DD MMM yyyy HH:mm:ss");
		
			while (eventType != XmlPullParser.END_DOCUMENT) 
			{
				if (eventType == XmlPullParser.START_DOCUMENT)
				{

				}
				else if (eventType == XmlPullParser.START_TAG) 
				{
					if (xpp.getName().equals("item"))
					{
						entry = new NewsEntry();
						currentTag = RSSXMLTag.IGNORETAG;
					}
					else if (xpp.getName().equals("title"))
					{
						currentTag = RSSXMLTag.TITLE;
					}
					else if (xpp.getName().equals("link"))
					{
						currentTag = RSSXMLTag.LINK;
					} 
					else if (xpp.getName().equals("pubDate"))
					{
						currentTag = RSSXMLTag.DATE;
					}
				}
				else if (eventType == XmlPullParser.END_TAG) 
				{
					if (xpp.getName().equals("item")) 
					{
						// format the data here, otherwise format data in
						// Adapter
						Date postDate = dateFormat.parse(entry.getPubDate());
						entry.setPubDate(dateFormat.format(postDate));
						postDataList.add(entry);
						if(postDataList.size()>=(page*10))
							break;
					} else 
					{
						currentTag = RSSXMLTag.IGNORETAG;
					}
				}
				else if (eventType == XmlPullParser.TEXT)
				{
					String content = xpp.getText();
					content = content.trim();
					Log.d("debug", content);
					if (entry != null)
					{
						switch (currentTag) 
						{
						case TITLE:
							if (content.length() != 0) 
							{
								if (entry.getTitle() != null) 
								{
									entry.setTitle(entry.getTitle()+content);
								} 
								else
								{
									entry.setTitle(content);
								}
							}
							break;
						case LINK:
							if (content.length() != 0) 
							{
								if (entry.getLink() != null) 
								{
									entry.setLink(entry.getLink()+content);
								} 
								else
								{
									entry.setLink(content);
								}
							}
							break;
						case DATE:
							if (content.length() != 0) 
							{
								if (entry.getPubDate() != null) 
								{
									entry.setPubDate(entry.getPubDate()+content);
								} 
								else
								{
									entry.setPubDate(content);
								}
							}
							break;
						default:
							break;
						}
					}
				}

				eventType = xpp.next();
			}
			 
		return postDataList;
	}

}
	    
	 