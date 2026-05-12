package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.model.CategoryModel;

import java.util.Set;

public interface CategoryService {

    Set<CategoryModel> getByCodes(Set<String> categoryCodes);

}
