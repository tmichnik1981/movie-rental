package com.epam.katowice.mapping;

import com.epam.katowice.domain.Language;
import com.epam.katowice.dto.LanguageTo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public LanguageTo asDto(Language languageEntity) {
        return modelMapper.map(languageEntity, LanguageTo.class);
    }
}
