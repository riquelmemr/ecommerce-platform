package com.riquelmemr.customerservice.converter;

import com.riquelmemr.customerservice.dto.AddressRequest;
import com.riquelmemr.customerservice.dto.CustomerRequest;
import com.riquelmemr.customerservice.model.AddressModel;
import com.riquelmemr.customerservice.model.CustomerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CustomerModelConverter implements Converter<CustomerRequest, CustomerModel> {

    private final Converter<AddressRequest, AddressModel> addressModelConverter;

    @Override
    public CustomerModel convert(CustomerRequest source) {
        if (isNull(source)) {
            return null;
        }

        return CustomerModel.builder()
                .withId(source.id())
                .withFirstName(source.firstName())
                .withFirstName(source.lastName())
                .withEmail(source.email())
                .withAddresses(getAddresses(source))
                .build();
    }

    private List<AddressModel> getAddresses(CustomerRequest source) {
        if (isNull(source.addresses()) || source.addresses().isEmpty()) {
            return List.of();
        }

        return source.addresses()
                .stream()
                .map(addressModelConverter::convert)
                .toList();
    }
}
