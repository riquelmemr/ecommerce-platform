package com.riquelmemr.customerservice.repository;

import com.riquelmemr.customerservice.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerModel, String> {
}
