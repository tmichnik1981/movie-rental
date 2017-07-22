package com.epam.katowice.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import java.util.Calendar;

public class SearchFilmFormTest {

    private static final short DEFAULT_SHORT_ZERO_VALUE = 0;
    private static final int DEFAULT_INT_ZERO_VALUE = 0;
    private static final int DEFAULT_NOT_EXISTING_CATEGORY_ID = -1;
    private static final int DEFAULT_NOT_EXISTING_LANGUAGE_ID = -1;

    @Test
    public void shouldNotConstructorOverWriteDefaultValues() {
        //given
        //when
        SearchFilmForm searchFilmFormResult = new SearchFilmForm();

        //then
        assertThat(searchFilmFormResult.getActorName()).isNull();
        assertThat(searchFilmFormResult.getCategory()).isEqualTo(DEFAULT_NOT_EXISTING_CATEGORY_ID);
        assertThat(searchFilmFormResult.getLanguage()).isEqualTo(DEFAULT_NOT_EXISTING_LANGUAGE_ID);
        assertThat(searchFilmFormResult.getLengthFrom()).isEqualTo(DEFAULT_SHORT_ZERO_VALUE);

        assertThat(searchFilmFormResult.getLengthTo()).isEqualTo(DEFAULT_SHORT_ZERO_VALUE);
        assertThat(searchFilmFormResult.getTitle()).isNull();
    }

    @Test
    public void shouldConstructorSetCurrentYearAsReleaseYear() {
        //given
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        //when
        SearchFilmForm searchFilmFormResult = new SearchFilmForm();

        //then
        assertThat(searchFilmFormResult.getReleaseYearFrom()).isEqualTo(DEFAULT_INT_ZERO_VALUE);
        assertThat(searchFilmFormResult.getReleaseYearTo()).isEqualTo(currentYear);
    }
}