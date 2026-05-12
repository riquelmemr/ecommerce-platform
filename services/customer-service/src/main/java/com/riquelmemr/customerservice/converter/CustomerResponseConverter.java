package com.riquelmemr.customerservice.converter;

import com.riquelmemr.customerservice.dto.AddressResponse;
import com.riquelmemr.customerservice.dto.CustomerResponse;
import com.riquelmemr.customerservice.model.AddressModel;
import com.riquelmemr.customerservice.model.CustomerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CustomerResponseConverter implements Converter<CustomerModel, CustomerResponse> {

    private final Converter<AddressModel, AddressResponse> addressResponseConverter;

    @Override
    public CustomerResponse convert(CustomerModel source) {
        if (isNull(source)) {
            return null;
        }

        return new CustomerResponse(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                getAddresses(source)
        );
    }

    private List<AddressResponse> getAddresses(CustomerModel source) {
        if (isNull(source.getAddresses()) || source.getAddresses().isEmpty()) {
            return List.of();
        }

        return source.getAddresses()
                .stream()
                .map(addressResponseConverter::convert)
                .toList();
    }
}
