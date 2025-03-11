package org.example.blog.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.blog.domain.entities.Category;
import org.example.blog.repositories.CategoryRepository;
import org.example.blog.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }


    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        categoryRepository.findById(id).ifPresent(category -> {
            if (!category.getPosts().isEmpty()) {
                throw new IllegalStateException("Category has associated posts");
            }
            categoryRepository.deleteById(id);
        });
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " does not exist"));
    }
}
