package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.infrastructure.controller.response.ErrorResponse;
import br.com.restaurants.infrastructure.exception.BusinessException;
import br.com.restaurants.infrastructure.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Mock
    private HttpServletRequest request;

    @Test
    void shouldHandleResourceNotFoundException() {
        String uri = "/api/test";
        String msg = "Not found";
        when(request.getRequestURI()).thenReturn(uri);

        ResponseEntity<ErrorResponse> response = handler.handleNotFound(new ResourceNotFoundException(msg), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldHandleBusinessException() {
        String uri = "/api/test";
        String msg = "Business error";
        when(request.getRequestURI()).thenReturn(uri);

        ResponseEntity<ErrorResponse> response = handler.handleBusiness(new BusinessException(msg), request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldHandleGenericException() {
        String uri = "/api/test";
        String msg = "Internal error";
        when(request.getRequestURI()).thenReturn(uri);

        ResponseEntity<ErrorResponse> response = handler.handleGeneric(new Exception(msg), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}