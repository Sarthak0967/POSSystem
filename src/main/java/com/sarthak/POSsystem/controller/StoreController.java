package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.domain.StoreStatus;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.StoreMapper;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.StoreDto;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.StoreService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<StoreDto> createStore(
            @RequestBody StoreDto storeDto,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        Users user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(
                storeService.createStore(storeDto, user)
        );
    }



    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStore(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByAdmin());
    }

    @GetMapping("/emmployee")
    public ResponseEntity<StoreDto> getStoreByEmployee(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByAdmin());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreDto storeDto
    ) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(
            @PathVariable Long id
    ) throws Exception {
        storeService.deleteStore(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Store deleted successfully.");
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDto> moderateStore(
            @PathVariable Long id,
            @RequestParam StoreStatus status
    ) throws Exception {
        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStore(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Users user = userService.getUserFromJwtToken(jwt);

        return ResponseEntity.ok(storeService.getStoreById(id));
    }
}
