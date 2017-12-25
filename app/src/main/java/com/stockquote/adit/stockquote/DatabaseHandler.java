package com.stockquote.adit.stockquote;

import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	int id=0;
	public static final int DATABASE_VERSION=1;
	public static final String DATABASE_NAME="ListStockData";
	public static final String TABLE_NAME1="RowHeaders";
	public static final String TABLE_NAME2="ColumnHeaders";
	public static final String TABLE_NAME3="Main";
	public static final String KEY_ID1="rhid";
	public static final String KEY_ID2="chid";
	public static final String KEY_SYMBOL="RowSymbols";
	public static final String KEY_NAME="ColumnNames";
	public static final String KEY_ID="ID";
	public String CREATE_TABLE_ROWHEADERS="CREATE TABLE IF NOT EXISTS "+TABLE_NAME1+"("+KEY_ID1+" INTEGER PRIMARY KEY,"+KEY_SYMBOL+" TEXT )";
	public String CREATE_TABLE_COLUMNHEADERS="CREATE TABLE IF NOT EXISTS "+TABLE_NAME2+"("+KEY_ID2+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT )";
	public String CREATE_TABLE_MAIN="CREATE TABLE IF NOT EXISTS "+TABLE_NAME3+"("+KEY_ID+" TEXT )";
	SQLiteDatabase db;
	Context context;

	public DatabaseHandler(Context context) 
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		this.context=context;
		id=0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ROWHEADERS);
		db.execSQL(CREATE_TABLE_COLUMNHEADERS);
		db.execSQL(CREATE_TABLE_MAIN);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
		onCreate(db);
	}
	
	public void openRead()
	{
		db=this.getReadableDatabase();
	}

	public void openWrite()
	{
		db=this.getWritableDatabase();
	}

	
	public void addSymbol(String symbol)
	{
		this.openWrite();
		ContentValues cv=new ContentValues();
		//cv.put(KEY_ID,(++id));
		cv.put(KEY_SYMBOL,symbol.toUpperCase(Locale.getDefault()));
		db.insert(TABLE_NAME1, null,cv);
		new POSTHttpFetcher(context,symbol,CommonUtils.uniqueID).execute();
		db.close();
	}
	public void addID(String ID)
	{
		this.openWrite();
		ContentValues cv=new ContentValues();
		//cv.put(KEY_ID,(++id));
		cv.put(KEY_ID,ID);
		db.insert(TABLE_NAME3, null,cv);
		db.close();
	}
	public void addColumn(String name)
	{
		this.openWrite();
		ContentValues cv=new ContentValues();
		//cv.put(KEY_ID,(++id));
		cv.put(KEY_NAME,name.toUpperCase(Locale.getDefault()));
		db.insert(TABLE_NAME2, null,cv);
		db.close();
	}
	public ArrayList<String> readAllSymbols()
	{
		ArrayList<String> li=new ArrayList<String>();
		
		this.openRead();
		Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME1,null);
		if(c.moveToFirst())
		{
			while(true)
			{
				li.add(c.getString(1));
				if(!c.moveToNext())
					break;
			}	
		}
		c.close();
		db.close();
		return li;
	}
	public ArrayList<String> readAllColumns()
	{
		ArrayList<String> li=new ArrayList<String>();
		
		this.openRead();
		Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME2,null);
		if(c.moveToFirst())
		{
			while(true)
			{
				li.add(c.getString(1));
				if(!c.moveToNext())
					break;
			}	
		}
		c.close();
		db.close();
		return li;
	}
	public String getID()
	{
		this.openWrite();
		db.execSQL(CREATE_TABLE_MAIN);
		String ID="";
		this.openRead();
		Cursor c=db.rawQuery("SELECT ID FROM "+TABLE_NAME3,null);
		if(c.moveToFirst())
		{
			while(true)
			{
				ID=c.getString(0);
				break;
			}
		}
		c.close();
		db.close();
		return ID;
	}

	public void deleteSymbol(String symbol)
	{
		this.openWrite();
		int i=db.delete(TABLE_NAME1, KEY_SYMBOL+"=?",new String[]{symbol});
		new PUTHttpFetcher(context,CommonUtils.uniqueID,symbol).execute();
		db.close();
	}
	public void deleteColumn(String name)
	{
		this.openWrite();
		int i=db.delete(TABLE_NAME2, KEY_NAME+"=?",new String[]{name});
		db.close();
	}
	public void deleteAllSymbols()
	{
		this.openWrite();
		db.execSQL("delete from "+ TABLE_NAME1);
		db.close();
	}
	public void deleteAllColumns()
	{
		this.openWrite();
		db.execSQL("delete from "+ TABLE_NAME2);
		db.close();
	}
}
