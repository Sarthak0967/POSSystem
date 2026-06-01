package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
    List<Users> findByStore(Store store);
    List<Users> findByBranchId(Long branchId);
}
