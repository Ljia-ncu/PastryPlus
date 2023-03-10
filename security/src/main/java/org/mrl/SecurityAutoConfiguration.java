package org.mrl;

import org.mrl.component.JwtTokenManager;
import org.mrl.config.DefaultUserDetails;
import org.mrl.config.SecurityConfiguration;
import org.mrl.filter.JwtAuthenticationFilter;
import org.mrl.property.JwtProperties;
import org.mrl.property.PermittedUrls;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@AutoConfiguration
@EnableWebSecurity
@Import({SecurityConfiguration.class, JwtAuthenticationFilter.class, JwtTokenManager.class})
@EnableConfigurationProperties(value = {JwtProperties.class, PermittedUrls.class})
public class SecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetails() {
        return new DefaultUserDetails();
    }
}
