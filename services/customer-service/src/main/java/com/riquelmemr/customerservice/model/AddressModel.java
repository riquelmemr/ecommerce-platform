package com.riquelmemr.customerservice.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class AddressModel {

    private String name;
    private String street;
    private String number;
    private String zipCode;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;

}
