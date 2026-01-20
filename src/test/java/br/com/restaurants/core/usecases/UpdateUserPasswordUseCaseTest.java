package br.com.restaurants.core.usecases;

import br.com.restaurants.core.gateway.UserGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateUserPasswordUseCase useCase;

    @Test
    void shouldUpdateUserPassword() {
        UUID id = UUID.randomUUID();
        String newPassword = "newPassword123";

        useCase.execute(id, newPassword);

        verify(userGateway).updatePassword(id, newPassword);
    }
}