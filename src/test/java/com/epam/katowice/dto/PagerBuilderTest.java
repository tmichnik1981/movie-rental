package com.epam.katowice.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class PagerBuilderTest {

    private static final int CURRENT_PAGE_1 = 0, CURRENT_PAGE_2 = 4, CURRENT_PAGE_3 = 9, CURRENT_PAGE_4 = 0;
    private static final int TOTAL_PAGES_1 = 10, TOTAL_PAGES_2 = 10, TOTAL_PAGES_3 = 10, TOTAL_PAGES_4 = 0;
    private static final boolean IS_FIRST_1 = true, IS_FIRST_2 = false, IS_FIRST_3 = false, IS_FIRST_4 = false;
    private static final boolean IS_LAST_1 = false, IS_LAST_2 = false, IS_LAST_3 = true, IS_LAST_4 = false;
    private static final int START_1 = 0, START_2 = 0, START_3 = 4, START_4 = 0;
    private static final int END_1 = 9, END_2 = 9, END_3 = 9, END_4 = 0;

    private Pager.PagerBuilder pagerBuilder = Pager.PagerBuilder.getInstance();

    @Test
    @Parameters(method = "getParameters")
    public void shouldBuildPager(int currentPage, int totalPages, boolean first, boolean last, Pager expectedResult) {
        //given
        pagerBuilder.withCurrentPage(currentPage);
        pagerBuilder.withTotalPages(totalPages);
        pagerBuilder.withFirst(first);
        pagerBuilder.withLast(last);

        //when
        Pager pagerResult = pagerBuilder.build();

        //then
        assertThat(pagerResult).isNotNull().isEqualTo(expectedResult);
    }

    private static final Object[] getParameters() {
        return new Object[]{
            new Object[]{CURRENT_PAGE_1, TOTAL_PAGES_1, IS_FIRST_1, IS_LAST_1, new Pager(TOTAL_PAGES_1, CURRENT_PAGE_1,
                                                                                         START_1, END_1, IS_FIRST_1,
                                                                                         IS_LAST_1)},
            new Object[]{CURRENT_PAGE_2, TOTAL_PAGES_2, IS_FIRST_2, IS_LAST_2, new Pager(TOTAL_PAGES_2, CURRENT_PAGE_2,
                                                                                         START_2, END_2, IS_FIRST_2,
                                                                                         IS_LAST_2)},
            new Object[]{CURRENT_PAGE_3, TOTAL_PAGES_3, IS_FIRST_3, IS_LAST_3, new Pager(TOTAL_PAGES_3, CURRENT_PAGE_3,
                                                                                         START_3, END_3, IS_FIRST_3,
                                                                                         IS_LAST_3)},
            new Object[]{CURRENT_PAGE_4, TOTAL_PAGES_4, IS_FIRST_4, IS_LAST_4, new Pager(TOTAL_PAGES_4, CURRENT_PAGE_4,
                                                                                         START_4, END_4, IS_FIRST_4,
                                                                                         IS_LAST_4)}
        };
    }

}