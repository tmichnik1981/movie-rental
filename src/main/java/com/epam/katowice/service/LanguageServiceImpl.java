package com.epam.katowice.service;

import com.epam.katowice.MovieRentalApplication;
import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.LanguageTo;
import com.epam.katowice.mapping.LanguageMapper;
import com.epam.katowice.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(propagation = Propagation.SUPPORTS,
    readOnly = true)
@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;
    private LanguageMapper languageMapper;

    @Cacheable(value = MovieRentalApplication.LANGUAGES_CACHE)
    @Override
    public List<LanguageTo> listAll() {
        List<LanguageTo> languageTos =
            languageRepository.findAll().stream().map(category -> languageMapper.asDto(category)).collect(
                Collectors.toList());
        return languageTos;
    }


    @Override
    public Language getOne(Long id) {
        return languageRepository.getOne(id);
    }


    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Autowired
    public void setLanguageMapper(LanguageMapper languageMapper) {
        this.languageMapper = languageMapper;
    }
}
