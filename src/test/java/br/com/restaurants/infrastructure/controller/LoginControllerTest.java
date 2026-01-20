package br.com.restaurants.infrastructure.controller;

import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.infrastructure.controller.request.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserGateway userGateway;

    @Mock
    private HttpSession session;

    @Test
    void shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest("user", "123456");

        when(userGateway.validateLogin("user", "123456")).thenReturn(true);

        ResponseEntity response = loginController.login(request, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logado com sucesso. Cookie de sessão criado.", response.getBody());
        verify(session).setAttribute("usuarioLogado", "user");
    }

    @Test
    void shouldFailLoginWithIncorrectCredentials() {
        LoginRequest request = new LoginRequest("user", "wrongPass");

        when(userGateway.validateLogin("user", "wrongPass")).thenReturn(false);

        ResponseEntity response = loginController.login(request, session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Login ou senha incorretos", response.getBody());
        verify(session, never()).setAttribute(anyString(), any());
    }
}