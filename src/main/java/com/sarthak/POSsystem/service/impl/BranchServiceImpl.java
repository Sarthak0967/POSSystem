package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.BranchMapper;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.BranchDto;
import com.sarthak.POSsystem.repository.BranchRepository;
import com.sarthak.POSsystem.repository.StoreRepository;
import com.sarthak.POSsystem.repository.UserRepository;
import com.sarthak.POSsystem.service.BranchService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {
        Users currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto, store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exist")
        );

        existing.setName(branchDto.getName());
        existing.setWorkingDays(branchDto.getWorkingDays());
        existing.setEmail(branchDto.getEmail());
        existing.setPhone(branchDto.getPhone());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setAddress(branchDto.getAddress());
        existing.setClosingTime(branchDto.getClosingTime());
        existing.setOpeningTime(branchDto.getOpeningTime());

        Branch updatedBranch = branchRepository.save(existing);
        return BranchMapper.toDto(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exist")
        );

        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exist")
        );
        return BranchMapper.toDto(existing);
    }
}
