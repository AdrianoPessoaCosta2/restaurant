package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindUserByIdUseCase useCase;

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        User expectedUser = new User();

        when(userGateway.findById(id)).thenReturn(expectedUser);

        User result = useCase.execute(id);

        assertEquals(expectedUser, result);
        verify(userGateway).findById(id);
    }
}