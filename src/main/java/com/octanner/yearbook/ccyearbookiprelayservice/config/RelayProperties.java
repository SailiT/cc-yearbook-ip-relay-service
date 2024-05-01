package com.octanner.yearbook.ccyearbookiprelayservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "relay")
public class RelayProperties {
    private String url;
    private String bearerToken;
}
