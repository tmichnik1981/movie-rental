package com.epam.katowice.service;

import static junit.framework.TestCase.fail;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Actor;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Language;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.SpecialFeature;
import com.epam.katowice.dto.AddFilmForm;
import com.epam.katowice.dto.DetailedFilmTo;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.SearchFilmForm;
import com.epam.katowice.mapping.FilmMapper;
import com.epam.katowice.repository.FilmRepository;
import com.epam.katowice.repository.FilmSpecificationCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceImplTest {

    private static final long FILMS_NUMBER_10 = 10l;
    private static final long NO_FILMS = 0l;
    private static final String TITLE_1 = "Title1", TITLE_2 = "Title2", TITLE_3 = "Title3";
    private static final short FILM1_LENGTH = 120;
    private static final int FILM1_RELEASE_YEAR = 2016;
    private static final long ID_1 = 1, ID_2 = 2, ID_3 = 3;

    private static final String FILM1_TITLE = "Title1";

    private FilmTo filmTo1 = new FilmTo(), filmTo2 = new FilmTo(), filmTo3 = new FilmTo();
    private Film film1 = new Film(), film2 = new Film(), film3 = new Film();
    private Actor actor1 = new Actor(), actor2 = new Actor(), actor3 = new Actor();

    @Mock
    private Page<Film> filmPage;
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private FilmMapper filmMapper;
    @Mock
    private PageRequest pageRequest;
    @Mock
    private SearchFilmForm searchFilmForm;
    @Mock
    private LanguageService languageService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ActorService actorService;
    @Mock
    private FilmSpecificationCreator filmSpecificationCreator;
    @InjectMocks
    private FilmService filmService = new FilmServiceImpl();


    @Before
    public void setUp() {
        filmTo1.setTitle(TITLE_1);
        filmTo2.setTitle(TITLE_2);
        filmTo3.setTitle(TITLE_3);
        filmTo1.setId(ID_1);
        filmTo2.setId(ID_2);
        filmTo3.setId(ID_3);

        Specification<Film> specification = mock(Specification.class);

        when(filmSpecificationCreator.advancedFilmSearch(searchFilmForm)).thenReturn(specification);
        when(filmRepository.findAll(specification, pageRequest)).thenReturn(filmPage);

        when(filmMapper.asDto(film1, FilmTo.class)).thenReturn(filmTo1);
        when(filmMapper.asDto(film2, FilmTo.class)).thenReturn(filmTo2);
        when(filmMapper.asDto(film3, FilmTo.class)).thenReturn(filmTo3);

        actor1.setId(ID_1);
        actor2.setId(ID_2);
        actor3.setId(ID_3);
    }

    @Test
    public void shouldReturnFilmsNumber() {
        //given
        when(filmRepository.count()).thenReturn(FILMS_NUMBER_10);

        //when
        long resultFilmNumber = filmService.count();

        //then
        assertThat(resultFilmNumber).isEqualTo(FILMS_NUMBER_10);
    }

    @Test
    public void shouldNotReturnFilmsNumberWhenNoFilmsInRepository() {
        //given
        when(filmRepository.count()).thenReturn(NO_FILMS);

        //when
        long resultFilmNumber = filmService.count();

        //then
        assertThat(resultFilmNumber).isEqualTo(NO_FILMS);
    }

    @Test
    public void shouldFindAllFilms() {
        //given
        when(filmPage.getContent()).thenReturn(Arrays.asList(film1, film2, film3));

        //when
        PageItemsHolder<FilmTo>
            pageItemsHolderResult =
            filmService.findBySearchFormCriteria(searchFilmForm, pageRequest);

        //then
        assertThat(pageItemsHolderResult).isNotNull();
        assertThat(pageItemsHolderResult.getItems()).isNotNull().isNotEmpty()
            .containsExactly(filmTo1, filmTo2, filmTo3);
    }

    @Test
    public void shouldNotFindAnyFilmsWhenNoFilmsInRepository() {
        //given
        when(filmPage.getContent()).thenReturn(Collections.emptyList());

        //when
        PageItemsHolder<FilmTo>
            pageItemsHolderResult =
            filmService.findBySearchFormCriteria(searchFilmForm, pageRequest);

        //then
        assertThat(pageItemsHolderResult).isNotNull();
        assertThat(pageItemsHolderResult.getItems()).isNotNull().isEmpty();
    }


    @Test
    public void shouldFindFilmDetails(){
        //given
        DetailedFilmTo detailedFilmTo = new DetailedFilmTo();
        detailedFilmTo.setId(ID_1);
        detailedFilmTo.setTitle(TITLE_1);

        when((filmRepository.findOne(1l))).thenReturn(film1);
        when(filmMapper.asDto(film1, DetailedFilmTo.class)).thenReturn(detailedFilmTo);

        //when
        DetailedFilmTo detailedFilmToResult = filmService.findDetailsById(ID_1);

        //then
        assertThat(detailedFilmToResult).isNotNull().isEqualTo(detailedFilmTo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilmNotExists() {
        //given

        when((filmRepository.findOne(ID_1))).thenReturn(null);

        //when
        filmService.findDetailsById(ID_1);

        //then
        fail();
    }

    @Test
    public void shouldAddNewFilm() {
        //given
        AddFilmForm.AddFilmFormBuilder addFilmFormBuilder  = AddFilmForm.AddFilmFormBuilder.getInstance();
        addFilmFormBuilder.withTitle(FILM1_TITLE)
            .withLength(FILM1_LENGTH)
            .withReleaseYear(FILM1_RELEASE_YEAR)
            .withLanguageId(ID_1)
            .withRating(Rating.PG);

        List<SpecialFeature>
            specialFeatures =
            Arrays.asList(SpecialFeature.COMMENTARIES, SpecialFeature.DELETED_SCENES);
        addFilmFormBuilder.withSpecialFeatures(specialFeatures);

        Set<Long> actorIds = new HashSet<>();
        actorIds.add(ID_1);
        actorIds.add(ID_2);
        actorIds.add(ID_3);
        addFilmFormBuilder.withActorsIds(actorIds);

        Language language = new Language();
        language.setId(ID_1);
        when(languageService.getOne(ID_1)).thenReturn(language);
        when(actorService.getOne(ID_1)).thenReturn(actor1);
        when(actorService.getOne(ID_2)).thenReturn(actor2);
        when(actorService.getOne(ID_3)).thenReturn(actor3);

        filmTo1.setTitle(TITLE_1);
        filmTo1.setId(ID_1);

        Film filmEntity = mock(Film.class);
        when(filmRepository.save(any(Film.class))).thenReturn(filmEntity);
        when(filmMapper.asDto(filmEntity, FilmTo.class)).thenReturn(filmTo1);

        //when
        FilmTo filmToResult = filmService.addNew(addFilmFormBuilder.build());

        //then
        verify(filmMapper).asDto(filmEntity, FilmTo.class);
        assertThat(filmToResult).isNotNull().isEqualTo(filmTo1);

        ArgumentCaptor<Film> saveMethodArgumentCaptor = ArgumentCaptor.forClass(Film.class);
        verify(filmRepository).save(saveMethodArgumentCaptor.capture());

        Film saveMethodPassedArgument = saveMethodArgumentCaptor.getValue();
        assertThat(saveMethodPassedArgument.getTitle()).isNotNull().isEqualTo(FILM1_TITLE);
        assertThat(saveMethodPassedArgument.getLength()).isNotNull().isEqualTo(FILM1_LENGTH);
        assertThat(saveMethodPassedArgument.getReleaseYear()).isNotNull().isEqualTo(FILM1_RELEASE_YEAR);
        assertThat(saveMethodPassedArgument.getLanguage()).isNotNull().isEqualTo(language);
        assertThat(saveMethodPassedArgument.getRating()).isNotNull().isEqualTo(Rating.PG);
        assertThat(saveMethodPassedArgument.getSpecialFeatures()).isNotNull().containsAll(specialFeatures);
        assertThat(saveMethodPassedArgument.getActors()).containsOnly(actor1, actor2, actor3);
    }

}