package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.payload.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStoreId(Long storeId);
}
