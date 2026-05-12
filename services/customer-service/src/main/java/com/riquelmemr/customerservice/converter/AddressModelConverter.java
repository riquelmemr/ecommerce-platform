package com.riquelmemr.customerservice.converter;

import com.riquelmemr.customerservice.dto.AddressRequest;
import com.riquelmemr.customerservice.model.AddressModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AddressModelConverter implements Converter<AddressRequest, AddressModel> {

    @Override
    public AddressModel convert(AddressRequest source) {
        if (isNull(source)) {
            return null;
        }

        return AddressModel.builder()
                .name(source.name())
                .street(source.street())
                .number(source.number())
                .zipCode(source.zipCode())
                .neighborhood(source.neighborhood())
                .city(source.city())
                .state(source.state())
                .build();
    }
}
