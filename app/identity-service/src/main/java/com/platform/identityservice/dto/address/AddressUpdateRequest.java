package com.platform.identityservice.dto.address;

import java.util.UUID;

public record AddressUpdateRequest(
        UUID storeId,
        String name,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode,
        String country,
        Boolean isDefault
) {
}
