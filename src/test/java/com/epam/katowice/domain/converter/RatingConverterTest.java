package com.epam.katowice.domain.converter;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.epam.katowice.domain.Rating;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class RatingConverterTest {

    public static final String INCORRECT_RATING_CODE = "X";
    private RatingConverter ratingConverter = new RatingConverter();

    @Test
    @Parameters(method = "getRatings")
    public void shouldConvertRatingCodeStringToRatingEnum(String ratingCode, Rating rating) {
        //given
        //when
        Rating ratingResult = ratingConverter.convertToEntityAttribute(ratingCode);

        //then
        assertThat(ratingResult).isNotNull().isEqualTo(rating);
    }

    @Test
    public void shouldConvertNullRatingCodeToNull() {
        //given
        //when
        Rating ratingResult = ratingConverter.convertToEntityAttribute(null);

        //then
        assertThat(ratingResult).isNull();
    }

    @Test
    public void shouldConvertEmptyRatingCodeToNull() {
        //given
        //when
        Rating ratingResult = ratingConverter.convertToEntityAttribute("");

        //then
        assertThat(ratingResult).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionRatingCodeIncorrect() {
        //given
        //when
        Rating ratingResult = ratingConverter.convertToEntityAttribute(INCORRECT_RATING_CODE);

        //then
        fail();
    }

    @Test
    @Parameters(method = "getRatings")
    public void shouldConvertRatingEnumToRatingCodeString(String ratingCode, Rating rating) {
        //given
        //when
        String ratingResult = ratingConverter.convertToDatabaseColumn(rating);

        //then
        assertThat(ratingResult).isNotNull().isEqualTo(ratingCode);
    }

    @Test
    public void shouldConvertNullRatingEnumToNull() {
        //given
        //when
        String ratingResult = ratingConverter.convertToDatabaseColumn(null);

        //then
        assertThat(ratingResult).isNull();
    }

    private static final Object[] getRatings() {
        return new Object[]{
            new Object[]{"G", Rating.G},
            new Object[]{"PG", Rating.PG},
            new Object[]{"PG-13", Rating.PG13},
            new Object[]{"R", Rating.R},
            new Object[]{"NC-17", Rating.NC17}
        };
    }
}