package com.ignitec.anota_ai.services;

import com.ignitec.anota_ai.dtos.CategoryDto;
import com.ignitec.anota_ai.dtos.ProductDto;
import com.ignitec.anota_ai.entities.Category;
import com.ignitec.anota_ai.entities.Product;
import com.ignitec.anota_ai.exceptions.CategoryNotFoundException;
import com.ignitec.anota_ai.exceptions.ProductNotFoundException;
import com.ignitec.anota_ai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private CategoryService categoryService;
    private ProductRepository productRepository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    public Product create(ProductDto productDto) {
        Category category = this.categoryService.getById(productDto.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productDto);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        return newProduct;
    }

    public Product update(String id, ProductDto productDto) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (productDto.categoryId() != null) {
            this.categoryService.getById(productDto.categoryId()).ifPresent(product::setCategory);
        }

        if (!productDto.title().isEmpty()) product.setTitle(productDto.title());
        if (!productDto.description().isEmpty()) product.setDescription(productDto.description());
        if (!(productDto.price() == null)) product.setPrice((productDto.price()));

        this.productRepository.save(product);
        return product;
    }

    public void delete(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
}
