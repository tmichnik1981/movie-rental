package com.epam.katowice.controller;

import static com.epam.katowice.controller.MovieRentalController.ALL_CATEGORIES_MODEL_ATTR;
import static com.epam.katowice.controller.MovieRentalController.ALL_LANGUAGES_MODEL_ATTR;
import static com.epam.katowice.controller.MovieRentalController.DETAILS_ENDPOINT;
import static com.epam.katowice.controller.MovieRentalController.DETAILS_VIEW;
import static com.epam.katowice.controller.MovieRentalController.FILMS_PAGE_MODEL_ATTR;
import static com.epam.katowice.controller.MovieRentalController.HOME_ENDPOINT;
import static com.epam.katowice.controller.MovieRentalController.HOME_VIEW;
import static com.epam.katowice.controller.MovieRentalController.ID_PARAM;
import static com.epam.katowice.controller.MovieRentalController.MOVIES_ENDPOINT;
import static com.epam.katowice.controller.MovieRentalController.MOVIES_VIEW;
import static com.epam.katowice.controller.MovieRentalController.NEW_MOVIE_ENDPOINT;
import static com.epam.katowice.controller.MovieRentalController.NEW_MOVIE_VIEW;
import static com.epam.katowice.controller.MovieRentalController.PAGE_NO_PARAM;
import static com.epam.katowice.controller.MovieRentalController.REDIRECT_PREFIX;
import static com.epam.katowice.controller.MovieRentalController.SEARCH_ENDPOINT;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.SpecialFeature;
import com.epam.katowice.dto.ActorTo;
import com.epam.katowice.dto.AddFilmForm;
import com.epam.katowice.dto.CategoryTo;
import com.epam.katowice.dto.DetailedFilmTo;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.LanguageTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.Pager;
import com.epam.katowice.dto.SearchFilmForm;
import com.epam.katowice.service.ActorService;
import com.epam.katowice.service.CategoryService;
import com.epam.katowice.service.FilmService;
import com.epam.katowice.service.LanguageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieRentalController.class)
@ActiveProfiles(value = "test")
public class MovieRentalControllerTest {

    private static final long FILMS_NUMBER = 123L;
    private static final String FILM1_TITLE = "Title1", FILM1_DESCRIPTION = "Description1";
    private static final String FILM2_TITLE = "Title2", FILM2_DESCRIPTION = "Description2";
    private static final String FILM3_TITLE = "Title3", FILM3_DESCRIPTION = "Description3";
    private static final int FILM1_RELEASE_YEAR = 2016, FILM2_RELEASE_YEAR = 2014, FILM3_RELEASE_YEAR = 2012;
    private static final short FILM1_LENGTH = 120, FILM2_LENGTH = 126, FILM3_LENGTH = 134;
    private static final int TOTAL_PAGES = 50, START_PAGE = 0, END_PAGE = 10;
    private static final String CATEGORY_NAME_1 = "categoryName1", LANGUAGE_NAME_1 = "languageName1";
    private static final long ID_1 = 1, ID_2 = 2, ID_3 = 3;
    private static final String ACTOR_NAME = "Kowalski", ACTOR_FIRST_NAME_1 = "ActorFirstName1";
    private static final String ACTOR_LAST_NAME_1 = "ActorLastName1";

    private static final int FILM_RELEASE_YEAR_FROM = 2013;
    private static final String PAGE_PARAM_VALUE = "5";
    private static final String SEARCH_FILM_FORM_ATTR = "searchFilmForm", ADD_FILM_FORM_ATTR = "addFilmForm";
    private static final String FILM_ID_PARAM = "id";
    private static final int VALIDATION_ERROR_COUNT = 2;


    @Autowired
    private MockMvc mvc;
    @MockBean
    private FilmService filmService;
    @MockBean
    private LanguageService languageService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ActorService actorService;

    @MockBean
    private PageItemsHolder<FilmTo> pageItemsHolder;

    private LanguageTo languageTo = new LanguageTo();
    private CategoryTo categoryTo = new CategoryTo();
    private List<FilmTo> filmTos = new ArrayList<>();
    private List<CategoryTo> categoryTos = new ArrayList<>();
    private List<LanguageTo> languageTos = new ArrayList<>();
    private List<ActorTo> actorTos = new ArrayList<>();
    private SearchFilmForm searchFilmForm = new SearchFilmForm();

    @Before
    public void setUp() {

        filmTos.add(createFilmTo(ID_1, FILM1_TITLE, FILM1_DESCRIPTION, FILM1_LENGTH, FILM1_RELEASE_YEAR));
        filmTos.add(createFilmTo(ID_2, FILM2_TITLE, FILM2_DESCRIPTION, FILM2_LENGTH, FILM2_RELEASE_YEAR));
        filmTos.add(createFilmTo(ID_3, FILM3_TITLE, FILM3_DESCRIPTION, FILM3_LENGTH, FILM3_RELEASE_YEAR));

        categoryTo.setId(ID_1);
        categoryTo.setName(CATEGORY_NAME_1);
        categoryTos.add(categoryTo);

        languageTo.setId(ID_1);
        languageTo.setName(LANGUAGE_NAME_1);
        languageTos.add(languageTo);

        ActorTo actorTo = new ActorTo();
        actorTo.setId(ID_1);
        actorTo.setFirstName(ACTOR_FIRST_NAME_1);
        actorTo.setLastName(ACTOR_LAST_NAME_1);
        actorTos.add(actorTo);

    }

    private FilmTo createFilmTo(long id, String title, String description, short length, int releaseYear){
        FilmTo filmTo = new FilmTo();
        filmTo.setId(id);
        filmTo.setTitle(title);
        filmTo.setDescription(description);
        filmTo.setLength(length);
        filmTo.setReleaseYear(releaseYear);
        return filmTo;
    }



    @Test
    public void shouldReturnIndexViewWithNumberOfFilms() throws Exception {
        //given
        when(filmService.count()).thenReturn(FILMS_NUMBER);

        //when
        //then
        this.mvc.perform(get(HOME_ENDPOINT))
            .andExpect(status().isOk()).andExpect(view().name(HOME_VIEW))
            .andExpect(model().attribute(MovieRentalController.FILMS_NUMBER_MODEL_ATTR, FILMS_NUMBER));
    }

    @Test
    public void shouldReturnMoviesViewWithListOfFilms() throws Exception {
        //given
        Pager pager = mock(Pager.class);
        when(pageItemsHolder.getPager()).thenReturn(pager);
        when(pager.getTotalPages()).thenReturn(TOTAL_PAGES);
        when(pager.getStart()).thenReturn(START_PAGE);
        when(pager.getEnd()).thenReturn(END_PAGE);
        when(pager.isFirst()).thenReturn(Boolean.TRUE);
        when(pager.isLast()).thenReturn(Boolean.FALSE);
        when(pageItemsHolder.getItems()).thenReturn(filmTos);

        when(filmService.findBySearchFormCriteria(any(SearchFilmForm.class), any(Pageable.class)))
            .thenReturn(pageItemsHolder);
        when(categoryService.listAll()).thenReturn(categoryTos);
        when(languageService.listAll()).thenReturn(languageTos);

        //when
        //then
        this.mvc.perform(get(MOVIES_ENDPOINT))
            .andExpect(status().isOk()).andExpect(view().name(MOVIES_VIEW))
            .andExpect(model().attribute(FILMS_PAGE_MODEL_ATTR, pageItemsHolder))
            .andExpect(model().attribute(ALL_CATEGORIES_MODEL_ATTR, categoryTos))
            .andExpect(model().attribute(ALL_LANGUAGES_MODEL_ATTR, languageTos));
    }

    @Test
    public void shouldRedirectToMoviesEndpointWithListOfFilms() throws Exception {
        //given
        searchFilmForm.setActorName(ACTOR_NAME);
        searchFilmForm.setTitle(FILM1_TITLE);
        searchFilmForm.setReleaseYearFrom(FILM_RELEASE_YEAR_FROM);
        searchFilmForm.setLengthFrom(FILM1_LENGTH);
        //when
        //then
        this.mvc.perform(post(SEARCH_ENDPOINT).param(PAGE_NO_PARAM, PAGE_PARAM_VALUE)
                             .flashAttr(SEARCH_FILM_FORM_ATTR, searchFilmForm))
            .andExpect(view().name(REDIRECT_PREFIX + MOVIES_ENDPOINT))
            .andExpect(flash().attribute(SEARCH_FILM_FORM_ATTR, searchFilmForm))
            .andExpect(model().attribute(PAGE_NO_PARAM, PAGE_PARAM_VALUE));
    }

    @Test
    public void shouldReturnDetailsViewWithFilmDetails() throws Exception {
        //given

        DetailedFilmTo detailedFilmTo = new DetailedFilmTo();

        detailedFilmTo.setId(ID_1);
        detailedFilmTo.setTitle(FILM1_TITLE);
        detailedFilmTo.setDescription(FILM1_DESCRIPTION);
        detailedFilmTo.setLength(FILM1_LENGTH);
        detailedFilmTo.setReleaseYear(FILM1_RELEASE_YEAR);
        detailedFilmTo.setLanguage(languageTo);
        Set<CategoryTo> categories = new HashSet<>();
        categories.add(categoryTo);
        detailedFilmTo.setCategories(categories);

        when(filmService.findDetailsById(ID_1)).thenReturn(detailedFilmTo);

        //when
        //then
        this.mvc.perform(get(DETAILS_ENDPOINT).param(FILM_ID_PARAM, String.valueOf(ID_1)))
            .andExpect(status().isOk()).andExpect(view().name(DETAILS_VIEW))
            .andExpect(model().attribute(MovieRentalController.FILM_MODEL_ATTR, detailedFilmTo));
    }

    @Test
    public void shouldReturnNewMovieViewWithFilmDetails() throws Exception {
        //given
        when(categoryService.listAll()).thenReturn(categoryTos);
        when(languageService.listAll()).thenReturn(languageTos);
        when(actorService.listAll()).thenReturn(actorTos);

        //when
        //then
        this.mvc.perform(get(NEW_MOVIE_ENDPOINT))
            .andExpect(status().isOk()).andExpect(view().name(NEW_MOVIE_VIEW))
            .andExpect(model().attribute(MovieRentalController.ALL_SPECIAL_FEATURES_MODEL_ATTR, SpecialFeature.values()))
            .andExpect(model().attribute(MovieRentalController.ALL_ACTORS_MODEL_ATTR, actorTos))
            .andExpect(model().attribute(MovieRentalController.ALL_RATINGS_MODEL_ATTR, Rating.values()))
            .andExpect(model().attribute(MovieRentalController.ALL_LANGUAGES_MODEL_ATTR, languageTos))
            .andExpect(model().attribute(MovieRentalController.ALL_CATEGORIES_MODEL_ATTR, categoryTos));
    }

    @Test
    public void shouldRedirectToDetailsEndpoint() throws Exception {
        //given
        AddFilmForm addFilmForm = new AddFilmForm();
        addFilmForm.setTitle(FILM1_TITLE);
        addFilmForm.setLength(FILM1_LENGTH);
        addFilmForm.setReleaseYear(FILM1_RELEASE_YEAR);
        addFilmForm.setLanguageId(ID_1);

        FilmTo filmTo = createFilmTo(ID_1, FILM1_TITLE, FILM1_DESCRIPTION, FILM1_LENGTH, FILM1_RELEASE_YEAR);

        when(filmService.addNew(addFilmForm)).thenReturn(filmTo);

        //when
        //then
        this.mvc.perform(post(NEW_MOVIE_ENDPOINT)
                             .flashAttr(ADD_FILM_FORM_ATTR, addFilmForm))
            .andExpect(view().name(REDIRECT_PREFIX + DETAILS_ENDPOINT))
            .andExpect(model().attribute(ID_PARAM, String.valueOf(ID_1)));
    }

    @Test
    public void shouldStayOnNewMovieViewIfNoTitleAndLanguageValidationErrors() throws Exception {
        //given
        //AddFilmForm without Title and/or language is invalid
        AddFilmForm addFilmForm = new AddFilmForm();
        addFilmForm.setLength(FILM1_LENGTH);
        addFilmForm.setReleaseYear(FILM1_RELEASE_YEAR);

        when(categoryService.listAll()).thenReturn(categoryTos);
        when(languageService.listAll()).thenReturn(languageTos);
        when(actorService.listAll()).thenReturn(actorTos);

        //when
        //then
        this.mvc.perform(post(NEW_MOVIE_ENDPOINT)
                             .flashAttr(ADD_FILM_FORM_ATTR, addFilmForm))
            .andExpect(view().name(NEW_MOVIE_VIEW))
            .andExpect(model().hasErrors())
            .andExpect(model().errorCount(VALIDATION_ERROR_COUNT))
            .andExpect(model().attribute(MovieRentalController.ALL_SPECIAL_FEATURES_MODEL_ATTR, SpecialFeature.values()))
            .andExpect(model().attribute(MovieRentalController.ALL_ACTORS_MODEL_ATTR, actorTos))
            .andExpect(model().attribute(MovieRentalController.ALL_RATINGS_MODEL_ATTR, Rating.values()))
            .andExpect(model().attribute(MovieRentalController.ALL_LANGUAGES_MODEL_ATTR, languageTos))
            .andExpect(model().attribute(MovieRentalController.ALL_CATEGORIES_MODEL_ATTR, categoryTos));
    }
}