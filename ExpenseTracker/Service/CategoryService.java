package com.example.ExpenseTracker.Service;

import com.example.ExpenseTracker.Modal.Category;
import com.example.ExpenseTracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

}

