package br.com.restaurants.core.usecases;

import br.com.restaurants.core.entities.User;
import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUsersUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindAllUsersUseCase useCase;

    @Test
    void shouldReturnAllUsers() {
        User user = new User();
        List<User> expectedList = List.of(user);

        when(userGateway.findAll()).thenReturn(expectedList);

        List<User> result = useCase.execute();

        assertEquals(expectedList, result);
        verify(userGateway).findAll();
    }
}