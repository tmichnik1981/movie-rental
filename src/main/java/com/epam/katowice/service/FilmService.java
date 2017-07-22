package com.epam.katowice.service;

import com.epam.katowice.dto.AddFilmForm;
import com.epam.katowice.dto.DetailedFilmTo;
import com.epam.katowice.dto.FilmTo;
import com.epam.katowice.dto.PageItemsHolder;
import com.epam.katowice.dto.SearchFilmForm;
import org.springframework.data.domain.Pageable;

public interface FilmService {

    long count();

    PageItemsHolder<FilmTo> findBySearchFormCriteria(SearchFilmForm searchFilmForm, Pageable pageable);

    DetailedFilmTo findDetailsById(long id);

    FilmTo addNew(AddFilmForm addFilmForm);
}
