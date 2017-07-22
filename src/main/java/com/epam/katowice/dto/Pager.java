package com.epam.katowice.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Pager {

    static final int MAX_PAGES = 10;

    private int totalPages;
    private int currentPage;
    private int start;
    private int end;
    private boolean first;
    private boolean last;


    public Pager(int totalPages, int currentPage, int start, int end, boolean first, boolean last) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.start = start;
        this.end = end;
        this.first = first;
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pager pager = (Pager) o;
        return new EqualsBuilder()
            .append(totalPages, pager.totalPages)
            .append(currentPage, pager.currentPage)
            .append(start, pager.start)
            .append(end, pager.end)
            .append(first, pager.first)
            .append(last, pager.last)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(totalPages)
            .append(currentPage)
            .append(start)
            .append(end)
            .append(first)
            .append(last)
            .toHashCode();
    }

    public static class PagerBuilder {

        private int totalPages;
        private int currentPage;
        private boolean first;
        private boolean last;

        private PagerBuilder() {
        }

        public static PagerBuilder getInstance() {
            return new PagerBuilder();
        }

        public PagerBuilder withTotalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PagerBuilder withCurrentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public PagerBuilder withFirst(boolean first) {
            this.first = first;
            return this;
        }

        public PagerBuilder withLast(boolean last) {
            this.last = last;
            return this;
        }

        public Pager build() {
            int start = Math.max(0, currentPage - MAX_PAGES / 2);
            int end = Math.min(start + MAX_PAGES, totalPages - 1);
            if (end < 0) {
                end = 0;
            }
            return new Pager(totalPages, currentPage, start, end, first, last);
        }
    }
}
