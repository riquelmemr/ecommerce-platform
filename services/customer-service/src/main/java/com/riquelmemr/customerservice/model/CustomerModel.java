package com.riquelmemr.customerservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@Document(collection = "tb_customer")
public class CustomerModel {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressModel> addresses;

}
