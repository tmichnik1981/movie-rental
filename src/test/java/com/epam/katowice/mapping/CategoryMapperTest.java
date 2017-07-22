package com.epam.katowice.mapping;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Category;
import com.epam.katowice.dto.CategoryTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class CategoryMapperTest {


    private Category category = new Category();
    private CategoryTo categoryTo = new CategoryTo();

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryMapper categoryMapper = new CategoryMapper();

    @Before
    public void setUp() {
        categoryTo.setId(1);
        categoryTo.setName("CategoryName1");
    }

    @Test
    public void shouldMapCategoryToDto() {
        //given
        when(modelMapper.map(category, CategoryTo.class)).thenReturn(categoryTo);

        //when
        CategoryTo categoryToResult = categoryMapper.asDto(category);

        //then
        assertThat(categoryToResult).isNotNull().isEqualTo(categoryTo);
    }


}