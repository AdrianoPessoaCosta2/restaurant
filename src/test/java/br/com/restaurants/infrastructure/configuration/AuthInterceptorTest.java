package br.com.restaurants.infrastructure.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthInterceptorTest {

    @InjectMocks
    private AuthInterceptor authInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Test
    void shouldDenyAccessWhenSessionIsNull() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response).sendError(eq(401), anyString());
    }

    @Test
    void shouldDenyAccessWhenAttributeIsNull() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("usuarioLogado")).thenReturn(null);

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertFalse(result);
        verify(response).sendError(eq(401), anyString());
    }

    @Test
    void shouldAllowAccessWhenUserIsLogged() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("usuarioLogado")).thenReturn(new Object());

        boolean result = authInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response, never()).sendError(anyInt(), anyString());
    }
}