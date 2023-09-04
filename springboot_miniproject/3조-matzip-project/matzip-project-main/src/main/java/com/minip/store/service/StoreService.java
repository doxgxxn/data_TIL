package com.minip.store.service;

import com.minip.store.data.dto.StoreDto;
import com.minip.store.data.entity.Store;

import java.util.List;

public interface StoreService {
    List<StoreDto> searchStoreByEvery(String every);
    
    List<StoreDto> getStoreDetailByName(String name);

    public List<Store> index();
}
