package com.epam.katowice.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;


public class PagerTest {

    private static final int CURRENT_PAGE = 4;
    private static final int TOTAL_PAGES = 10;
    private static final boolean IS_FIRST = false;
    private static final boolean IS_LAST = false;
    private static final int START = 0;
    private static final int END = 9;

    @Test
    public void shouldConstructorSetValues() {
        //given
        //when
        Pager pagerResult = new Pager(TOTAL_PAGES, CURRENT_PAGE, START, END, IS_FIRST, IS_LAST);

        //then
        assertThat(pagerResult.getTotalPages()).isEqualTo(TOTAL_PAGES);
        assertThat(pagerResult.getCurrentPage()).isEqualTo(CURRENT_PAGE);
        assertThat(pagerResult.getStart()).isEqualTo(START);
        assertThat(pagerResult.getEnd()).isEqualTo(END);
        assertThat(pagerResult.isFirst()).isEqualTo(IS_FIRST);
        assertThat(pagerResult.isLast()).isEqualTo(IS_LAST);
    }
}