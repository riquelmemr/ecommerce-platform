package com.riquelmemr.customerservice.dto;

import java.util.List;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        List<AddressResponse> addresses
) {}
