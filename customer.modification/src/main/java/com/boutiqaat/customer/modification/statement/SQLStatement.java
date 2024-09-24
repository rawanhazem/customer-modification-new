/************************************************************
 * Copyright ©2015-2024 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/
package com.boutiqaat.customer.modification.statement;

/**
 * @author r.elamin
 */


public interface SQLStatement {
    
    String sqlForGetCustomerInfo = "SELECT ce.entity_id, ce.email, ce.firstname, ce.middlename, ce.lastname, " +
            "ce.created_at, cev.value FROM customer_entity ce Inner Join customer_entity_varchar cev " +
            "on ce.entity_id = cev.entity_id where cev.attribute_id = 311 Limit 5000";
    
    String sqlForUpdateCustomerInfo = "UPDATE customer_entity Set firstname = ?, middlename = '.', lastname = ?, " +
            "email = ? where entity_id = ?";
    
    String sqlForUpdatePhoneNumber = "UPDATE customer_entity_varchar Set value = ? where attribute_id = 311 and " +
            "entity_id = ?";
    
    String sqlForUpdateCustomerAddressEntity = "UPDATE customer_address_entity Set firstname = ?, middlename = '.', " +
            "lastname = ?, telephone = ? where parent_id = ?";
    
    String sqlForUpdatingAllCustomerInfoOnce = "UPDATE customer_entity ce " +
            "Left Join boutiqaat_v2.customer_entity_varchar cev on ce.entity_id = cev.entity_id " +
            "Left join boutiqaat_v2.customer_address_entity cae  on ce.entity_id  = cae.parent_id " +
            "Set ce.firstname = ? " +
            ",ce.middlename = ? " +
            ",ce.lastname = ? " +
            ",ce.email = ? " +
            ",cae.telephone = ? " +
            ",cev.value = ? " +
            "where cev.attribute_id = 311 and ce.entity_id = ?";
    
}