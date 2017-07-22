package com.epam.katowice.dto;


import java.util.List;

public class PageItemsHolder<TO> {

    private List<TO> items;
    private Pager pager;


    public PageItemsHolder(List<TO> items, Pager pager) {
        this.items = items;
        this.pager = pager;
    }

    public List<TO> getItems() {
        return items;
    }

    public Pager getPager() {
        return pager;
    }
}
