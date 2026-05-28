package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, Users user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDto, Users user) throws Exception;
    void deleteProduct(Long id, Users user) throws Exception;
    List<ProductDto> getProductsByStoreId(Long id, Users user);
    List<ProductDto> searchByKeyword(Long id, String keyword);
}
