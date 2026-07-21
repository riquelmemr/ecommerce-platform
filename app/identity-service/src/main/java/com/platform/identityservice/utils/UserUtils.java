package com.platform.identityservice.utils;

import com.platform.identityservice.enums.RoleName;

import java.util.List;

public class UserUtils {

    private static final List<String> EMPLOYEE_ROLES = List.of(
            RoleName.STORE_EMPLOYEE.name(),
            RoleName.PLATFORM_ADMIN.name()
    );

    private UserUtils() {}

    public static boolean isPlatformAdmin(String roles) {
        return roles.contains(RoleName.PLATFORM_ADMIN.name());
    }

    public static boolean isNotPlatformAdmin(String roles) {
        return !isPlatformAdmin(roles);
    }

}
