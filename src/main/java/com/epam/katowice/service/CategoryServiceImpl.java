package com.epam.katowice.service;

import com.epam.katowice.MovieRentalApplication;
import com.epam.katowice.domain.Category;
import com.epam.katowice.dto.CategoryTo;
import com.epam.katowice.mapping.CategoryMapper;
import com.epam.katowice.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Cacheable(value = MovieRentalApplication.CATEGORIES_CACHE)
    @Override
    public List<CategoryTo> listAll() {

        List<CategoryTo> categoryTos =
            categoryRepository.findAll().stream().map(category -> categoryMapper.asDto(category)).collect(
                Collectors.toList());

        return categoryTos;
    }

    @Override
    public Category getOne(Long id) {
        return categoryRepository.getOne(id);
    }

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
}
