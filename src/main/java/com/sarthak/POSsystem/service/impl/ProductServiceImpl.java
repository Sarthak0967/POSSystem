package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.mapper.ProductMapper;
import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.models.Product;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.ProductDto;
import com.sarthak.POSsystem.repository.CategoryRepository;
import com.sarthak.POSsystem.repository.ProductRepository;
import com.sarthak.POSsystem.repository.StoreRepository;
import com.sarthak.POSsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto, Users user) throws Exception {
        Store store = storeRepository.findById(
                productDto.getStoreId()
        ).orElseThrow(
                () -> new Exception("Store not found")
        );

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new Exception("Category not found")
        );

        Product product = ProductMapper.toEntity(productDto, store, category);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, Users user) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Product not found")
                );






        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setMrp(productDto.getMrp());
        product.setBrand(productDto.getBrand());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setUpdatedAt(productDto.getUpdatedAt());
        product.setImageUrl(productDto.getImageUrl());

        if(productDto.getCategory()!=null) {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                    () -> new Exception("Category not found")
            );
            product.setCategory(category);
        }

        return ProductMapper.toDto(product);

    }

    @Override
    public void deleteProduct(Long id, Users user) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Product not found")
                );

        productRepository.delete(product);

    }

    @Override
    public List<ProductDto> getProductsByStoreId(Long id, Users user) {
        List<Product> products = productRepository.findByStoreId(id);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long id, String keyword) {
        List<Product> products = productRepository.searchByKeyword(id, keyword);
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
