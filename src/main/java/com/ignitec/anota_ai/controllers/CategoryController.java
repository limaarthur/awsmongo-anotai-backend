package com.ignitec.anota_ai.controllers;

import com.ignitec.anota_ai.dtos.CategoryDto;
import com.ignitec.anota_ai.entities.Category;
import com.ignitec.anota_ai.services.CategoryService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDto categoryDto) {
        Category newCategory = this.categoryService.create(categoryDto);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = this.categoryService.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDto categoryDto) {
        Category updatedCategory = this.categoryService.update(id, categoryDto);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable("id") String id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
