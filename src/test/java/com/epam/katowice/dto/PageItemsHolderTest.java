package com.epam.katowice.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PageItemsHolderTest {

    private static final String TITLE_1 = "Title1", TITLE_2 = "Title2", TITLE_3 = "Title3";
    private static final int TOTAL_PAGES = 50, START_PAGE = 0, END_PAGE = 10, CURRENT_PAGE = 2;
    private static final boolean IS_FIRST_PAGE = false, IS_LAST_PAGE = false;

    private Pager pager = new Pager(TOTAL_PAGES, CURRENT_PAGE, START_PAGE, END_PAGE, IS_FIRST_PAGE, IS_LAST_PAGE);
    private FilmTo filmTo1 = new FilmTo(), filmTo2 = new FilmTo(), filmTo3 = new FilmTo();

    @Before
    public void setUp() {
        filmTo1.setTitle(TITLE_1);
        filmTo2.setTitle(TITLE_2);
        filmTo3.setTitle(TITLE_3);
    }

    @Test
    public void shouldConstructorSetValues() {
        //given
        //when
        PageItemsHolder<FilmTo> pageItemsHolder = new PageItemsHolder(Arrays.asList(filmTo1, filmTo2, filmTo3), pager);

        //then
        assertThat(pageItemsHolder.getPager()).isNotNull().isEqualTo(pager);
        assertThat(pageItemsHolder.getItems()).isNotNull().isNotEmpty().containsExactly(filmTo1, filmTo2, filmTo3);
    }

}