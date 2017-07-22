package com.epam.katowice.service;

import com.epam.katowice.domain.Category;
import com.epam.katowice.dto.CategoryTo;

import java.util.List;

public interface CategoryService {

    List<CategoryTo> listAll();

    Category getOne(Long id);
}
