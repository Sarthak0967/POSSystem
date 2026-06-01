package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDto> getAllBranchesByStoreId(Long storeId);
    BranchDto getBranchById(Long id) throws Exception;
}
