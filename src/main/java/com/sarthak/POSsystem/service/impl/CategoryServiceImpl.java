package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.domain.UserRole;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.CategoryMapper;
import com.sarthak.POSsystem.models.Category;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.CategoryDto;
import com.sarthak.POSsystem.repository.CategoryRepository;
import com.sarthak.POSsystem.repository.StoreRepository;
import com.sarthak.POSsystem.service.CategoryService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
//    private final CategoryService categoryService;
    private final UserService userService;
    private final StoreRepository storeRepository;

    @Override
    public CategoryDto createCategory(CategoryDto dto) throws Exception {
        Users user = userService.getCurrentUser();

        Store store = storeRepository.findById(dto.getId()).orElseThrow(
                () -> new Exception("Store not found")
        );



        Category category = Category.builder()

                .store(store)
                .name(dto.getName())
                .build();

        checkAuthority(user, category.getStore());

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto((category));

    }

    @Override
    public List<CategoryDto> getCategoriesByStore(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream()
                .map(
                        CategoryMapper::toDto
                ).collect(Collectors.toList());
    }


    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Category not found")
                );
        Users user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());

        category.setName(dto.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Category not found")
                );
        Users user = userService.getCurrentUser();

        checkAuthority(user, category.getStore());

        categoryRepository.delete(category);
    }

    private void checkAuthority(Users user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        boolean adminOfSameStore = isAdmin && isSameStore;

        if(!(isManager || adminOfSameStore)) {
            throw new Exception("You don't have permissions to manage this category.");
        }

    }
}
