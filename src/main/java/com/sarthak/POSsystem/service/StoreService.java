package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.domain.StoreStatus;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {

    StoreDto createStore(StoreDto storeDto, Users user);
    StoreDto getStoreById(Long id) throws Exception;
    List<StoreDto> getAllStores();
    StoreDto getStoreByAdmin() throws UserException;
    StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
    void deleteStore(Long id) throws UserException;
    StoreDto getStoreByEmployee() throws Exception;

    StoreDto moderateStore(Long id, StoreStatus status) throws Exception;

}
