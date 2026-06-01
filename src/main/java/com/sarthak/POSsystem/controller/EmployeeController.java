package com.sarthak.POSsystem.controller;

import com.sarthak.POSsystem.domain.UserRole;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.UserDto;
import com.sarthak.POSsystem.payload.response.ApiResponse;
import com.sarthak.POSsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto> createStoreEmployee(
            @RequestBody UserDto userDto,
            @PathVariable Long storeId

    ) throws Exception {
        UserDto employee = employeeService.createStoreEmployee(userDto, storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto> createBranchEmployee(
            @PathVariable Long branchId,
            @RequestBody UserDto userDto
            ) throws Exception {
        UserDto employee = employeeService.createBranchEmployee(userDto, branchId);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateEmployee(
            @PathVariable Long id,
            @RequestBody UserDto userDto
    ) throws Exception {
        Users employee = employeeService.updateEmployee(userDto ,id);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(
            @PathVariable Long id
    ) throws Exception {
        employeeService.deleteEmployee(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deleted employee");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Users>> storeEmployee(
            @PathVariable Long storeId,
            @RequestParam(required = false) UserRole role
    ) throws Exception {
        List<Users> employees = employeeService.findStoreEmployees(storeId, role);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Users>> branchEmployee(
            @PathVariable Long branchId,
            @RequestParam(required = false) UserRole role
    ) throws Exception {
        List<Users> employees = employeeService.findBranchEmployees(branchId, role);

        return ResponseEntity.ok(employees);
    }
}
