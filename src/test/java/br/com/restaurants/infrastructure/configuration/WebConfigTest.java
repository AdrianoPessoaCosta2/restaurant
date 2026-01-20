package br.com.restaurants.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebConfigTest {

    @InjectMocks
    private WebConfig webConfig;

    @Mock
    private AuthInterceptor authInterceptor;

    @Mock
    private InterceptorRegistry registry;

    @Mock
    private InterceptorRegistration registration;

    @Test
    void shouldAddInterceptors() {
        when(registry.addInterceptor(authInterceptor)).thenReturn(registration);
        when(registration.addPathPatterns(anyString())).thenReturn(registration);

        webConfig.addInterceptors(registry);

        verify(registry).addInterceptor(authInterceptor);
        verify(registration).addPathPatterns("/**");
        verify(registration).excludePathPatterns("/login", "/auth/login");
    }
}