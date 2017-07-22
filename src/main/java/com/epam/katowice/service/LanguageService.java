package com.epam.katowice.service;

import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.LanguageTo;

import java.util.List;

public interface LanguageService {

    List<LanguageTo> listAll();

    Language getOne(Long id);
}
