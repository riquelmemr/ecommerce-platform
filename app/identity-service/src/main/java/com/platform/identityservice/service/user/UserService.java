package com.platform.identityservice.service.user;

import com.platform.identityservice.dto.internal.ValidateCredentialsRequest;
import com.platform.identityservice.dto.internal.UserCredentialsResponse;

public interface UserService {

    UserCredentialsResponse validateCredentials(ValidateCredentialsRequest request);

}
