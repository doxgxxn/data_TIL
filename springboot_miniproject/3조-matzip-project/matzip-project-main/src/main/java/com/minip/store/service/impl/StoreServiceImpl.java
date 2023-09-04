package com.minip.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minip.store.data.dto.StoreDto;
import com.minip.store.data.entity.Store;
import com.minip.store.data.repository.StoreRepository;
import com.minip.store.service.StoreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService{
    private StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    @Override
    public List<StoreDto> searchStoreByEvery(String every) {
        List<Store> stores = storeRepository.queryByEvery(every);
        return stores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<Store> index() {
        return storeRepository.findAll();
    }
    
    @Override
    public List<StoreDto> getStoreDetailByName(String name) {
        List<Store> stores = storeRepository.queryByName(name);
        return stores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private StoreDto convertToDTO(Store store){
        StoreDto dto = new StoreDto();
        dto.setName(store.getName());
        dto.setLocation(store.getLocation());
        dto.setPhone(store.getPhone());
        dto.setType(store.getType());
        dto.setLatitude(store.getLatitude());
        dto.setLongitude(store.getLongitude());
        dto.setSite(store.getSite());
        return dto;
    }
    
    // @Override
    // public List<StoreDto> getStoreByStoreCodeRange(String code, String from_to, String end_to) {
    //     List<Store> stores = storeRepository.queryByStoreCodeAndDateAndDate(code, from_to, end_to);

    //     return stores.stream().map(this::convertToDTO).collect(Collectors.toList());
    // }


}