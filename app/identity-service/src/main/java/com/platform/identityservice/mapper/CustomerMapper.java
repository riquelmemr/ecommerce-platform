package com.platform.identityservice.mapper;

import com.platform.identityservice.dto.customer.CustomerRequest;
import com.platform.identityservice.dto.customer.CustomerResponse;
import com.platform.identityservice.model.CustomerModel;
import com.platform.identityservice.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "roles", expression = "java(mapRoles(customer.getRoles()))")
    CustomerResponse toResponse(CustomerModel customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    CustomerModel toEntity(CustomerRequest request);

    default Set<String> mapRoles(Set<RoleModel> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
