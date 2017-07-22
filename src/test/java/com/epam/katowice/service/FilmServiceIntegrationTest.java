package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;

import com.epam.katowice.domain.Actor;
import com.epam.katowice.domain.Category;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Language;
import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.SpecialFeature;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.SearchFilmForm;
import com.epam.katowice.repository.ActorRepository;
import com.epam.katowice.repository.CategoryRepository;
import com.epam.katowice.repository.FilmRepository;
import com.epam.katowice.repository.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
public class FilmServiceIntegrationTest {

    private static final long FILMS_NUMBER_3 = 3l;
    private static final String FILM1_TITLE = "Title1", FILM1_DESCRIPTION = "Description1";
    private static final String FILM2_TITLE = "Title2", FILM2_DESCRIPTION = "Description2";
    private static final String FILM3_TITLE = "Title3", FILM3_DESCRIPTION = "Description3";
    private static final int FILM1_RELEASE_YEAR = 2016, FILM2_RELEASE_YEAR = 2014, FILM3_RELEASE_YEAR = 2012;
    private static final short FILM1_LENGTH = 120, FILM2_LENGTH = 126, FILM3_LENGTH = 134;
    private static final String LANGUAGE_NAME = "English";
    private static final int PAGE_NUMBER = 0,  PAGE_SIZE = 20;
    public static final int LANGUAGE_ID_1 = 1, CATEGORY_ID_1 = 1;
    public static final String SEARCHED_TITLE_ELEMENT = "tle", SEARCHED_ACTOR_NAME_ELEMENT = "omba";


    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private FilmService filmService;

    @Before
    public void setUp() {
        filmRepository.deleteAll();
        languageRepository.deleteAll();

        Language language = new Language();
        language.setName(LANGUAGE_NAME);
        language.setTimeStamp(new Date());
        languageRepository.save(language);

        Category category = new Category();
        category.setName("Horror");
        category.setLastUpdate(new Date());

        Category category2 = new Category();
        category2.setName("Comedy");
        category2.setLastUpdate(new Date());

        Category category3 = new Category();
        category3.setName("SF");
        category3.setLastUpdate(new Date());

        categoryRepository.save(category);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Actor actor = new Actor();
        actor.setFirstName("Leonardo");
        actor.setLastName("Caprio");
        actor.setLastUpdate(new Date());

        Actor actor2 = new Actor();
        actor2.setFirstName("Kevin");
        actor2.setLastName("Costner");
        actor2.setLastUpdate(new Date());

        Actor actor3 = new Actor();
        actor3.setFirstName("Kapitan");
        actor3.setLastName("Bomba");
        actor3.setLastUpdate(new Date());

        actorRepository.save(actor);
        actorRepository.save(actor2);
        actorRepository.save(actor3);

        List<SpecialFeature> specialFeatures = Arrays.asList(SpecialFeature.COMMENTARIES, SpecialFeature.DELETED_SCENES,
                                                     SpecialFeature.BEHIND_THE_SCENES);

        Film
            film =
            buildFilm(FILM1_TITLE, FILM1_DESCRIPTION, FILM1_LENGTH, FILM1_RELEASE_YEAR, language, specialFeatures,
                      Rating.G, Arrays.asList(category, category2), Arrays.asList(actor, actor2));
        Film
            film2 =
            buildFilm(FILM2_TITLE, FILM2_DESCRIPTION, FILM2_LENGTH, FILM2_RELEASE_YEAR, language, specialFeatures,
                      Rating.NC17, Arrays.asList(category2, category3), Arrays.asList(actor, actor3));
        Film
            film3 =
            buildFilm(FILM3_TITLE, FILM3_DESCRIPTION, FILM3_LENGTH, FILM3_RELEASE_YEAR, language, specialFeatures,
                      Rating.PG, Arrays.asList(category3, category), Arrays.asList(actor, actor3));

        filmRepository.save(Arrays.asList(film, film2, film3));

    }


    @Test
    public void shouldReturnFilmsNumber() {
        //given
        //when
        long resultFilmNumber = filmService.count();

        //then
        assertThat(resultFilmNumber).isEqualTo(FILMS_NUMBER_3);
    }


    @Test
    public void shouldFindFilmsByAdvancedCriteria() {
        //given
        //when
        PageRequest pageRequest = new PageRequest(PAGE_NUMBER, PAGE_SIZE);
        SearchFilmForm searchFilmForm = new SearchFilmForm();
        searchFilmForm.setTitle(SEARCHED_TITLE_ELEMENT);
        searchFilmForm.setLanguage(LANGUAGE_ID_1);
        searchFilmForm.setCategory(CATEGORY_ID_1);
        searchFilmForm.setActorName(SEARCHED_ACTOR_NAME_ELEMENT);
        PageItemsHolder<FilmTo> pageItemsHolder = filmService.findBySearchFormCriteria(searchFilmForm, pageRequest);

        List<FilmTo> filmTosResult = pageItemsHolder.getItems();
        //then
        assertThat(filmTosResult).isNotNull().hasSize(1);

    }

    private static Film buildFilm(String title, String description, short length, int releaseYear, Language language,
                                  List<SpecialFeature> specialFeatures, Rating rating, List<Category> categories, List<Actor> actors) {
        Film film = new Film();
        film.setTitle(title);
        film.setDescription(description);
        film.setLength(length);
        film.setReleaseYear(releaseYear);
        film.setSpecialFeatures(specialFeatures);
        film.setLanguage(language);
        film.setOriginalLanguage(language);
        film.setRating(rating);
        film.setActors(new HashSet<>(actors));
        film.setCategories(new HashSet<>(categories));
        return film;
    }


}