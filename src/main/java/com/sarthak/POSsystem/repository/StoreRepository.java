package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.payload.dto.StoreDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByStoreAdminId(Long adminId);
}
