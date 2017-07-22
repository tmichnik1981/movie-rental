package com.epam.katowice.mapping;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.dto.FilmTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class FilmMapperTest {

    private static final String FILM_TITLE = "Title1", FILM_DESCRIPTION = "Description1";
    private static final int FILM_RELEASE_YEAR = 2016;
    private static final short FILM_LENGTH = 120;

    private FilmTo filmTo1 = new FilmTo();
    private Film film1 = new Film();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FilmMapper filmMapper = new FilmMapper();

    @Before
    public void setUp() {
        film1.setTitle(FILM_TITLE);
        filmTo1.setTitle(FILM_TITLE);
        filmTo1.setDescription(FILM_DESCRIPTION);
        filmTo1.setLength(FILM_LENGTH);
        filmTo1.setRating(Rating.R);
        filmTo1.setReleaseYear(FILM_RELEASE_YEAR);
    }


    @Test
    public void shouldMapFilmToDto() {
        //given
        when(modelMapper.map(film1, FilmTo.class)).thenReturn(filmTo1);

        //when
        FilmTo filmToResult = filmMapper.asDto(film1, FilmTo.class);

        //then
        assertThat(filmToResult).isNotNull().isEqualTo(filmTo1);
    }
}