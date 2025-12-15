package fmi.sports.tournament.organizer.backend.configs;

import fmi.sports.tournament.organizer.backend.filters.AuthFilter;
import fmi.sports.tournament.organizer.backend.services.JWTService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class FilterConfig {
    private static final String[] URL_PATTERNS =
            {"/api/*"};

    private final JWTService jwtService;

    public FilterConfig(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean =
                new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(jwtService));
        registrationBean.setUrlPatterns(Arrays.asList(URL_PATTERNS));
        return registrationBean;
    }
}
