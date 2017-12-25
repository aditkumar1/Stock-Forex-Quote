package com.stockquote.adit.stockquote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;



/**
 * This is a helper class that implements the management of tabs and all
 * details of connecting a ViewPager with associated TabHost.  It relies on a
 * trick.  Normally a tab host has a simple API for supplying a View or
 * Intent that each tab will show.  This is not sufficient for switching
 * between pages.  So instead we make the content part of the tab host
 * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
 * view to show as the tab content.  It listens to changes in tabs, and takes
 * care of switch to the correct paged in the ViewPager whenever the selected
 * tab changes.
 */
public class CustomFragmentAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener
{
    private final Context mContext;
    private final ViewPager mViewPager;
    private final TabHost mTabHost;
    private final String symbol;
    private String range;
    

   
    public String getRange()
    {
    	return mTabHost.getCurrentTabTag(); 
    }
    public String getSymbol()
    {
		return symbol;
	}

	public CustomFragmentAdapter(FragmentActivity activity,TabHost mTabHost,ViewPager pager,String symbol)
    {
        super(activity.getSupportFragmentManager());
        mContext = activity;
        mViewPager = pager;
        this.mTabHost=mTabHost;
        
        mTabHost.setOnTabChangedListener(this);
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
        this.symbol=symbol;
        
    }
	class DummyTabFactory implements TabHost.TabContentFactory
    {
        private final Context mContext;

        public DummyTabFactory(Context context)
        {
            mContext = context;
        }

        public View createTabContent(String tag)
        {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }
	public void addTab(TabHost.TabSpec tabSpec)
    {
        tabSpec.setContent(new DummyTabFactory(mContext));
        mTabHost.addTab(tabSpec);
        notifyDataSetChanged();
    }
	
	@Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
        case 0:
        	return Fragment.instantiate(mContext,PageOneFragment.class.getName());
		case 1:
        	return Fragment.instantiate(mContext,PageTwoFragment.class.getName());
        	default:
        		break;
        }
        return null;

    }

    @Override
	public int getItemPosition(Object object) 
    {
		return -1;
		
	}
	@Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        // Unfortunately when TabHost changes the current tab, it kindly
        // also takes care of putting focus on it when not in touch mode.
        // The jerk.
        // This hack tries to prevent this from pulling focus out of our
        // ViewPager.
        //TabWidget widget = mTabHost.getTabWidget();
        //int oldFocusability = widget.getDescendantFocusability();
        //widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        //mTabHost.setCurrentTab(position);
        //widget.setDescendantFocusability(oldFocusability);
    }

    public void onPageScrollStateChanged(int state)
    {
    
    }
	@Override
	public void onTabChanged(String tabId)
	{
		this.mViewPager.setAdapter(this);
		
	}
}

