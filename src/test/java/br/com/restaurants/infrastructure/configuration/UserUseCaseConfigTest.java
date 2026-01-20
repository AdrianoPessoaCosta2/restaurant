package br.com.restaurants.infrastructure.configuration;

import br.com.restaurants.core.gateway.AddressGateway;
import br.com.restaurants.core.gateway.UserGateway;
import br.com.restaurants.core.usecases.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserUseCaseConfigTest {

    private final UserUseCaseConfig config = new UserUseCaseConfig();

    @Mock
    private UserGateway userGateway;

    @Mock
    private AddressGateway addressGateway;

    @Test
    void shouldCreateFindAllUsersUseCase() {
        FindAllUsersUseCase useCase = config.findAllUsersUseCase(userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFindUserByIdUseCase() {
        FindUserByIdUseCase useCase = config.findUserByIdUseCase(userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFindUserByNameUseCase() {
        FindUserByNameUseCase useCase = config.findUserByNameUseCase(userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateCreateUserUseCase() {
        CreateUserUseCase useCase = config.createUserUseCase(userGateway, addressGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateUpdateUserUseCase() {
        UpdateUserUseCase useCase = config.updateUserUseCase(userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateDeleteUserUseCase() {
        DeleteUserUseCase useCase = config.deleteUserUseCase(userGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateUpdateUserPasswordUseCase() {
        UpdateUserPasswordUseCase useCase = config.updateUserPasswordUseCase(userGateway);
        assertNotNull(useCase);
    }
}