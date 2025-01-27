package com.ignitec.anota_ai.services;

import com.ignitec.anota_ai.dtos.CategoryDto;
import com.ignitec.anota_ai.entities.Category;
import com.ignitec.anota_ai.exceptions.CategoryNotFoundException;
import com.ignitec.anota_ai.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CategoryDto categoryDto) {
        Category newCategory = new Category(categoryDto);
        this.categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category update(String id, CategoryDto categoryDto) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if (!categoryDto.title().isEmpty()) category.setTitle(categoryDto.title());
        if (!categoryDto.description().isEmpty()) category.setDescription(categoryDto.description());
        this.categoryRepository.save(category);
        return category;
    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.categoryRepository.delete(category);
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> getById(String id) {
        return this.categoryRepository.findById(id);
    }
}
