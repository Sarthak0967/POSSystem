package com.sarthak.POSsystem.service.impl;

import com.sarthak.POSsystem.domain.StoreStatus;
import com.sarthak.POSsystem.exceptions.UserException;
import com.sarthak.POSsystem.mapper.StoreMapper;
import com.sarthak.POSsystem.models.Store;
import com.sarthak.POSsystem.models.StoreContact;
import com.sarthak.POSsystem.models.Users;
import com.sarthak.POSsystem.payload.dto.StoreDto;
import com.sarthak.POSsystem.repository.StoreRepository;
import com.sarthak.POSsystem.service.StoreService;
import com.sarthak.POSsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDto createStore(StoreDto storeDto, Users user) {
        Store store = StoreMapper.toEntity(storeDto, user);

        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) throws Exception {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found")
        );

        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> dtos = storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public StoreDto getStoreByAdmin() throws UserException {

        Users admin = userService.getCurrentUser();

        Store store = storeRepository.findByStoreAdminId(admin.getId());

        if(store == null){
            throw new UserException("Store not found");
        }

        return StoreMapper.toDto(store);
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {
        Users currentUser = userService.getCurrentUser();

        Store existingStore = storeRepository.findByStoreAdminId(currentUser.getId());

        if(existingStore == null ) {
            throw new Exception("Store not found");
        }

        existingStore.setBrand(storeDto.getBrand());
        existingStore.setDescription(storeDto.getDescription());


        if(storeDto.getStoreType()!=null) {
            existingStore.setStoreType(storeDto.getStoreType());
        }

        if(storeDto.getContact()!=null) {
            StoreContact contact = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            existingStore.setContact(contact);
        }

        Store updatedStore = storeRepository.save(existingStore);
        return StoreMapper.toDto(updatedStore);
    }


    @Override
    public void deleteStore(Long id) throws UserException {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new UserException("Store not found"));

        storeRepository.delete(store);
    }

    @Override
    public StoreDto getStoreByEmployee() throws Exception {
        Users currentUser = userService.getCurrentUser();

        if(currentUser == null) {
            throw  new Exception("You don't have permission to access store.");
        }

        return StoreMapper.toDto(currentUser.getStore());
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) throws Exception {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found")
        );

        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDto(updatedStore);
    }
}
