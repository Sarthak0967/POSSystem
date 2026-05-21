package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
