package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.ProductDto;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.ProductService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ProductDto> create(
            @RequestBody ProductDto productDto,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(
                productService.createProduct(
                        productDto,
                        null
                )
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDto>> getByStoreId(
            @PathVariable Long storeId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(
                productService.getProductsByStoreId(
                        storeId,
                        userService.getUserFromJwtToken(jwt)
                )
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> update(
            @PathVariable Long id,
            @RequestBody ProductDto productDto,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        Users user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        productDto,
                        user
                )
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDto>> searchByKeyword(
            @PathVariable Long storeId,
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(
                productService.searchByKeyword(
                        storeId,
                        keyword
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        Users user = userService.getUserFromJwtToken(jwt);

        productService.deleteProduct(
                id,
                user
        );
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Your product has been deleted.");

        return ResponseEntity.ok(
                apiResponse
        );
    }


}
