package com.riquelmemr.productservice.service.impl;

import com.riquelmemr.productservice.dto.CategoryRequest;
import com.riquelmemr.productservice.dto.CategoryResponse;
import com.riquelmemr.productservice.dto.CategoryUpdateRequest;
import com.riquelmemr.productservice.exception.CategoryCodeAlreadyExistsException;
import com.riquelmemr.productservice.exception.CategoryNotFoundException;
import com.riquelmemr.productservice.exception.CategoriesNotFoundException;
import com.riquelmemr.productservice.model.CategoryModel;
import com.riquelmemr.productservice.repository.CategoryRepository;
import com.riquelmemr.productservice.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Converter<CategoryRequest, CategoryModel> categoryModelConverter;
    private final Converter<CategoryModel, CategoryResponse> categoryResponseConverter;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        validateUniqueCode(request.code(), null);

        CategoryModel category = categoryModelConverter.convert(request);
        CategoryModel createdCategory = categoryRepository.save(category);

        return categoryResponseConverter.convert(createdCategory);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryResponseConverter::convert)
                .toList();
    }

    @Override
    public CategoryResponse findById(Long id) {
        CategoryModel category = findCategoryById(id);
        return categoryResponseConverter.convert(category);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        CategoryModel category = findCategoryById(id);

        mergeCategory(category, request);
        categoryRepository.save(category);

        return categoryResponseConverter.convert(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CategoryModel category = findCategoryById(id);
        categoryRepository.delete(category);
    }

    @Override
    public Set<CategoryModel> getByCodes(Set<String> categoryCodes) {
        List<CategoryModel> categories = categoryRepository.findByCodeIn(categoryCodes);

        Set<String> foundCodes = categories.stream()
                .map(CategoryModel::getCode)
                .collect(Collectors.toSet());

        Set<String> missingCodes = categoryCodes.stream()
                .filter(code -> !foundCodes.contains(code))
                .collect(Collectors.toSet());

        if (!missingCodes.isEmpty()) {
            throw new CategoriesNotFoundException(missingCodes);
        }

        return new HashSet<>(categories);
    }

    private CategoryModel findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    private void mergeCategory(CategoryModel category, CategoryUpdateRequest request) {
        if (nonNull(request.code())) {
            validateUniqueCode(request.code(), category.getId());
            category.setCode(request.code());
        }

        if (nonNull(request.name())) {
            category.setName(request.name());
        }

        if (nonNull(request.description())) {
            category.setDescription(request.description());
        }
    }

    private void validateUniqueCode(String code, Long categoryId) {
        categoryRepository.findByCode(code)
                .filter(existingCategory -> isAnotherCategory(categoryId, existingCategory))
                .ifPresent(category -> {
                    throw new CategoryCodeAlreadyExistsException(category.getCode());
                });
    }

    private boolean isAnotherCategory(Long categoryId, CategoryModel existingCategory) {
        return !existingCategory.getId().equals(categoryId);
    }
}
