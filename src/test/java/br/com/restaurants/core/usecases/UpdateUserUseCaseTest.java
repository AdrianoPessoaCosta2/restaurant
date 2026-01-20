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
class UpdateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateUserUseCase useCase;

    @Test
    void shouldUpdateUser() {
        User user = new User();
        User updatedUser = new User();

        when(userGateway.update(user)).thenReturn(updatedUser);

        User result = useCase.execute(user);

        assertEquals(updatedUser, result);
        verify(userGateway).update(user);
    }
}