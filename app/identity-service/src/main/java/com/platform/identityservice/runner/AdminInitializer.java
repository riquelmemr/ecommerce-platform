package com.platform.identityservice.runner;

import com.platform.identityservice.config.AdminConfig;
import com.platform.identityservice.enums.RoleName;
import com.platform.identityservice.model.EmployeeModel;
import com.platform.identityservice.model.RoleModel;
import com.platform.identityservice.repository.EmployeeRepository;
import com.platform.identityservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AdminConfig.class)
public class AdminInitializer implements ApplicationRunner {

    private final AdminConfig adminConfig;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<EmployeeModel> adminOpt = employeeRepository.findByEmail(adminConfig.getEmail());

        if (adminOpt.isPresent()) {
            log.info("Platform admin already exists, skipping creation");
            return;
        }

        RoleModel role = roleRepository.findByName(RoleName.PLATFORM_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role PLATFORM_ADMIN not found"));

        EmployeeModel admin = new EmployeeModel();

        admin.setEmail(adminConfig.getEmail());
        admin.setPassword(passwordEncoder.encode(adminConfig.getPassword()));
        admin.setName("Platform Admin");
        admin.setActive(true);
        admin.setEmployee(true);
        admin.setRoles(Set.of(role));

        employeeRepository.save(admin);
        log.info("Platform admin created successfully");
    }
}
