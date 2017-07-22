package com.epam.katowice.mapping;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.LanguageTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class LanguageMapperTest {


    private static final int LANGUAGE_ID = 1;
    private static final String LANGUAGE_NAME = "LanguageName1";

    private Language language = new Language();
    private LanguageTo languageTo = new LanguageTo();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LanguageMapper languageMapper = new LanguageMapper();


    @Before
    public void seUp() {
        languageTo.setId(LANGUAGE_ID);
        languageTo.setName(LANGUAGE_NAME);
    }

    @Test
    public void shouldMapLanguageToDto() {

        //given
        when(modelMapper.map(language, LanguageTo.class)).thenReturn(languageTo);

        //when
        LanguageTo languageToResult = languageMapper.asDto(language);

        //then
        assertThat(languageToResult).isNotNull().isEqualTo(languageTo);

    }
}