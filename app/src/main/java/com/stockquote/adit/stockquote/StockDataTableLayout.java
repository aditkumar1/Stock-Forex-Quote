package com.stockquote.adit.stockquote;
 
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 










import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

 
public class StockDataTableLayout extends LinearLayout implements MainLayout
{
 
    public final String TAG = "TableMainLayout.java";
     
    // set the header titles
    TableLayout tableA;
    TableLayout tableB;
    TableLayout tableC;
    TableLayout tableD;
    LinearLayout layoutB;
    TextView heading;
    ScrollView scrollViewA;
    HorizontalScrollView horizontalScrollViewB; 
    Context context;
    List<String> symbols;
    
    //this is for first column entries
    String headers[]={"Name","Open","High","Low","Close","Adj Close","Volume","Div Amt","Split Cof","Last Upd","Tm Zone","API Source"};
    List<StockData> stockDatas;
    public StockDataTableLayout(Activity context,String symbol)
    {
        super(context);
        this.context = context;
        stockDatas=this.initializeList();
        this.symbols=new ArrayList<String>();
        symbols.add(symbol);
        
        //initializing stock data with random values

        
        // initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
        this.initComponents();
        this.setComponentsId();
        this.setScrollViewAndHorizontalScrollViewTag();
        // add the components to be part of the main layout
        this.addComponentToMainLayout();
        this.setBackgroundColor(Color.BLACK);
        // add some table rows
        this.generateTables();
        this.resizeBodyTableRowHeight();
        this.resizeBodyTableRowWidth();
        
    }
 
    // this is just the sample data
    //initializing the list with some given values
    //when class is invoked first time. The values will be retreived later on AsyncTask
    List<StockData> initializeList()
    {
        List<StockData> stockDatas = new ArrayList<>();
        StockData stockData = new StockData("N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A","N/A");
        stockDatas.add(stockData);
        return stockDatas;
    }

	// initalized components 
    public void initComponents()
    {
         
    	this.tableA = new TableLayout(this.context); 
        this.tableB = new TableLayout(this.context); 
        this.tableC = new TableLayout(this.context); 
        this.tableD = new TableLayout(this.context);
        
        heading=new TextView(context);
        heading.setText("Statistics");
        heading.setBackgroundColor(Color.parseColor("#323232"));
        heading.setTextColor(Color.WHITE);
        heading.setTextSize(30);
        heading.setGravity(Gravity.CENTER);
        heading.setTypeface(null,Typeface.BOLD);
        
        layoutB=new LinearLayout(context);
        layoutB.setOrientation(LinearLayout.HORIZONTAL);
        this.horizontalScrollViewB = new HorizontalScrollView(this.context);
        
        this.scrollViewA = new ScrollView(this.context);
               
        //this.tableA.setBackgroundColor(Color.GREEN);
        this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);

         
    }
     
    // set essential component IDs
    private void setComponentsId()
    {
        this.scrollViewA.setId(1);
        this.horizontalScrollViewB.setId(2);
    }
     
    // set tags for some horizontal and vertical scroll view
    private void setScrollViewAndHorizontalScrollViewTag()
    {
        this.horizontalScrollViewB.setTag("horizontal scroll view b");
        this.scrollViewA.setTag("scroll view a");
    }
     
    // we add the components here in our TableMainLayout
    private void addComponentToMainLayout()
    {
         
        // RelativeLayout params were very useful here
        // the addRule method is the key to arrange the components properly
    	this.setOrientation(LinearLayout.VERTICAL);
    	LayoutParams componentParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        this.horizontalScrollViewB.addView(layoutB,componentParams);
        this.layoutB.addView(this.tableA);
        this.layoutB.addView(this.tableB);
        this.layoutB.addView(this.tableC);
        this.layoutB.addView(this.tableD);
        
    	this.scrollViewA.addView(this.horizontalScrollViewB,componentParams);
        // no need to assemble component A, since it is just a table
       
        // we extend this table layout as relative layout as seen during the creation of this class
        this.addView(heading,componentParams);
    	this.addView(this.scrollViewA,componentParams);
    }
     
    public String parseSymbol(String symbol)
    {
    	if(symbol!=null)
    	{
    		if(symbol.contains("=X"))
    		{
    			return symbol.replaceAll("=X","");
    		}
    		else
    		{
    			return symbol;
    		}
    	}
    	return null;
    }
    private void generateTables(){

        StockData sampleObject=this.stockDatas.get(0);
        String values[] =
        {
                sampleObject.getSymbol(),
                sampleObject.getOpen(),
                sampleObject.getHigh(),
                sampleObject.getLow(),
                sampleObject.getClose(),
                sampleObject.getAdj_clos(),
                sampleObject.getVolume(),
                sampleObject.getDiv_amt(),
                sampleObject.getSplit_cof(),
                sampleObject.getLast_upd(),
                sampleObject.getTm_zn(),
                sampleObject.getAPI_prov()
        };
        
        for(int i=0;i< this.headers.length;i+=2) {
            TableRow tableRowForTableA=this.tableRowForTable(this.parseSymbol(headers[i]));
            TableRow tableRowForTableC = this.tableRowForTable(this.parseSymbol(headers[i+1]));
            TableRow tableRowForTableB=this.tableRowForTable(this.parseSymbol(values[i]));
            TableRow tableRowForTableD = this.tableRowForTable(this.parseSymbol(values[i+1]));

            tableRowForTableA.setBackgroundColor(Color.parseColor("#323232"));
            tableRowForTableB.setBackgroundColor(Color.DKGRAY);
            tableRowForTableC.setBackgroundColor(Color.parseColor("#323232"));
            tableRowForTableD.setBackgroundColor(Color.DKGRAY);
            this.tableA.addView(tableRowForTableA);
            this.tableB.addView(tableRowForTableB);
            this.tableC.addView(tableRowForTableC);
            this.tableD.addView(tableRowForTableD);
        }
    }
     
    // a TableRow for table C. All the symbols here
    TableRow tableRowForTable(String symbol){
         
        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        params.setMargins(0, 2, 0, 0);
         
        TableRow tableRowForTable = new TableRow(this.context);
        TextView textView = this.bodyTextView(symbol);
        tableRowForTable.addView(textView,params);
         
        return tableRowForTable;
    }
     
        
    // table cell standard TextView
    TextView bodyTextView(String label)
    {
         
        TextView bodyTextView = new TextView(this.context);
        bodyTextView.setTextColor(Color.WHITE);
        bodyTextView.setTypeface(null,Typeface.BOLD);
        bodyTextView.setBackgroundColor(Color.TRANSPARENT);
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setPadding(50, 20, 50, 20);
         
        return bodyTextView;
    }
     
        
     
      
    // resize body table row height
    void resizeBodyTableRowHeight()
    {
         
        int tableChildCount = this.tableA.getChildCount();
         
        for(int x=0; x<tableChildCount; x++)
        {
         
            TableRow tableARow = (TableRow) this.tableA.getChildAt(x);
            TableRow tableBRow = (TableRow) this.tableB.getChildAt(x);
            TableRow tableCRow = (TableRow) this.tableC.getChildAt(x);
            TableRow tableDRow = (TableRow) this.tableD.getChildAt(x);
     
            int rowAHeight = this.viewHeight(tableARow);
            int rowBHeight = this.viewHeight(tableBRow);
            int rowCHeight = this.viewHeight(tableCRow);
            int rowDHeight = this.viewHeight(tableDRow);
        
                   
            int[] heights={rowAHeight,rowBHeight,rowCHeight,rowDHeight};
            
            TableRow[] tablerows={tableARow,tableBRow,tableCRow,tableDRow};
            int finalHeight=0;
            for(int height:heights)
            {
            	finalHeight=Math.max(finalHeight, height);
            }
            
 
            this.matchLayoutHeight(tablerows, finalHeight);  
            
        }
         
    }
    void resizeBodyTableRowWidth()
    {
    	TableLayout[] tables={this.tableA,this.tableB,this.tableC,this.tableD};
    	ArrayList<TableRow> rows=new ArrayList<TableRow>();
    	for(TableLayout table:tables)
    	{
    		rows.clear();
    		int tableChildCount =table.getChildCount();
    		int finalWidth=0;
    		for(int x=0; x<tableChildCount; x++)
            {
    			TableRow row=(TableRow)table.getChildAt(x);
    			rows.add(row);
    			finalWidth=Math.max(finalWidth,this.viewWidth(row));
            }
    		this.matchLayoutWidth(rows,finalWidth);
    		
    		
    	}
    }
     
    // match all height in a table row
    // to make a standard TableRow height
    private void matchLayoutHeight(TableRow[] tablerows, int height) 
    {
         
        for(TableRow tableRow:tablerows)
        {
        	// if a TableRow has only 1 child
	        if(tableRow.getChildCount()==1)
	        {
	             
	            View view = tableRow.getChildAt(0);
	            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
	            params.height = height - (params.bottomMargin + params.topMargin);
	             
	        }
        }
         
    }
    private void matchLayoutWidth(ArrayList<TableRow> tablerows, int width) 
    {
         
        for(TableRow tableRow:tablerows)
        {
        		             
            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.width = width - (params.leftMargin + params.rightMargin);
             
	        
        }
         
    }
 
      // read a view's height
    private int viewHeight(View view)
    {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }
 
    // read a view's width
    private int viewWidth(View view)
    {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }
 
    // horizontal scroll view custom class

    @Override
    public List<String> getSymbols() {
        return symbols;
    }

    @Override
    public List<StockData> getStockDatas()
    {
        if(this.stockDatas==null)
        {
            return null;
        }
        return this.stockDatas;
    }

    public void refresh()
    {
    	this.tableA.removeAllViews();
    	this.tableB.removeAllViews();
    	this.tableC.removeAllViews();
    	this.tableD.removeAllViews();
         
           
        this.generateTables();
         
        this.resizeBodyTableRowHeight();
        this.resizeBodyTableRowWidth();
        invalidate();
    	
    }
}