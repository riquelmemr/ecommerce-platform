package com.platform.storeservice.mapper;

import com.platform.storeservice.dto.request.BaseStoreRequest;
import com.platform.storeservice.dto.response.BaseStoreResponse;
import com.platform.storeservice.model.BaseStoreModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BaseStoreMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "active", source = "isActive")
    BaseStoreModel toModel(BaseStoreRequest request);

    @Mapping(target = "isActive", source = "active")
    BaseStoreResponse toResponse(BaseStoreModel model);
}
