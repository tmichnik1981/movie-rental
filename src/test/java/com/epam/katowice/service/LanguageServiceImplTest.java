package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.LanguageTo;
import com.epam.katowice.mapping.LanguageMapper;
import com.epam.katowice.repository.LanguageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LanguageServiceImplTest {

    private static final long LANGUAGE_ID_1 = 1, LANGUAGE_ID_2 = 2;
    private static final long LANGUAGE_ID_3 = 3;
    private static final int EXPECTED_LANGUAGES_RESULT_SIZE = 3;

    private Language language1 = new Language(), language2 = new Language(), language3 = new Language();
    private LanguageTo languageTo1 = new LanguageTo(), languageTo2 = new LanguageTo(), languageTo3 = new LanguageTo();

    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private LanguageMapper languageMapper;

    @InjectMocks
    private LanguageService languageService = new LanguageServiceImpl();

    @Before
    public void setUp() {
        languageTo1.setId(LANGUAGE_ID_1);
        languageTo2.setId(LANGUAGE_ID_2);
        languageTo3.setId(LANGUAGE_ID_3);

        language1.setId(LANGUAGE_ID_1);
        language2.setId(LANGUAGE_ID_2);
        language3.setId(LANGUAGE_ID_3);
    }

    @Test
    public void shouldFindAllLanguages() {
        //given
        when(languageRepository.findAll()).thenReturn(Arrays.asList(language1, language2, language3));

        when(languageMapper.asDto(language1)).thenReturn(languageTo1);
        when(languageMapper.asDto(language2)).thenReturn(languageTo2);
        when(languageMapper.asDto(language3)).thenReturn(languageTo3);

        //when
        List<LanguageTo> languageTosResult = languageService.listAll();

        //then
        assertThat(languageTosResult).isNotNull().isNotEmpty().hasSize(EXPECTED_LANGUAGES_RESULT_SIZE)
            .containsExactly(languageTo1, languageTo2, languageTo3);
    }

    @Test
    public void shouldNotFindAnyLanguagesWhenNoLanguagesInRepository() {
        //given
        when(languageRepository.findAll()).thenReturn(Collections.<Language>emptyList());

        //when
        List<LanguageTo> languageTosResult = languageService.listAll();

        //then
        assertThat(languageTosResult).isNotNull().isEmpty();
    }

    @Test
    public void shouldFindOneLanguage() {

        //given
        when(languageRepository.getOne(LANGUAGE_ID_1)).thenReturn(language1);

        //when
        Language languageResult = languageService.getOne(LANGUAGE_ID_1);

        //then
        assertThat(languageResult).isNotNull().isEqualTo(languageResult);
    }

    @Test
    public void shouldNotFindLanguageWithGivenIdWhenNotExistsInRepository() {

        //given
        when(languageRepository.getOne(LANGUAGE_ID_1)).thenReturn(null);

        //when
        Language languageResult = languageService.getOne(LANGUAGE_ID_1);

        //then
        assertThat(languageResult).isNull();
    }

}