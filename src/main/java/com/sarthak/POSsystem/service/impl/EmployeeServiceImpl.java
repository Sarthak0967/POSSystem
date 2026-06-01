package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.domain.UserRole;
import com.sarthak.POSsystem.mapper.UserMapper;
import com.sarthak.POSsystem.models.Branch;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.repository.BranchRepository;
import com.sarthak.POSsystem.repository.StoreRepository;
import com.sarthak.POSsystem.repository.UserRepository;
import com.sarthak.POSsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store not found")
        );

        Branch branch =null;
        if(employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            if(employee.getBranchId() == null) {
                throw new Exception("BranchId is required to create Branch manager");
            }
            branch = branchRepository.findById(employee.getBranchId()).orElseThrow(
                    () -> new Exception("Branch not found")
            );
        }

        Users user = UserMapper.toEntity(employee);

        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        Users savedEmployee = userRepository.save(user);

        if(employee.getRole() == UserRole.ROLE_BRANCH_MANAGER && branch != null) {
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }


        return UserMapper.toDTO(user);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch not found")
        );

        if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER || employee.getRole()==UserRole.ROLE_BRANCH_MANAGER) {

            Users user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));

        }
        throw new Exception("Branch role not supported");
    }

    @Override
    public Users updateEmployee(UserDto userDto, Long employeeId) throws Exception {
        Users existingEmployee = userRepository.findById(employeeId).orElseThrow(
                () -> new Exception("Employee not exist")
        );

        Branch branch = branchRepository.findById(userDto.getBranchId()).orElseThrow(
                () -> new Exception("Branch not exist")
        );

        existingEmployee.setEmail(userDto.getEmail());
        existingEmployee.setUsername(userDto.getUsername());
        existingEmployee.setPassword(userDto.getPassword());
        existingEmployee.setRole(userDto.getRole());
        existingEmployee.setBranch(branch);
        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        Users employee = userRepository.findById(employeeId).orElseThrow(
                () -> new Exception("User not found")
        );
        userRepository.delete(employee);

    }

    @Override
    public List<Users> findStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store not found")
        );


        return userRepository.findByStore(store).stream()
                .filter(
                        user -> role == null || user.getRole() == role
                ).collect(Collectors.toList());
    }

    @Override
    public List<Users> findBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch not exist")
        );


        return userRepository.findByBranchId(branchId).stream()
                .filter(
                        user -> role == null || user.getRole() == role
                ).collect(Collectors.toList());
    }
}
