/************************************************************
 * Copyright ©2015-2024 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/
package com.boutiqaat.customer.modification.model;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author r.elamin
 */

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoModel {
    private Integer entityId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private Timestamp createdAt;
    
}