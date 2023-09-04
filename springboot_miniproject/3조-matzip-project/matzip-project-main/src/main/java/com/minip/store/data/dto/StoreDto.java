package com.minip.store.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.minip.store.data.entity.Store;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {
    private long id;
    private String name;
    private String location;
    private String phone;
    private String type;
    private double latitude;
    private double longitude;
    private String site;

    public Store toEntity() {
        return new Store(id, name, location, phone, type, latitude, longitude, site);
    }
}
