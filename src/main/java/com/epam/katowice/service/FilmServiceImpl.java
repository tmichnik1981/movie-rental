package com.epam.katowice.service;

import com.epam.katowice.domain.Actor;
import com.epam.katowice.domain.Category;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.AddFilmForm;
import com.epam.katowice.dto.DetailedFilmTo;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.Pager;
import com.epam.katowice.dto.SearchFilmForm;
import com.epam.katowice.mapping.FilmMapper;
import com.epam.katowice.repository.FilmRepository;
import com.epam.katowice.repository.FilmSpecificationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(propagation = Propagation.SUPPORTS,
    readOnly = true)
@Service
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;
    private FilmMapper filmMapper;
    private FilmSpecificationCreator filmSpecificationCreator;

    private LanguageService languageService;
    private CategoryService categoryService;
    private ActorService actorService;

    @Override
    public long count() {
        return filmRepository.count();
    }

    @Override
    public PageItemsHolder<FilmTo> findBySearchFormCriteria(SearchFilmForm searchFilmForm, Pageable pageable) {
        Specification<Film> specification = filmSpecificationCreator.advancedFilmSearch(searchFilmForm);
        Page<Film> filmPage = filmRepository.findAll(specification, pageable);

        List<FilmTo> filmTos =
            filmPage.getContent().stream().map(film -> filmMapper.asDto(film, FilmTo.class))
                .collect(Collectors.toList());

        Pager.PagerBuilder pagerBuilder = Pager.PagerBuilder.getInstance();
        pagerBuilder.withCurrentPage(filmPage.getNumber())
            .withFirst(filmPage.isFirst())
            .withLast(filmPage.isLast())
            .withTotalPages(filmPage.getTotalPages());

        PageItemsHolder<FilmTo> filmToPageItemsHolder =
            new PageItemsHolder<>(filmTos, pagerBuilder.build());

        return filmToPageItemsHolder;
    }

    @Override
    public DetailedFilmTo findDetailsById(long id) {
        Film film = filmRepository.findOne(id);

        if (film == null) {
            throw new IllegalArgumentException("Film with id: " + id + " cannot not be found");
        }

        DetailedFilmTo filmTo = filmMapper.asDto(film, DetailedFilmTo.class);

        return filmTo;
    }

    @Transactional(propagation = Propagation.REQUIRED,
        readOnly = false)
    @Override
    public FilmTo addNew(AddFilmForm addFilmForm) {

        Film filmEntity = new Film();
        filmEntity.setTitle(addFilmForm.getTitle());
        filmEntity.setReleaseYear(addFilmForm.getReleaseYear());
        filmEntity.setDescription(addFilmForm.getDescription());
        filmEntity.setLength(addFilmForm.getLength());
        filmEntity.setRating(addFilmForm.getRating());
        filmEntity.setSpecialFeatures(addFilmForm.getSpecialFeatures());

        Language language = languageService.getOne(addFilmForm.getLanguageId());
        filmEntity.setLanguage(language);

        Set<Actor> actors = addFilmForm.getActorsIds().stream()
            .map(id -> actorService.getOne(id))
            .collect(Collectors.toSet());

        filmEntity.setActors(actors);

        Set<Category> categories = addFilmForm.getCategoriesIds().stream()
            .map(id -> categoryService.getOne(id))
            .collect(Collectors.toSet());
        filmEntity.setCategories(categories);

        filmEntity = filmRepository.save(filmEntity);

        return filmMapper.asDto(filmEntity, FilmTo.class);
    }

    @Autowired
    public void setFilmRepository(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Autowired
    public void setFilmMapper(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    @Autowired
    public void setFilmSpecificationCreator(FilmSpecificationCreator filmSpecificationCreator) {
        this.filmSpecificationCreator = filmSpecificationCreator;
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
