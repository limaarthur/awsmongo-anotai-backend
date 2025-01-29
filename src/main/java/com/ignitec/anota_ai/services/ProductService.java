package com.ignitec.anota_ai.services;

import com.ignitec.anota_ai.dtos.CategoryDto;
import com.ignitec.anota_ai.dtos.MessageDto;
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
    private AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService) {
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.snsService = snsService;
    }

    public Product create(ProductDto productDto) {
        this.categoryService.getById(productDto.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productDto);

        this.productRepository.save(newProduct);

        this.snsService.publishe(new MessageDto(productDto.toString()));

        return newProduct;
    }

    public Product update(String id, ProductDto productDto) {
        Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (productDto.categoryId() != null) {
            this.categoryService.getById(productDto.categoryId()).orElseThrow(CategoryNotFoundException::new);
            product.setCategory(productDto.categoryId());
        }

        if (!productDto.title().isEmpty()) product.setTitle(productDto.title());
        if (!productDto.description().isEmpty()) product.setDescription(productDto.description());
        if (!(productDto.price() == null)) product.setPrice((productDto.price()));

        this.productRepository.save(product);

        this.snsService.publishe(new MessageDto(productDto.toString()));

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
