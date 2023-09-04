package com.minip.store.data.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SharedData {    
    private String test = "0";
    private String admin = "0";
}
