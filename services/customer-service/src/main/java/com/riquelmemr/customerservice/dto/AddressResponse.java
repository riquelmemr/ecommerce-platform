package com.riquelmemr.customerservice.dto;

public record AddressResponse(
        String street,
        String number,
        String zipCode,
        String neighborhood,
        String city,
        String state
) {}
