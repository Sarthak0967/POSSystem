package com.sarthak.POSsystem.mapper;

import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.payload.dto.BranchDto;

import java.time.LocalDateTime;

public class BranchMapper {

    public static BranchDto toDto(Branch branch) {
        return BranchDto.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .closingTime(branch.getClosingTime())
                .openingTime(branch.getOpeningTime())
                .workingDays(branch.getWorkingDays())
                .storeId(branch.getStore()!= null ? branch.getStore().getId() : null)
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }

    public static Branch toEntity(BranchDto branchDto, Store store) {
        return Branch.builder()
                .name(branchDto.getName())
                .address(branchDto.getAddress())
                .store(store)
                .email(branchDto.getEmail())
                .phone(branchDto.getPhone())
                .openingTime(branchDto.getOpeningTime())
                .closingTime(branchDto.getClosingTime())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .workingDays(branchDto.getWorkingDays())
                .build();
    }
}
