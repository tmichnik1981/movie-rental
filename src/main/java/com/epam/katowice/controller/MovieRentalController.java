package com.epam.katowice.controller;

import com.epam.katowice.domain.Rating;
import com.epam.katowice.domain.SpecialFeature;
import com.epam.katowice.dto.AddFilmForm;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.SearchFilmForm;
import com.epam.katowice.service.ActorService;
import com.epam.katowice.service.CategoryService;
import com.epam.katowice.service.FilmService;
import com.epam.katowice.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MovieRentalController {

    static final String HOME_VIEW = "index";
    static final String MOVIES_VIEW = "movies";
    static final String DETAILS_VIEW = "details";
    static final String NEW_MOVIE_VIEW = "newmovie";
    static final String LOGIN_VIEW = "login";

    static final String MOVIES_ENDPOINT = "/movies";
    static final String SEARCH_ENDPOINT = "/search";
    static final String HOME_ENDPOINT = "/";
    static final String DETAILS_ENDPOINT = "/details";
    public static final String NEW_MOVIE_ENDPOINT = "/newmovie";
    public static final String LOGIN_ENDPOINT = "/login";


    static final int PAGE_SIZE = 20;
    static final String ID_PARAM = "id";
    static final String PAGE_NO_PARAM = "page";
    static final String FILMS_PAGE_MODEL_ATTR = "filmsPageHolder";
    static final String FILMS_NUMBER_MODEL_ATTR = "filmsNumber";
    static final String ALL_CATEGORIES_MODEL_ATTR = "allCategories";
    static final String ALL_LANGUAGES_MODEL_ATTR = "allLanguages";
    static final String FILM_MODEL_ATTR = "film";
    static final String REDIRECT_PREFIX = "redirect:";
    static final String ALL_RATINGS_MODEL_ATTR = "allRatings";
    static final String ALL_ACTORS_MODEL_ATTR = "allActors";
    static final String ALL_SPECIAL_FEATURES_MODEL_ATTR = "allSpecialFeatures";
    public static final String SORT_BY_PARAM = "sortBy";
    public static final String DIRECTION_PARAM = "direction";


    private FilmService filmService;
    private LanguageService languageService;
    private CategoryService categoryService;
    private ActorService actorService;

    @RequestMapping(value = HOME_ENDPOINT)
    public String home(Model model) {
        model.addAttribute(FILMS_NUMBER_MODEL_ATTR, filmService.count());
        return HOME_VIEW;
    }

    @RequestMapping(value = MOVIES_ENDPOINT)
    public String list(SearchFilmForm searchFilmForm, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "title") String sortBy,
                       @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                       Model model) {

        PageItemsHolder<FilmTo>
            filmToPageItemsHolder =
            filmService.findBySearchFormCriteria(searchFilmForm, new PageRequest(page, PAGE_SIZE, direction, sortBy));

        model.addAttribute(SORT_BY_PARAM, sortBy);
        model.addAttribute(DIRECTION_PARAM, direction);

        model.addAttribute(ALL_LANGUAGES_MODEL_ATTR, languageService.listAll());
        model.addAttribute(ALL_CATEGORIES_MODEL_ATTR, categoryService.listAll());
        model.addAttribute(FILMS_PAGE_MODEL_ATTR, filmToPageItemsHolder);

        return MOVIES_VIEW;
    }


    @RequestMapping(value = SEARCH_ENDPOINT,
        method = RequestMethod.POST)
    public String search(SearchFilmForm searchFilmForm, RedirectAttributes redirectAttributes,
                         HttpServletRequest httpServletRequest) {
        String page = httpServletRequest.getParameter(PAGE_NO_PARAM);
        String sortBy = httpServletRequest.getParameter(SORT_BY_PARAM);
        String direction = httpServletRequest.getParameter(DIRECTION_PARAM);
        redirectAttributes.addAttribute(SORT_BY_PARAM, sortBy);
        redirectAttributes.addAttribute(DIRECTION_PARAM, direction);

        redirectAttributes.addAttribute(PAGE_NO_PARAM, page);
        redirectAttributes.addFlashAttribute(searchFilmForm);
        return REDIRECT_PREFIX + MOVIES_ENDPOINT;
    }

    @RequestMapping(value = DETAILS_ENDPOINT)
    public String showDetails(@RequestParam long id, Model model) {
        FilmTo filmTo = filmService.findDetailsById(id);
        model.addAttribute(FILM_MODEL_ATTR, filmTo);
        return DETAILS_VIEW;
    }

    @RequestMapping(value = NEW_MOVIE_ENDPOINT)
    public String addMovie(AddFilmForm addFilmForm, Model model) {
        setUpAddNewMovieFormModel(model);
        return NEW_MOVIE_VIEW;
    }

    @RequestMapping(value = NEW_MOVIE_ENDPOINT, method = RequestMethod.POST)
    public String addMovie(@Validated AddFilmForm addFilmForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            setUpAddNewMovieFormModel(model);
            return  NEW_MOVIE_VIEW;
        }
        FilmTo filmTo = filmService.addNew(addFilmForm);
        redirectAttributes.addAttribute(ID_PARAM, filmTo.getId());

        return REDIRECT_PREFIX + DETAILS_ENDPOINT;
    }

    @RequestMapping(value = LOGIN_ENDPOINT)
    public String authenticate() {
        return LOGIN_VIEW;
    }

    private void setUpAddNewMovieFormModel(Model model) {
        model.addAttribute(ALL_SPECIAL_FEATURES_MODEL_ATTR, SpecialFeature.values());
        model.addAttribute(ALL_ACTORS_MODEL_ATTR, actorService.listAll());
        model.addAttribute(ALL_RATINGS_MODEL_ATTR, Rating.values());
        model.addAttribute(ALL_LANGUAGES_MODEL_ATTR, languageService.listAll());
        model.addAttribute(ALL_CATEGORIES_MODEL_ATTR, categoryService.listAll());
    }

    @Autowired
    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    @Autowired
    public void setLanguageService(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setActorService(ActorService actorService) {
        this.actorService = actorService;
    }
}
