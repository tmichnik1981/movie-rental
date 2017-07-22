package com.epam.katowice.mapping;

import com.epam.katowice.domain.Film;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public  <TO> TO asDto(Film filmEntity, Class<TO> clazz) {
        return modelMapper.map(filmEntity, clazz);
    }
}
