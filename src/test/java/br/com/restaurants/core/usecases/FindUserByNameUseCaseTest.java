package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByNameUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindUserByNameUseCase useCase;

    @Test
    void shouldFindUserByName() {
        String name = "John Doe";
        User expectedUser = new User();

        when(userGateway.findByName(name)).thenReturn(expectedUser);

        User result = useCase.execute(name);

        assertEquals(expectedUser, result);
        verify(userGateway).findByName(name);
    }
}