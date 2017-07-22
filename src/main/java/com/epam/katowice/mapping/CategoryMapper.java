package com.epam.katowice.mapping;

import com.epam.katowice.domain.Category;
import com.epam.katowice.dto.CategoryTo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public CategoryTo asDto(Category categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryTo.class);
    }
}
