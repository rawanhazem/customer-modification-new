/************************************************************
 * Copyright ©2015-2024 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/
package com.boutiqaat.customer.modification.service;

import com.boutiqaat.customer.modification.model.CustomerInfoModel;
import com.boutiqaat.customer.modification.repository.CustomerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author r.elamin
 */

@Service
public class CustomerInfoService {
    
    @Autowired
    private CustomerEntityRepository customerEntityRepository;
    
    public CustomerInfoModel getAllCustomersInfo(){
        return customerEntityRepository.findAll().stream().findAny().get();
    }
    
    public String updateCustomerInfo(){
        return customerEntityRepository.updateAll();
    }
    
    public String updateAllAtOnce(){
        return "The updated rows count = " + customerEntityRepository.updateCustomerInfoAtOnce();
    }
}