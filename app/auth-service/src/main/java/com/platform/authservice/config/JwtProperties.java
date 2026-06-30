package com.platform.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private TokenConfig accessToken;
    private TokenConfig refreshToken;

    @Getter
    @Setter
    public static class TokenConfig {
        private long expiration;
    }
}
