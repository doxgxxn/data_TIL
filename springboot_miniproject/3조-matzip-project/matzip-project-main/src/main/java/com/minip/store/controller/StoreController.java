package com.minip.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

import com.minip.store.service.StoreService;

import lombok.extern.slf4j.Slf4j;

import com.minip.store.data.dto.SharedData;
// import com.minip.store.data.dto.ResponseDto;
import com.minip.store.data.dto.StoreDto;
import com.minip.store.data.repository.StoreRepository;
import com.minip.store.data.entity.Store;

import java.util.List;

@Slf4j
@Controller
public class StoreController {
    @Autowired
    private SharedData sharedData;
    private final StoreService storeService;
    private final StoreRepository storeRepository;
    

    @Autowired
    public StoreController(StoreService storeService, StoreRepository storeRepository) {
        this.storeService = storeService;
        this.storeRepository = storeRepository;
    }

    @GetMapping("/stores/{every}")
    public String show(@PathVariable String every, Model model) {
        String testValue = sharedData.getTest();
        String adminValue = sharedData.getAdmin();
        boolean isAdminValueZero = "1".equals(adminValue);
        boolean isTestValueZero = "1".equals(testValue);

        log.info(adminValue);
        log.info(Boolean.toString(isAdminValueZero));
        model.addAttribute("isAdminValueZero", isAdminValueZero);
        model.addAttribute("isTestValueZero", isTestValueZero);
        
        List<StoreDto> storeEntity = storeService.searchStoreByEvery(every);
        model.addAttribute("storeList", storeEntity);
        
        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "stores/show";
        }
    }
    
    @GetMapping("/stores/detail/{name}")
    public String detail(@PathVariable String name, Model model) {
        String testValue = sharedData.getTest();
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isTestValueZero", isTestValueZero);
        List<StoreDto> storEntity = storeService.getStoreDetailByName(name);
        model.addAttribute("store", storEntity);
        
        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "stores/detail";
        }
    }

    @GetMapping("/stores")
    public String index(Model model) {
        String testValue = sharedData.getTest();
        String adminValue = sharedData.getAdmin();
        boolean isAdminValueZero = "1".equals(adminValue);
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isAdminValueZero", isAdminValueZero);
        model.addAttribute("isTestValueZero", isTestValueZero);

        log.info(adminValue);
        log.info(Boolean.toString(isAdminValueZero));
        List<Store> storeEntityList = (List<Store>)storeRepository.findAll();
        model.addAttribute("storeList", storeEntityList);

        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "stores/show";
        }
    }

    @GetMapping("/stores/map/{site}")
    public String showBySite(@PathVariable String site, Model model) {
        if (site.equals("전체")) {
            site = "서울";
        }
        String testValue = sharedData.getTest();
        boolean isTestValueZero = "1".equals(testValue);
        model.addAttribute("isTestValueZero", isTestValueZero);

        List<StoreDto> storeEntityList = storeService.searchStoreByEvery(site);
        model.addAttribute("storeList", storeEntityList);
        
        if(!isTestValueZero) {
            return "redirect:/login/";
        } else {
            return "stores/map";
        }
    }


}
