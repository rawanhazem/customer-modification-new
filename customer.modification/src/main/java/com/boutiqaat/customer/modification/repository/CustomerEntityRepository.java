/************************************************************
 * Copyright ©2015-2024 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/
package com.boutiqaat.customer.modification.repository;

import com.boutiqaat.customer.modification.model.CustomerInfoModel;
import com.boutiqaat.customer.modification.statement.SQLStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author r.elamin
 */

@Repository
@Slf4j
public class CustomerEntityRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<CustomerInfoModel> findAll() {
        return jdbcTemplate.query(SQLStatement.sqlForGetCustomerInfo,
                (rs, rowNum) -> new CustomerInfoModel(
                        rs.getInt("entity_id"),
                        rs.getString("email") == null ? "" : rs.getString("email"),
                        rs.getString("firstname") == null ? "" : rs.getString("firstname"),
                        rs.getString("middlename") == null ? "" : rs.getString("middlename"),
                        rs.getString("lastname") == null ? "" : rs.getString("lastname"),
                        rs.getString("value"),
                        rs.getTimestamp("created_at")
                ));
    }
    
    private Integer generateRandom() {
        long timeStamp = System.currentTimeMillis();
        Random random = new Random(timeStamp);
        return random.nextInt();
    }
    
    
    public String updateAll(){
        jdbcTemplate.batchUpdate();
        var customerEntities = findAll();
        customerEntities.forEach(entity -> {
            String firstName = "test".concat(generateRandom().toString());
            String lastName = "test".concat(String.valueOf((generateRandom() + 1)));
            String email = firstName.concat("_").concat(lastName).concat("@btqTest.com");
            String phoneNumber = "99887766";
            jdbcTemplate.update(SQLStatement.sqlForUpdateCustomerInfo, firstName, lastName, email,
                    entity.getEntityId());
            jdbcTemplate.update(SQLStatement.sqlForUpdatePhoneNumber, phoneNumber, entity.getEntityId());
            jdbcTemplate.update(SQLStatement.sqlForUpdateCustomerAddressEntity,firstName,lastName,phoneNumber,
                    entity.getEntityId());
        });
        return "Success";
    }
    
    private static final int BATCH_SIZE = 500;
    
    public long updateCustomerInfoAtOnce() {
    
        List<CustomerInfoModel> valuesList = findAll();
        
        String firstName = "firstName".concat(String.valueOf(System.currentTimeMillis() / 1000));
        String lastName = "lastName".concat(String.valueOf((System.currentTimeMillis() / 100 )+1));
        String phoneNumber = "99887766";
        
        var proceedCount = 0;
        
        if (CollectionUtils.isEmpty(valuesList)) {
            return proceedCount;
        }
        
        
        for (var index = 0; index < valuesList.size(); index += BATCH_SIZE) {
            log.info("Enter for loop with batch size = {}", BATCH_SIZE);
            final var stopwatch = new StopWatch();
            stopwatch.start();
            
            final var batchList = valuesList.subList(index,
                    Math.min(index + BATCH_SIZE, valuesList.size()));
    
            log.info(SQLStatement.sqlForUpdatingAllCustomerInfoOnce);
            final var results = jdbcTemplate.batchUpdate(SQLStatement.sqlForUpdatingAllCustomerInfoOnce,
                    new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    final var entry = batchList.get(i);
                    log.info("Process entity with id = {}" , entry.getEntityId());
                    ps.setString(1, firstName);
                    ps.setString(2, ".");
                    ps.setString(3, lastName);
                    ps.setString(4, firstName.concat("_").concat(lastName).concat("_")
                            .concat(String.valueOf(entry.getEntityId() * System.currentTimeMillis()/10000)).concat(
                                    "@btq.com"));
                    ps.setString(5, phoneNumber);
                    ps.setString(6, phoneNumber);
                    ps.setLong(7, entry.getEntityId());
                }
                
                @Override
                public int getBatchSize() {
                    return batchList.size();
                }
                
            });
            
            final var updatedCount = Arrays.stream(results).filter(i -> i > 0).count();
            proceedCount += updatedCount;
            
            stopwatch.stop();
            
            log.info("customer-modification update count {} in {} nanos", updatedCount, stopwatch.getDuration());
            
        }
        return proceedCount;
    }
}