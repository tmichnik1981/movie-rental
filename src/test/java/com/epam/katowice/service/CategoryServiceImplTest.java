package com.epam.katowice.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.katowice.domain.Category;
import com.epam.katowice.dto.CategoryTo;
import com.epam.katowice.mapping.CategoryMapper;
import com.epam.katowice.repository.CategoryRepository;
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
public class CategoryServiceImplTest {

    private static final long CATEGORY_ID_1 = 1, CATEGORY_ID_2 = 2,
        CATEGORY_ID_3 = 3;
    private static final int EXPECTED_CATEGORIES_RESULT_SIZE = 3;

    private Category category1 = new Category(), category2 = new Category(), category3 = new Category();
    private CategoryTo categoryTo1 = new CategoryTo(), categoryTo2 = new CategoryTo(), categoryTo3 = new CategoryTo();

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();

    @Before
    public void setUp() {
        categoryTo1.setId(CATEGORY_ID_1);
        categoryTo2.setId(CATEGORY_ID_2);
        categoryTo3.setId(CATEGORY_ID_3);

        category1.setId(CATEGORY_ID_1);
        category2.setId(CATEGORY_ID_2);
        category3.setId(CATEGORY_ID_3);
    }

    @Test
    public void shouldFindAllCategories() {
        //given
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2, category3));
        when(categoryMapper.asDto(category1)).thenReturn(categoryTo1);
        when(categoryMapper.asDto(category2)).thenReturn(categoryTo2);
        when(categoryMapper.asDto(category3)).thenReturn(categoryTo3);

        //when
        List<CategoryTo> categoryTosResult = categoryService.listAll();

        //then
        assertThat(categoryTosResult).isNotNull().isNotEmpty().hasSize(EXPECTED_CATEGORIES_RESULT_SIZE)
            .containsExactly(categoryTo1, categoryTo2, categoryTo3);
    }

    @Test
    public void shouldNotFindAnyCategoriesWhenNoCategoriesInRepository() {
        //given
        when(categoryRepository.findAll()).thenReturn(Collections.<Category>emptyList());
        //when
        List<CategoryTo> categoryTosResult = categoryService.listAll();

        //then
        assertThat(categoryTosResult).isEmpty();
    }

    @Test
    public void shouldFindOneCategory() {
        //given
        when(categoryRepository.getOne(CATEGORY_ID_1)).thenReturn(category1);

        //when
        Category categoryToResult = categoryService.getOne(CATEGORY_ID_1);

        //then
        assertThat(categoryToResult).isNotNull().isEqualTo(category1);
    }

    @Test
    public void shouldNotFindCategoryWithGivenIdWhenNotExistsInRepository() {
        //given
        when(categoryRepository.getOne(CATEGORY_ID_1)).thenReturn(null);

        //when
        Category categoryToResult = categoryService.getOne(CATEGORY_ID_1);

        //then
        assertThat(categoryToResult).isNull();
    }
}