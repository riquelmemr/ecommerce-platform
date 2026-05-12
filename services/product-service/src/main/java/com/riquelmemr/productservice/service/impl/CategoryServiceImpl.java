package com.riquelmemr.productservice.service.impl;

import com.riquelmemr.productservice.exception.CategoriesNotFoundException;
import com.riquelmemr.productservice.model.CategoryModel;
import com.riquelmemr.productservice.repository.CategoryRepository;
import com.riquelmemr.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Set<CategoryModel> getByCodes(Set<String> categoryCodes) {
        List<CategoryModel> categories = categoryRepository.findByCodeIn(categoryCodes);

        Set<String> foundCodes = categories.stream()
                .map(CategoryModel::getCode)
                .collect(java.util.stream.Collectors.toSet());

        Set<String> missingCodes = categoryCodes.stream()
                .filter(code -> !foundCodes.contains(code))
                .collect(java.util.stream.Collectors.toSet());

        if (!missingCodes.isEmpty()) {
            throw new CategoriesNotFoundException(missingCodes);
        }

        return new HashSet<>(categories);
    }
}
