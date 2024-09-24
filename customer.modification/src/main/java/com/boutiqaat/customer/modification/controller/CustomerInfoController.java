/************************************************************
 * Copyright ©2015-2024 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/
package com.boutiqaat.customer.modification.controller;

import com.boutiqaat.customer.modification.model.CustomerInfoModel;
import com.boutiqaat.customer.modification.service.CustomerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author r.elamin
 */

@RestController
@RequestMapping("customerInfo/")
@Slf4j
public class CustomerInfoController {
    
    @Autowired
    private CustomerInfoService customerInfoService;
    
    @GetMapping("getAll")
    public CustomerInfoModel getAll(){
        return customerInfoService.getAllCustomersInfo();
    }
    
    @PostMapping("updateAll")
    public String updateAll(){
        return customerInfoService.updateCustomerInfo();
    }
    
    @PostMapping("updateAllAtOnce")
    public String updateAllAtOnce(){
        return customerInfoService.updateAllAtOnce();
    }
}