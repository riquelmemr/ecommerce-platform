package com.riquelmemr.customerservice.converter;

import com.riquelmemr.customerservice.dto.AddressResponse;
import com.riquelmemr.customerservice.model.AddressModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AddressResponseConverter implements Converter<AddressModel, AddressResponse> {

    @Override
    public AddressResponse convert(AddressModel source) {
        if (isNull(source)) {
            return null;
        }

        return new AddressResponse(
                source.getStreet(),
                source.getNumber(),
                source.getZipCode(),
                source.getNeighborhood(),
                source.getCity(),
                source.getState()
        );
    }
}
