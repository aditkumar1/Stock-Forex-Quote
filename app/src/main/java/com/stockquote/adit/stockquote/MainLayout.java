package com.stockquote.adit.stockquote;

import java.util.List;

/**
 * Created by adit on 2/8/16.
 */
public interface MainLayout {
    public List<String> getSymbols();
    public <T> List<T> getStockDatas();
    public void refresh();
}
