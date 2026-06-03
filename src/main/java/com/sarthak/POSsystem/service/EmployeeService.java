package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.domain.UserRole;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface EmployeeService {

    UserDto createStoreEmployee(UserDto employee, Long storeId) throws  Exception;
    UserDto createBranchEmployee(UserDto employee, Long branchId) throws  Exception;
    Users updateEmployee(UserDto userDto, Long employeeId) throws Exception;
    void deleteEmployee(Long employeeId) throws Exception;
    List<UserDto> findStoreEmployees(Long storeId, UserRole role) throws Exception;
    List<UserDto> findBranchEmployees(Long branchId, UserRole role) throws Exception;



}
