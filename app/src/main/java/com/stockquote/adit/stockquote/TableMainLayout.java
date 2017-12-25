package com.stockquote.adit.stockquote;
 
 
import java.util.ArrayList;
import java.util.List;
 






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

 
public class TableMainLayout extends RelativeLayout implements OnClickListener,View.OnLongClickListener,MainLayout
{
 
    public final String TAG = "TableMainLayout.java";
    // set the header titles
    String headers[]={"Sym","Open","High","Low","Close","Volume"};
    int headerCellsWidth[] = new int[headers.length];

    TableLayout messageTable;
    TableLayout tableA;
    TableLayout tableB;
    TableLayout tableC;
    TableLayout tableD;

    HorizontalScrollView horizontalScrollViewB;
    HorizontalScrollView horizontalScrollViewD;
 
    ScrollView scrollViewC;
    ScrollView scrollViewD;
     
    Context context;
    
    //this is for first column entries
    List<String> symbols;
    
    //this is for other column entries
    List<ListStockData> stockDatas;
     
    public List<String> getSymbols()
	{
		// TODO Auto-generated method stub
		return  symbols;
	}
     
    public TableMainLayout(Activity context) 
    {
         
        super(context);
        this.context = context;
       
        //getting first column entries from database. Call it first as stockDatas use it
         symbols=new DatabaseHandler(context).readAllSymbols();
        
        //initializing stock data with random values
        stockDatas=this.initializeList();
        
       
        
        
        // initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)
        
        
        this.initComponents();
        this.setComponentsId();
        this.setScrollViewAndHorizontalScrollViewTag();
         
         
        // no need to assemble component A, since it is just a table
        this.horizontalScrollViewB.addView(this.tableB);
         
        this.scrollViewC.addView(this.tableC);
         
        this.scrollViewD.addView(this.horizontalScrollViewD);
        this.horizontalScrollViewD.addView(this.tableD);
         
        // add the components to be part of the main layout
        this.addComponentToMainLayout();
        this.setBackgroundColor(Color.BLACK);
         
         
        // add some table rows
        this.addTableRowToMessageComponent();
        this.addTableRowToTableA();
        this. addTableRowToTableB();
         
        this.resizeHeaderHeight();
         
        this.getTableRowHeaderCellWidth();
         
        this.generateTableC_AndTable_B();
         
        this.resizeBodyTableRowHeight();
    }

 
    // this is just the sample data
    //initializing the list with some given values
    //when class is invoked first time. The values will be retreived later on AsyncTask
    List<ListStockData> initializeList()
    {
        List<ListStockData> stockDatas = new ArrayList<ListStockData>();
        for(int x=0; x< symbols.size(); x++)
        {
            ListStockData stockdata = new ListStockData("N/A","N/A","N/A","N/A","N/A",symbols.get(x));
            stockDatas.add(stockdata);
        }
        return stockDatas;
    }
    void initializeNewStockData(){
        if(stockDatas==null){
            List<StockData> stockDatas = new ArrayList<StockData>();
        }
        ListStockData stockdata = new ListStockData("  N/A  ","  N/A  ","  N/A  ","  N/A  ","  N/A  ",this.symbols.get(stockDatas.size()));
        stockDatas.add(stockdata);
    }
     
    
    
    public List<ListStockData> getStockDatas()
    {
		if(stockDatas==null)
		{
			return null;
		}
    	return stockDatas;
	}

	// initalized components 
    public void initComponents()
    {
        this.messageTable = new TableLayout(this.context);
    	this.tableA = new TableLayout(this.context); 
        this.tableB = new TableLayout(this.context); 
        this.tableC = new TableLayout(this.context); 
        this.tableD = new TableLayout(this.context);
        
        this.horizontalScrollViewB = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewD = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewB.setHorizontalScrollBarEnabled(false);
        
        this.scrollViewC = new MyScrollView(this.context);
        this.scrollViewD = new MyScrollView(this.context);
        this.scrollViewC.setVerticalScrollBarEnabled(false);
       
        //this.tableA.setBackgroundColor(Color.GREEN);
        this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);

         
    }
     
    // set essential component IDs
    private void setComponentsId()
    {
        this.messageTable.setId(10);
        this.tableA.setId(1);
        this.horizontalScrollViewB.setId(2);
        this.scrollViewC.setId(3);
        this.scrollViewD.setId(4);
    }
     
    // set tags for some horizontal and vertical scroll view
    private void setScrollViewAndHorizontalScrollViewTag()
    {
         
        this.horizontalScrollViewB.setTag("horizontal scroll view b");
        this.horizontalScrollViewD.setTag("horizontal scroll view d");
         
        this.scrollViewC.setTag("scroll view c");
        this.scrollViewD.setTag("scroll view d");
    }
     
    // we add the components here in our TableMainLayout
    private void addComponentToMainLayout()
    {
         
        // RelativeLayout params were very useful here
        // the addRule method is the key to arrange the components properly
        LayoutParams messageTable_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

        LayoutParams componentA_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentA_Params.addRule(RelativeLayout.BELOW, this.messageTable.getId());

        LayoutParams componentB_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentB_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());
        componentB_Params.addRule(RelativeLayout.BELOW, this.messageTable.getId());
         
        LayoutParams componentC_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentC_Params.addRule(RelativeLayout.BELOW, this.tableA.getId());
         
        LayoutParams componentD_Params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentD_Params.addRule(RelativeLayout.RIGHT_OF, this.scrollViewC.getId());
        componentD_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewB.getId());
         
        // 'this' is a relative layout, 
        // we extend this table layout as relative layout as seen during the creation of this class
        this.addView(this.messageTable,messageTable_Params);
        this.addView(this.tableA,componentA_Params);
        this.addView(this.horizontalScrollViewB, componentB_Params);
        this.addView(this.scrollViewC, componentC_Params);
        this.addView(this.scrollViewD, componentD_Params);
             
    }
     
 
     
    private void addTableRowToTableA()
    {
        this.tableA.addView(this.componentATableRow());
    }
    private void addTableRowToMessageComponent()
    {
        this.messageTable.addView(messageComponentTableRow());
    }
     
    private void addTableRowToTableB()
    {
        this.tableB.addView(this.componentBTableRow());
    }

    TableRow messageComponentTableRow()
    {
        TableRow messageComponentATableRow = new TableRow(this.context);
        TextView textView = this.headerTextView("Long press any item to remove");
        textView.setGravity(Gravity.CENTER);
        messageComponentATableRow.addView(textView);
        messageComponentATableRow.setGravity(Gravity.CENTER);
        messageComponentATableRow.setBackgroundColor(Color.BLACK);
        return messageComponentATableRow;
    }
    // generate table row of table A
    TableRow componentATableRow()
    {
        TableRow componentATableRow = new TableRow(this.context);
        TextView textView = this.headerTextView(headers[0]);
        componentATableRow.addView(textView);
        componentATableRow.setBackgroundColor(Color.BLACK);
        return componentATableRow;
    }
     
    // generate table row of table B
    TableRow componentBTableRow()
    {
        TableRow componentBTableRow = new TableRow(this.context);
        int headerFieldCount = this.headers.length;
         
        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        params.setMargins(2, 0, 0, 0);
         
        for(int x=0; x<(headerFieldCount-1); x++)
        {
            TextView textView = this.headerTextView(this.headers[x+1]);
            textView.setLayoutParams(params);
            componentBTableRow.addView(textView);
        }
        componentBTableRow.setBackgroundColor(Color.BLACK); 
        return componentBTableRow;
    }
     
    // generate table row of table C and table D
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
    private void generateTableC_AndTable_B(){


        //fill the rows based on first column entries 
        
        for(int i=0;i< this. symbols.size();i++)
        {
             
           
        	TableRow tableRowForTableC = this.tableRowForTableC(this.parseSymbol( symbols.get(i)));
            TableRow taleRowForTableD = this.taleRowForTableD(this.stockDatas.get(i));
             
            if(i%2==0)
            {
            	tableRowForTableC.setBackgroundColor(Color.parseColor("#323232"));
            	taleRowForTableD.setBackgroundColor(Color.parseColor("#323232"));
            }
            else
            {
            	tableRowForTableC.setBackgroundColor(Color.DKGRAY);
            	taleRowForTableD.setBackgroundColor(Color.DKGRAY);

            }
            tableRowForTableC.setOnClickListener(this);
            tableRowForTableC.setOnLongClickListener(this);
            tableRowForTableC.setClickable(true);
            tableRowForTableC.setTag(i);
            
            taleRowForTableD.setOnClickListener(this);
            taleRowForTableD.setOnLongClickListener(this);
            taleRowForTableD.setClickable(true);
            taleRowForTableD.setTag(i);
            
            this.tableC.addView(tableRowForTableC);
            this.tableD.addView(taleRowForTableD);
             
        }
    }
     
    // a TableRow for table C. All the symbols here
    TableRow tableRowForTableC(String symbol){
         
        TableRow.LayoutParams params = new TableRow.LayoutParams( this.headerCellsWidth[0],LayoutParams.MATCH_PARENT);
        params.setMargins(0, 2, 0, 0);
         
        TableRow tableRowForTableC = new TableRow(this.context);
        TextView textView = this.bodyTextView(symbol);
        tableRowForTableC.addView(textView,params);
         
        return tableRowForTableC;
    }
     
    TableRow taleRowForTableD(ListStockData sampleObject)
    {
 
        TableRow tableRowForTableD = new TableRow(this.context);
     
         
        int loopCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();
        String info[] = 
        	{
                    sampleObject.getOpen(),
            sampleObject.getHigh(),
            sampleObject.getLow(),sampleObject.getClose(),sampleObject.getVolume()
        };
         
        for(int x=0 ; x<loopCount; x++)
        {
            TableRow.LayoutParams params = new TableRow.LayoutParams( headerCellsWidth[x+1],LayoutParams.MATCH_PARENT);
            
            params.setMargins(2, 2, 0, 0);
             
            TextView textViewB = this.bodyTextView(info[x]);
            tableRowForTableD.addView(textViewB,params);
        }
         
        return tableRowForTableD;
         
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
        bodyTextView.setPadding(30, 20, 30, 20);
        return bodyTextView;
    }
     
    // header standard TextView
    TextView headerTextView(String label)
    {
         
        TextView headerTextView = new TextView(this.context);
        headerTextView.setTextColor(Color.WHITE);
        headerTextView.setBackgroundColor(Color.TRANSPARENT);
        headerTextView.setTypeface(null,Typeface.BOLD);
        
        headerTextView.setText(label);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(60, 10, 60, 10);

        return headerTextView;
    }
    // resizing TableRow height starts here
    void resizeHeaderHeight()
    {
        TableRow productNameHeaderTableRow = (TableRow) this.tableA.getChildAt(0);
        TableRow productInfoTableRow = (TableRow)  this.tableB.getChildAt(0);
 
        int rowAHeight = this.viewHeight(productNameHeaderTableRow);
        int rowBHeight = this.viewHeight(productInfoTableRow);
 
        TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
        int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;
 
        this.matchLayoutHeight(tableRow, finalHeight);
    }
     
    void getTableRowHeaderCellWidth(){
         
        int tableAChildCount = ((TableRow)this.tableA.getChildAt(0)).getChildCount();
        int tableBChildCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();;
         
        for(int x=0; x<(tableAChildCount+tableBChildCount); x++){
             
            if(x==0){
                this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableA.getChildAt(0)).getChildAt(x));
            }else{
                this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableB.getChildAt(0)).getChildAt(x-1));
            }
             
        }
    }
     
    // resize body table row height
    void resizeBodyTableRowHeight()
    {
         
        int tableC_ChildCount = this.tableC.getChildCount();
         
        for(int x=0; x<tableC_ChildCount; x++){
         
            TableRow productNameHeaderTableRow = (TableRow) this.tableC.getChildAt(x);
            TableRow productInfoTableRow = (TableRow)  this.tableD.getChildAt(x);
     
            int rowAHeight = this.viewHeight(productNameHeaderTableRow);
            int rowBHeight = this.viewHeight(productInfoTableRow);
     
            TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
            int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;
 
            this.matchLayoutHeight(tableRow, finalHeight);      
        }
         
    }
     
    // match all height in a table row
    // to make a standard TableRow height
    private void matchLayoutHeight(TableRow tableRow, int height)
    {
         
        int tableRowChildCount = tableRow.getChildCount();
         
        // if a TableRow has only 1 child
        if(tableRow.getChildCount()==1){
             
            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);
             
            return ;
        }
         
        // if a TableRow has more than 1 child
        for (int x = 0; x < tableRowChildCount; x++) {
             
            View view = tableRow.getChildAt(x);
             
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
 
            if (!isTheHeighestLayout(tableRow, x)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                return;
            }
        }
    }
 
    // check if the view has the highest height in a TableRow
    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {
         
        int tableRowChildCount = tableRow.getChildCount();
        int heighestViewPosition = -1;
        int viewHeight = 0;
 
        for (int x = 0; x < tableRowChildCount; x++) {
            View view = tableRow.getChildAt(x);
            int height = this.viewHeight(view);
 
            if (viewHeight < height) {
                heighestViewPosition = x;
                viewHeight = height;
            }
        }
        return heighestViewPosition == layoutPosition;
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
    
    public void refresh()
    {
        symbols=new DatabaseHandler(context).readAllSymbols();
        while(this.stockDatas.size()<symbols.size()){
            initializeNewStockData();
        }
        this.tableA.removeAllViews();
    	this.tableB.removeAllViews();
    	this.tableC.removeAllViews();
    	this.tableD.removeAllViews();
    	this.addTableRowToTableA();
        this. addTableRowToTableB();

        this.resizeHeaderHeight();

        this.getTableRowHeaderCellWidth();

        this.generateTableC_AndTable_B();

        this.resizeBodyTableRowHeight();
        invalidate();
    	
    }


    class MyHorizontalScrollView extends HorizontalScrollView
	{
 
        public MyHorizontalScrollView(Context context) {
            super(context);
        }
         
        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();
             
            if(tag.equalsIgnoreCase("horizontal scroll view b")){
                horizontalScrollViewD.scrollTo(l, 0);
            }else{
                horizontalScrollViewB.scrollTo(l, 0);
            }
        }
    }
 
    // scroll view custom class
    class MyScrollView extends ScrollView{
 
        public MyScrollView(Context context)
        {
            super(context);
        }
         
        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
             
            String tag = (String) this.getTag();
             
            if(tag.equalsIgnoreCase("scroll view c")){
                scrollViewD.scrollTo(0, t);
            }else{
                scrollViewC.scrollTo(0,t);
            }
        }
    }
	@Override
	public void onClick(View v) 
	{
		Intent i=new Intent(context,GraphActivity.class);
		int index=(Integer)v.getTag();
		i.putExtra("Symbol",this. symbols.get(index));
		Toast.makeText(context.getApplicationContext(),(String)this. symbols.get(index),Toast.LENGTH_SHORT).show();
		context.startActivity(i);
	}
    @Override
    public boolean onLongClick(View view) {
        int index=(Integer)view.getTag();
        String sym=symbols.get(index);
        new DatabaseHandler(context).deleteSymbol(sym);
        this.getStockDatas().remove(index);
        this.refresh();
        return true;
    }
}