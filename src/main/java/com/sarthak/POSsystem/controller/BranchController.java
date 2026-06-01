package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.payload.dto.BranchDto;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) throws UserException {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranch(
            @PathVariable Long id
    ) throws Exception {
        BranchDto createdBranch = branchService.getBranchById(id);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDto>> getAllBranchesByStoreId(
            @PathVariable Long storeId
    ) {
        List<BranchDto> createdBranch = branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDto branchDto
    ) throws Exception {
        BranchDto updatedBranch = branchService.updateBranch(id, branchDto);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranchById(
            @PathVariable Long id
    ) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Branch deleted");
        return ResponseEntity.ok(apiResponse);
    }


}
