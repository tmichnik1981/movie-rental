package com.epam.katowice.repository;


import com.epam.katowice.domain.Actor;
import com.epam.katowice.domain.Category;
import com.epam.katowice.domain.Film;
import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.SearchFilmForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class FilmSpecificationCreator {

    static final String WILD_CARD = "%";

    public Specification<Film> advancedFilmSearch(SearchFilmForm searchFilmForm) {
        return new Specification<Film>() {
            @Override
            public Predicate toPredicate(Root<Film> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (searchFilmForm.getLengthTo() > 0) {
                    predicates.add(criteriaBuilder
                                       .between(root.get(Film.LENGTH), searchFilmForm.getLengthFrom(),
                                                searchFilmForm.getLengthTo()));
                } else {

                    predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(Film.LENGTH), searchFilmForm.getLengthFrom()));
                }

                if (searchFilmForm.getReleaseYearTo() > 0) {
                    predicates.add(criteriaBuilder
                                       .between(root.get(Film.RELEASE_YEAR),
                                                searchFilmForm.getReleaseYearFrom(),
                                                searchFilmForm.getReleaseYearTo()));

                } else {
                    predicates.add(criteriaBuilder
                                       .greaterThanOrEqualTo(root.get(Film.RELEASE_YEAR),
                                                             searchFilmForm.getReleaseYearFrom()));
                }

                if (StringUtils.isNoneBlank(searchFilmForm.getTitle())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Film.TITLE)),
                                                        WILD_CARD + searchFilmForm.getTitle().toUpperCase()
                                                        + WILD_CARD));
                }

                if (searchFilmForm.getLanguage() >= 0) {

                    Join<Film, Language> filmLanguageJoin = root.join(Film.LANGUAGE);

                    predicates
                        .add(criteriaBuilder.equal(filmLanguageJoin.get(Language.ID), searchFilmForm.getLanguage()));
                }

                if (searchFilmForm.getCategory() >= 0) {

                    Join<Film, Category> filmCategoryJoin = root.join(Film.CATEGORIES);

                    predicates
                        .add(criteriaBuilder.equal(filmCategoryJoin.get(Category.ID), searchFilmForm.getCategory()));
                }
                if (StringUtils.isNoneBlank(searchFilmForm.getActorName())) {

                    Join<Film, Actor> filmActorJoin = root.join(Film.ACTORS);

                    predicates.add(criteriaBuilder.or(criteriaBuilder
                                                          .like(criteriaBuilder
                                                                    .upper(filmActorJoin.get(Actor.FIRST_NAME)),
                                                                WILD_CARD + searchFilmForm.getActorName().toUpperCase()
                                                                + WILD_CARD),
                                                      criteriaBuilder
                                                          .like(
                                                              criteriaBuilder.upper(filmActorJoin.get(Actor.LAST_NAME)),
                                                              WILD_CARD + searchFilmForm.getActorName().toUpperCase()
                                                              + WILD_CARD)));
                }

                Predicate[] predicateArray = new Predicate[predicates.size()];

                return criteriaBuilder.and(predicates.toArray(predicateArray));
            }

        };
    }

}
